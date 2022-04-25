package ar.edu.itba.paw.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class OfferJdbcDao implements OfferDao {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private SimpleJdbcInsert jdbcOfferInsert;
    private SimpleJdbcInsert jdbcPaymentMethodAtOfferInsert;


    private final static RowMapper<Offer.Builder> OFFER_ROW_MAPPER =
            (resultSet, i) -> {
                User seller = User.builder()
                        .id(resultSet.getInt("seller_id"))
                        .email(resultSet.getString("email"))
                        .build();

                OfferStatus offerStatus = OfferStatus.getInstance(
                        resultSet.getString("status_code"),
                        resultSet.getString("status_description")
                );

                String cryptoId = resultSet.getString("crypto_code");
                Cryptocurrency crypto = Cryptocurrency.getInstance(
                        cryptoId,
                        resultSet.getString("commercial_name"),
                        resultSet.getDouble("market_price")
                );

                String paymentCode = resultSet.getString("payment_code");
                PaymentMethod pm = paymentCode == null ? null : PaymentMethod.getInstance(paymentCode, resultSet.getString("payment_description"));

                return Offer.builder(
                                seller,
                                crypto,
                                resultSet.getDouble("asking_price")
                        )
                        .id(resultSet.getInt("offer_id"))
                        .quantity(resultSet.getDouble("quantity"))
                        .paymentMethod(pm)
                        .date(resultSet.getTimestamp("offer_date").toLocalDateTime())
                        .status(offerStatus);
            };

    private final static ResultSetExtractor<List<Offer.Builder>> OFFER_MULTIROW_MAPPER = resultSet -> {
        int i = 0;
        Map<Integer, Offer.Builder> cache = new HashMap<>(); // TODO - rename
        while (resultSet.next()) {
            int offerId = resultSet.getInt("offer_id");
            String paymentCode = resultSet.getString("payment_code");  // TODO: Improve
            PaymentMethod pm = paymentCode == null ? null : PaymentMethod.getInstance( paymentCode, resultSet.getString("payment_description"));
            Offer.Builder instance = cache.getOrDefault(
                    offerId,
                    OFFER_ROW_MAPPER.mapRow(resultSet, i)
            ).paymentMethod(pm);
            cache.putIfAbsent(offerId, instance);
            i ++;
        }
        return cache.values().stream().collect(Collectors.toList());
    };

    @Autowired
    public OfferJdbcDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcOfferInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("offer").usingGeneratedKeyColumns("id");
        jdbcPaymentMethodAtOfferInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("payment_methods_at_offer");
    }

    String allQuery = "SELECT *\n" +
            "FROM offer_complete\n" +
            "WHERE offer_id IN (\n" +
            "    SELECT DISTINCT offer_id\n" +
            "    FROM offer_complete\n" +
            "    WHERE ( COALESCE(:offer_ids, null) IS NULL OR offer_id IN (:offer_ids)) AND\n" +
            "          ( COALESCE(:payment_codes, null) IS NULL OR payment_code IN (:payment_codes)) AND\n" +
            "          ( COALESCE(:crypto_codes, null) IS NULL OR crypto_code IN (:crypto_codes)) AND\n" +
            "          :min <= asking_price*quantity AND\n" +
            "          :max >= asking_price*quantity\n" +
            "    LIMIT :limit OFFSET :offset\n" +
            ")";


    String countQuery = "SELECT COUNT(DISTINCT offer_id)\n" +
            "FROM offer_complete\n" +
            "WHERE offer_id IN (\n" +
            "    SELECT DISTINCT offer_id\n" +
            "    FROM offer_complete\n" +
            "    WHERE ( COALESCE(:offer_ids, null) IS NULL OR offer_id IN (:offer_ids)) AND\n" +
            "          ( COALESCE(:payment_codes, null) IS NULL OR payment_code IN (:payment_codes)) AND\n" +
            "          ( COALESCE(:crypto_codes, null) IS NULL OR crypto_code IN (:crypto_codes)) AND\n" +
            "          :min <= asking_price*quantity AND\n" +
            "          :max >= asking_price*quantity\n" +
            ")";


    private static MapSqlParameterSource toMapSqlParameterSource(OfferFilter filter) {
        return new MapSqlParameterSource()
                .addValue("crypto_codes", filter.getCryptoCodes().isEmpty() ? null: filter.getCryptoCodes())
                .addValue("offer_ids", filter.getIds().isEmpty() ? null : filter.getIds())
                .addValue("payment_codes", filter.getPaymentMethods().isEmpty() ? null : filter.getPaymentMethods())
                .addValue("limit", filter.getPageSize())
                .addValue("offset", filter.getPage()*filter.getPageSize())
                .addValue("min", filter.getMinPrice())
                .addValue("max", filter.getMaxPrice());
    }

    @Override
    public int getOfferCount(OfferFilter filter) {
        return namedJdbcTemplate.queryForObject(countQuery, toMapSqlParameterSource(filter), Integer.class);
    }

    @Override
    public Collection<Offer> getOffersBy(OfferFilter filter) {
            List<Offer> x =  namedJdbcTemplate.query(allQuery, toMapSqlParameterSource(filter), OFFER_MULTIROW_MAPPER)
                .stream()
                .map(Offer.Builder::build)
                .collect(Collectors.toList());
        return x;
    }

    @Override
    public Offer makeOffer(Offer.Builder builder) {
        Map<String,Object> args = new HashMap<>();
        args.put("seller_id", builder.getSeller().getId());

        args.put("offer_date", builder.getDate().getYear()+"-"+builder.getDate().getMonthValue()+"-"+builder.getDate().getDayOfMonth());
        args.put("crypto_code", builder.getCrypto().getCode());
        args.put("status_code", "APR");
        args.put("asking_price", builder.getAskingPrice());
        args.put("quantity", builder.getQuantity());
        int offerId = jdbcOfferInsert.executeAndReturnKey(args).intValue();
        args.clear();

        args.put("offer_id", offerId);
        for (PaymentMethod pm: builder.getPaymentMethods()) {
            args.put("payment_code", pm.getName());
            jdbcPaymentMethodAtOfferInsert.execute(args);
        }

        builder.id(offerId);

        return builder.build();
    }

}

package ar.edu.itba.paw.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class OfferJdbcDao implements OfferDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

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
                PaymentMethod pm = paymentCode == null ? null : PaymentMethod.getInstance( paymentCode, resultSet.getString("payment_description"));

                return Offer.builder(
                                seller,
                                crypto,
                                resultSet.getDouble("asking_price")
                        )
                        .id(resultSet.getInt("offer_id"))
                        .amount(resultSet.getDouble("quantity"))
                        .paymentMethod(pm)
                        .date(resultSet.getTimestamp("offer_date").toLocalDateTime())
                        .status(offerStatus);
            };

    private final static ResultSetExtractor<List<Offer.Builder>> OFFER_MULTIROW_MAPPER = resultSet -> {
        int i = 0;
        Map<Integer, Offer.Builder> cache = new HashMap<>();
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
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("offer").usingGeneratedKeyColumns("offer_id");
    }

    @Override
    public Offer makeOffer(Offer.Builder builder) {
        final Map<String,Object> args = new HashMap<>();
        args.put("seller_id", builder.getSeller().getId());
        args.put("offer_date", builder.getDate());
//        args.put("coin_id", builder.getCoinId());
        args.put("asking_price", builder.getAskingPrice());
        args.put("status_id", 2);
        args.put("coin_amount", builder.getCoinAmount());
        jdbcInsert.executeAndReturnKey(args).intValue();
        return builder.build();
    }


    @Override
    public Offer getOffer(int offer_id) {

        String query =
                "SELECT * FROM offer \n" +
                        "    JOIN users ON offer.seller_id = users.id\n" +
                        "    JOIN cryptocurrency c on offer.crypto_code = c.code\n" +
                        "    LEFT OUTER JOIN payment_methods_at_offer pmao on offer.id = pmao.offer_id \n" +
                        "    LEFT OUTER JOIN payment_method pm on pmao.payment_code = pm.code\n" +
                        "    JOIN status s on s.code = offer.status_code"+
                        "    WHERE offer.id=?";

        final List<Offer.Builder> offer = jdbcTemplate.query(query, OFFER_MULTIROW_MAPPER, offer_id);
        return offer.get(0).build();
    }

    @Override
    public List<Offer> getAllOffers() {

        String query =
                "SELECT * FROM offer \n" +
                "    JOIN users ON offer.seller_id = users.id\n" +
                "    JOIN cryptocurrency c on offer.crypto_code = c.code\n" +
                "    LEFT OUTER JOIN payment_methods_at_offer pmao on offer.id = pmao.offer_id \n" +
                "    JOIN status s on s.code = offer.status_code"+
                "    LEFT OUTER JOIN payment_method pm on pmao.payment_code = pm.code\n";

        Collection<Offer.Builder> builders = jdbcTemplate.query(query, OFFER_MULTIROW_MAPPER);
        return builders.stream().map(Offer.Builder::build)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Offer> getPagedOffers(int page, int pageSize) {
        int idx = pageSize * page;
        String query = "SELECT * FROM\n" +
                "( SELECT * FROM offer LIMIT ? OFFSET ?) paged_offer\n" +
                "JOIN users ON paged_offer.seller_id = users.id\n" +
                "JOIN cryptocurrency c on paged_offer.crypto_code = c.code\n" +
                "LEFT OUTER JOIN payment_methods_at_offer pmao on paged_offer.id = pmao.offer_id\n" +
                "JOIN status s on s.code = paged_offer.status_code\n" +
                "LEFT OUTER JOIN payment_method pm on pmao.payment_code = pm.code;";

        return jdbcTemplate.query(query, OFFER_MULTIROW_MAPPER, pageSize, idx)
                .stream().map(Offer.Builder::build)
                .collect(Collectors.toList());
    }

    @Override
    public int getOfferCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM offer", Integer.class);
    }
}

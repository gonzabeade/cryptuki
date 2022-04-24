package ar.edu.itba.paw.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.beans.Expression;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class OfferJdbcDao implements OfferDao {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedJdbcTemplate;
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
                PaymentMethod pm = paymentCode == null ? null : PaymentMethod.getInstance(paymentCode, resultSet.getString("payment_description"));

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
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("offer").usingGeneratedKeyColumns("offer_id");
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
        System.out.println(x);
        return x;
    }

}

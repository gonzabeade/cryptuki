package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.OfferDigest;
import ar.edu.itba.paw.OfferFilter;
import ar.edu.itba.paw.exception.UncategorizedPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
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
                User seller = new User.Builder( resultSet.getString("email") )
                        .withId(resultSet.getInt("seller_id"))
                        .withPhoneNumber(resultSet.getString("phone_number"))
                        .withRatingCount(resultSet.getInt("rating_count"))
                        .withRatingSum(resultSet.getInt("rating_sum"))
                        .withUsername(resultSet.getString("uname"))
                        .withLastLogin(resultSet.getTimestamp("last_login").toLocalDateTime())
                        .build();

                OfferStatus offerStatus = OfferStatus.getInstance(
                        resultSet.getString("status_code"),
                        resultSet.getString("status_description")
                );

                String cryptoId = resultSet.getString("crypto_code");
                Cryptocurrency crypto = Cryptocurrency.getInstance(
                        cryptoId,
                        resultSet.getString("commercial_name")
                );

                String paymentCode = resultSet.getString("payment_code");
                PaymentMethod pm = paymentCode == null ? null : PaymentMethod.getInstance(paymentCode, resultSet.getString("payment_description"));

                return new Offer.Builder(
                                resultSet.getInt("offer_id"),
                                seller,
                                crypto,
                                resultSet.getFloat("asking_price")
                        )
                        .withMinQuantity(resultSet.getFloat("min_quantity"))
                        .withMaxQuantity(resultSet.getFloat("max_quantity"))
                        .withPaymentMethod(pm)
                        .withDate(resultSet.getTimestamp("offer_date").toLocalDateTime())
                        .withComments(resultSet.getString("comments"))
                        .withStatus(offerStatus);
            };

    private final static ResultSetExtractor<List<Offer.Builder>> OFFER_MULTIROW_MAPPER = resultSet -> {
        int i = 0;
        Map<Integer, Offer.Builder> map = new LinkedHashMap<>();
        while (resultSet.next()) {
            int offerId = resultSet.getInt("offer_id");
            String paymentCode = resultSet.getString("payment_code");
            PaymentMethod pm = paymentCode == null ? null : PaymentMethod.getInstance( paymentCode, resultSet.getString("payment_description"));
            Offer.Builder instance = map.getOrDefault(
                    offerId,
                    OFFER_ROW_MAPPER.mapRow(resultSet, i)
            ).withPaymentMethod(pm);
            map.putIfAbsent(offerId, instance);
            i ++;
        }
        return map.values().stream().collect(Collectors.toList());
    };

    @Autowired
    public OfferJdbcDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        jdbcOfferInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("offer").usingGeneratedKeyColumns("id");
        jdbcPaymentMethodAtOfferInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("payment_methods_at_offer");
    }

    private static MapSqlParameterSource toMapSqlParameterSource(OfferFilter filter) {
        return new MapSqlParameterSource()
                .addValue("crypto_codes", filter.getCryptoCodes().isEmpty() ? null: filter.getCryptoCodes())
                .addValue("offer_ids", filter.getIds().isEmpty() ? null : filter.getIds())
                .addValue("payment_codes", filter.getPaymentMethods().isEmpty() ? null : filter.getPaymentMethods())
                .addValue("limit", filter.getPageSize())
                .addValue("offset", filter.getPage()*filter.getPageSize())
                .addValue("min", filter.getMinPrice().isPresent() ? filter.getMinPrice().getAsDouble() : null)
                .addValue("max", filter.getMaxPrice().isPresent() ? filter.getMaxPrice().getAsDouble() : null)
                .addValue("uname", filter.getUsername().orElse(null))
                .addValue("status", filter.getStatus().isEmpty() ? null: filter.getStatus());
    }

    private static MapSqlParameterSource toMapSqlParameterSource(OfferDigest digest) {
        return new MapSqlParameterSource()
                .addValue("min_quantity", digest.getMinQuantity())
                .addValue("crypto_code", digest.getCryptoCode())
                .addValue("max_quantity", digest.getMaxQuantity())
                .addValue("comments", digest.getComments())
                .addValue("asking_price", digest.getAskingPrice())
                .addValue("offer_id", digest.getId());
    }

    @Override
    public int getOfferCount(OfferFilter filter) {

        final String countQuery = "SELECT COUNT( DISTINCT offer_id) FROM offer_complete\n" +
                "WHERE offer_id IN (\n" +
                "    SELECT DISTINCT offer_id\n" +
                "    FROM offer_complete\n" +
                "    WHERE ( COALESCE(:offer_ids, null) IS NULL OR offer_id IN (:offer_ids)) AND\n" +
                "\n" +
                "          ( COALESCE(:payment_codes, null) IS NULL OR payment_code IN (:payment_codes)) AND\n" +
                "          ( COALESCE(:crypto_codes, null) IS NULL OR crypto_code IN (:crypto_codes)) AND\n" +
                "          ( COALESCE(:min,null) IS NULL OR :min >= asking_price*min_quantity) AND\n" +
                "          ( COALESCE(:max,null) IS NULL OR :max <= asking_price*max_quantity) AND\n" +
                "          ( COALESCE(:uname, null) IS NULL or uname = :uname) AND\n" +
                "          ( COALESCE(:status, null) IS NULL or status_code IN (:status))\n" +
                ")";

        try {
            return namedJdbcTemplate.queryForObject(countQuery, toMapSqlParameterSource(filter), Integer.class);
        } catch (EmptyResultDataAccessException erde) {
            return 0;
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }

    @Override
    public Collection<Offer> getOffersBy(OfferFilter filter) {
        final String allQuery = "SELECT * FROM offer_complete\n" +
                "WHERE offer_id IN (\n" +
                "    SELECT DISTINCT offer_id\n" +
                "    FROM offer_complete\n" +
                "    WHERE ( COALESCE(:offer_ids, null) IS NULL OR offer_id IN (:offer_ids)) AND\n" +
                "          ( COALESCE(:payment_codes, null) IS NULL OR payment_code IN (:payment_codes)) AND\n" +
                "          ( COALESCE(:crypto_codes, null) IS NULL OR crypto_code IN (:crypto_codes)) AND\n" +
                "          ( COALESCE(:min,null) IS NULL OR :min >= asking_price*min_quantity) AND\n" +
                "          ( COALESCE(:max,null) IS NULL OR :max <= asking_price*max_quantity) AND\n" +
                "          ( COALESCE(:uname, null) IS NULL or uname = :uname) AND\n" +
                "          ( COALESCE(:status, null) IS NULL or status_code IN (:status))\n" +
                "   LIMIT :limit OFFSET :offset " +
                ") ORDER BY last_login DESC";

        try {
            return namedJdbcTemplate.query(allQuery, toMapSqlParameterSource(filter), OFFER_MULTIROW_MAPPER)
                    .stream()
                    .map(Offer.Builder::build)
                    .collect(Collectors.toList());
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }

    @Override
    public int makeOffer(OfferDigest digest) {
        Map<String,Object> args = new HashMap<>();

        args.put("seller_id", digest.getSellerId());
        args.put("offer_date", digest.getDate().getYear()+"-"+digest.getDate().getMonthValue()+"-"+digest.getDate().getDayOfMonth()+" "+digest.getDate().getHour()+":"+digest.getDate().getMinute()+":"+digest.getDate().getSecond());
        args.put("crypto_code", digest.getCryptoCode());
        args.put("status_code", "APR");
        args.put("asking_price", digest.getAskingPrice());
        args.put("max_quantity", digest.getMaxQuantity());
        args.put("min_quantity", digest.getMinQuantity());
        args.put("comments", digest.getComments());

        int offerId;
        try {
            offerId = jdbcOfferInsert.executeAndReturnKey(args).intValue();
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }

        addPaymentMethodsToOffer(offerId, digest.getPaymentMethods());

        return offerId;
    }

    private void addPaymentMethodsToOffer(int offerId, Collection<String> paymentMethods) {
        final String paymentMethodQuery = "INSERT INTO payment_methods_at_offer SELECT id, code FROM offer, payment_method WHERE code IN (:pm) AND id = :id";

        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", offerId)
                .addValue("pm", paymentMethods);
        try {
            namedJdbcTemplate.update(paymentMethodQuery, map);
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }


    private void changeOfferStatus(int offerId, String statusCode) {
        String query = "UPDATE offer SET status_code= ? WHERE id = ?";
        try {
            jdbcTemplate.update(query, statusCode, offerId);
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }
    @Override
    public void deleteOffer(int offerId) {
        changeOfferStatus(offerId, "DEL");
    }

    @Override
    public void hardPauseOffer(int offerId) {
        changeOfferStatus(offerId, "PSU");
    }

    @Override
    public void pauseOffer(int offerId) {
        changeOfferStatus(offerId, "PSE");
    }

    @Override
    public void resumeOffer(int offerId) {
        changeOfferStatus(offerId, "APR");
    }

    @Override
    public Optional<String> getOwner(int offerId) {
        final String query = "SELECT DISTINCT uname FROM offer_complete WHERE offer_id = ?";

        try {
            return Optional.of(jdbcTemplate.queryForObject(query, String.class, offerId));
        } catch (EmptyResultDataAccessException erde) {
            return Optional.empty();
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }

    @Override
    public void setMaxQuantity(int offerId, float newQuantity) {
        final String query = "UPDATE offer SET max_quantity = ? WHERE id = ?";

        try {
            jdbcTemplate.update(query, newQuantity, offerId);
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }

    @Override
    public void modifyOffer(OfferDigest digest) {
        final String baseQuery = "UPDATE offer SET asking_price = :asking_price, max_quantity = :max_quantity, min_quantity = :min_quantity, comments = :comments, crypto_code = :crypto_code WHERE id = :offer_id";
        final String deleteQuery = "DELETE FROM payment_methods_at_offer WHERE offer_id = ?";

        try {
            namedJdbcTemplate.update(baseQuery, toMapSqlParameterSource(digest));
            jdbcTemplate.update(deleteQuery, digest.getId());
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }

        addPaymentMethodsToOffer(digest.getId(), digest.getPaymentMethods());
    }

}

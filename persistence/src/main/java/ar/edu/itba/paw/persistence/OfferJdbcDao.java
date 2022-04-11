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

                String cryptoId = resultSet.getString("coin_id");
                Cryptocurrency crypto = Cryptocurrency.getInstance(
                        cryptoId,
                        resultSet.getString("description"),
                        resultSet.getDouble("market_price")
                );

                PaymentMethod pm = PaymentMethod.getInstance(
                        resultSet.getString("payment_name"),
                        ""
                );

                return Offer.builder(
                                seller,
                                crypto,
                                resultSet.getDouble("asking_price")
                        )
                        .id(resultSet.getInt("offer_id"))
                        .amount(resultSet.getDouble("coin_amount"))
                        .paymentMethod(pm)
                        .date(resultSet.getTimestamp("offer_date").toLocalDateTime())
                        .status(resultSet.getInt("status_id"));
            };

    private final static ResultSetExtractor<Collection<Offer.Builder>> OFFER_MULTIROW_MAPPER = resultSet -> {
        int i = 0;
        Map<Integer, Offer.Builder> cache = new HashMap<>();
        while (resultSet.next()) {
            int offerId = resultSet.getInt("offer_id");
            PaymentMethod pm = PaymentMethod.getInstance( resultSet.getString("payment_name"), "Dummy Description");
            cache.getOrDefault(
                    offerId,
                    OFFER_ROW_MAPPER.mapRow(resultSet, i)
            ).paymentMethod(pm);
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
        final List<Offer.Builder> offer = jdbcTemplate.query("SELECT * FROM PUBLIC.OFFER JOIN PUBLIC.USERS ON offer.seller_id = users.id JOIN cryptocurrency c on offer.coin_id = c.id WHERE offer_id=?", OFFER_ROW_MAPPER, offer_id);
        return offer.get(0).build();
    }

    @Override
    public List<Offer> getAllOffers() {

        String query =
                "SELECT * FROM offer \n" +
                "    JOIN users ON offer.seller_id = users.id\n" +
                "    JOIN cryptocurrency c on offer.coin_id = c.id\n" +
                "    JOIN payment_methods_at_offer pmao on offer.offer_id = pmao.offer_id \n" +
                "    JOIN payment_method pm on pmao.payment_method_id = pm.id\n";

        return jdbcTemplate.query(query, OFFER_MULTIROW_MAPPER)
                .stream().map(Offer.Builder::build)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Offer> getPagedOffers(int page, int pageSize) {
        int idx = pageSize * page;
        String query = "SELECT * FROM\n" +
                "( SELECT * FROM offer LIMIT ? OFFSET ?) paged_offer\n" +
                "JOIN users ON paged_offer.seller_id = users.id\n" +
                "JOIN cryptocurrency c on paged_offer.coin_id = c.id\n" +
                "JOIN payment_methods_at_offer pmao on paged_offer.offer_id = pmao.offer_id\n" +
                "JOIN payment_method pm on pmao.payment_method_id = pm.id;";

        return jdbcTemplate.query(query, OFFER_MULTIROW_MAPPER, pageSize, idx)
                .stream().map(Offer.Builder::build)
                .collect(Collectors.toList());
    }

    @Override
    public int getOfferCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM offer", Integer.class);
    }
}

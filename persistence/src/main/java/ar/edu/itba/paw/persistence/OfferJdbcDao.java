package ar.edu.itba.paw.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class OfferJdbcDao implements OfferDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    private static Map<String, Offer.Builder> builders = new HashMap<>();

    private final static RowMapper<Offer> OFFER_ROW_MAPPER =
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

                return Offer.builder(
                                seller,
                                crypto,
                                resultSet.getDouble("asking_price")
                        )
                        .id(resultSet.getInt("offer_id"))
                        .amount(resultSet.getDouble("coin_amount"))
                        .date(resultSet.getTimestamp("offer_date").toLocalDateTime())
                        .status(resultSet.getInt("status_id"))
                        .build();
            };

    private final static RowMapper<Offer.Builder> OFFER_BUILDER_ROW_MAPPER =
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

                PaymentMethod pm = new PaymentMethod(
                        resultSet.getInt("payment_method_id"),
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
    public List<Offer> getAllOffers() {
        final List<Offer> offers = jdbcTemplate.query("SELECT * FROM PUBLIC.OFFER JOIN PUBLIC.USERS ON offer.seller_id = users.id JOIN cryptocurrency c on offer.coin_id = c.id", OFFER_ROW_MAPPER);
        return offers;
    }
    @Override
    public Offer getOffer(int offer_id) {
        final List<Offer> offer = jdbcTemplate.query("SELECT * FROM PUBLIC.OFFER JOIN PUBLIC.USERS ON offer.seller_id = users.id JOIN cryptocurrency c on offer.coin_id = c.id WHERE offer_id=" + offer_id, OFFER_ROW_MAPPER);
        return offer != null ? offer.get(0) : null;
    }
    @Override
    public Iterable<Offer> getPagedOffers(int page, int pageSize) {

        String query = "SELECT * FROM offer \n" +
                "    JOIN users ON offer.seller_id = users.id\n" +
                "    JOIN cryptocurrency c on offer.coin_id = c.id\n" +
                "    JOIN payment_methods_at_offer pmao on offer.offer_id = pmao.offer_id \n" +
                "    JOIN payment_method pm on pmao.payment_method_id = pm.id\n" +
                "    LIMIT ? OFFSET ?";



        final List<Offer.Builder> offerBuilders = jdbcTemplate.query(query, OFFER_BUILDER_ROW_MAPPER, pageSize, page);
        Map<Integer,Offer.Builder> builderMap = new HashMap<>();
        for(Offer.Builder builder : offerBuilders ){
            builderMap.putIfAbsent(builder.getId(),builder);
            for(PaymentMethod paymentMethod : builder.getPaymentMethods())
                builderMap.get(builder.getId()).paymentMethod(paymentMethod);
        }

        final List<Offer> offerList = new ArrayList<>();
        for(Offer.Builder builder : builderMap.values())
            offerList.add(builder.build());

        return offerList;
    }
}

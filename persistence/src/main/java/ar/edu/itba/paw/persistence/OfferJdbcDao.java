package ar.edu.itba.paw.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.util.*;

@Repository
public class OfferJdbcDao implements OfferDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    private final static RowMapper<Offer> ROW_MAPPER =
            (resultSet, i) -> Offer.builder(
                            resultSet.getInt("seller_id"),
                            resultSet.getString("coin_id"),
                            resultSet.getDouble("asking_price")
                    )
                    .id(resultSet.getInt("offer_id"))
                    .amount(resultSet.getDouble("coin_amount"))
                    .date(resultSet.getTimestamp("offer_date"))
                    .status(resultSet.getInt("status_id"))
                    .build();

    @Autowired
    public OfferJdbcDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("offer").usingGeneratedKeyColumns("offer_id");
    }

    @Override
    public Offer makeOffer(Offer.Builder builder) {
        final Map<String,Object> args = new HashMap<>();
        args.put("seller_id", builder.getSellerId());
        args.put("offer_date", builder.getDate());
        args.put("coin_id", builder.getCoinId());
        args.put("asking_price", builder.getAskingPrice());
        args.put("status_id", 2);
        args.put("coin_amount", builder.getCoinAmount());
        jdbcInsert.executeAndReturnKey(args).intValue();
        return builder.build();
    }

    @Override
    public List<Offer> getAllOffers() {
        final List<Offer> offers = jdbcTemplate.query("SELECT * FROM PUBLIC.OFFER", ROW_MAPPER);
        return offers;
    }
}

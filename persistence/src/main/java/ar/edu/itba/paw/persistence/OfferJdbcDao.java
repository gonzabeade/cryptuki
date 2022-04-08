package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.Offer;
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
            (resultSet, i) -> new Offer.Builder()
                    .id(resultSet.getInt("offer_id"))
                    .seller(resultSet.getInt("seller_id"))
                    .date(resultSet.getTimestamp("offer_date"))
                    .coin(resultSet.getString("coin_id"))
                    .status(resultSet.getString("status_id"))
                    .price(resultSet.getDouble("asking_price"))
                    .amount(resultSet.getDouble("coin_amount"))
                    .build();

    @Autowired
    public OfferJdbcDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("offer").usingGeneratedKeyColumns("offer_id");
    }

    @Override // TODO: Reduce parameters in this code
    public int makeOffer(int sellerId, Date date,String coinId, double price, double coinAmount) {
        final Map<String,Object> args = new HashMap<>();
        args.put("seller_id", sellerId);
        args.put("offer_date", new java.sql.Date(date.getTime()));
        args.put("coin_id", coinId);
        args.put("asking_price", price);
        args.put("status_id", 2);
        args.put("coin_amount", coinAmount);
        final int offerId = jdbcInsert.executeAndReturnKey(args).intValue();
        return offerId;
    }

    @Override
    public List<Offer> getAllOffers() {
        final List<Offer> offers = jdbcTemplate.query("SELECT * FROM PUBLIC.OFFER", ROW_MAPPER);
        return offers;
    }
}

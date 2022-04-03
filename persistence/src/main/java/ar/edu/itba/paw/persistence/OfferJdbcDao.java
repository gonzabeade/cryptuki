package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OfferJdbcDao implements OfferDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    private final static RowMapper<Offer> ROW_MAPPER = (resultSet, i) -> new Offer(resultSet.getInt("offer_id"),resultSet.getInt("seller_id"),resultSet.getTimestamp("offer_date"), resultSet.getString("coin_id"),resultSet.getDouble("asking_price"),
            resultSet.getDouble("coin_amount"));

    @Autowired
    public OfferJdbcDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("offers").usingGeneratedKeyColumns("offer_id");
    }

    @Override
    public Offer makeOffer(int seller_id, Date offer_date,String coin_id, float asking_price, float coin_amount) {
        final Map<String,Object> args = new HashMap<>();
        args.put("seller_id",seller_id);
        args.put("offer_date",new java.sql.Date(offer_date.getTime()));
        args.put("coin_id",coin_id);
        args.put("asking_price",asking_price);
        args.put("coin_amount",coin_amount);
        final Number offer_id = jdbcInsert.executeAndReturnKey(args);
        return new Offer(offer_id,seller_id,offer_date,coin_id,asking_price,coin_amount);
    }

    @Override
    public List<Offer> getAllOffers() {
        final List<Offer> offers = jdbcTemplate.query("SELECT * FROM PUBLIC.OFFERS",ROW_MAPPER);
        return offers;
    }
}

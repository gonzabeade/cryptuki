package ar.edu.itba.paw.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class TradeDaoImpl implements TradeDao {

    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    private final static RowMapper<Trade> TRADE_COMPLETE_ROW_MAPPER =
            (rs, i) -> new Trade.Builder(
                    rs.getInt("offer_id"),
                    rs.getString("buyer_uname"))
                    .withSellerUsername(rs.getString("seller_uname"))
                    .withTradeStatus( TradeStatus.valueOf(rs.getString("status")))
                    .withTradeId(rs.getInt("trade_id"))
                    .withQuantity(rs.getFloat("quantity"))
                    .withStartDate(rs.getTimestamp("start_date").toLocalDateTime())
                    .build();

    @Autowired
    public TradeDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void makeTrade(int offerId, String buyerUsername, float quantity, TradeStatus status) {
        final MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("offer_id", offerId)
                .addValue("buyer_uname", buyerUsername)
                .addValue("status", status.toString())
                .addValue("quantity", quantity);


        final String query = "INSERT INTO trade (offer_id, buyer_id, start_date, status, quantity) VALUES ( :offer_id,\n" +
                "        (SELECT user_id FROM auth WHERE auth.uname = :buyer_uname),\n" +
                "        NOW(), :status, :quantity)";

        namedJdbcTemplate.update(query, map);
    }

    @Override
    public void makeTrade(Trade.Builder builder) {
        makeTrade(builder.getTradeId(), builder.getBuyerUsername(), builder.getQuantity(), builder.getStatus());
    }

    @Override
    public void updateStatus(int tradeId, TradeStatus status) {
        final String query = "UPDATE trade SET status = ? WHERE trade_id = ?";
        jdbcTemplate.update(query, status.toString(), tradeId);
    }

    @Override
    public Optional<Trade> getTradeById(int tradeId) {
        String query = "SELECT * FROM trade_complete WHERE trade_id = ?";
        Trade trade = jdbcTemplate.queryForObject(query, TRADE_COMPLETE_ROW_MAPPER, tradeId);
        return Optional.ofNullable(trade);
    }

    @Override
    public Collection<Trade> getSellingTradesByUsername(String username, int page, int pageSize) {
        final String query = "SELECT * FROM trade_complete WHERE seller_uname = ? LIMIT ? OFFSET ?";
        return Collections.unmodifiableCollection(jdbcTemplate.query(query, TRADE_COMPLETE_ROW_MAPPER, username, pageSize, pageSize*page));
    }

    @Override
    public Collection<Trade> getBuyingTradesByUsername(String username, int page, int pageSize) {
        final String query = "SELECT * FROM trade_complete WHERE buyer_uname = ? LIMIT ? OFFSET ?";
        return Collections.unmodifiableCollection(jdbcTemplate.query(query, TRADE_COMPLETE_ROW_MAPPER, username, pageSize, pageSize*page));
    }
}

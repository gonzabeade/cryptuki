package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.exception.UncategorizedPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class TradeDaoImpl implements TradeDao {

    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcTradeInsert;



    private final static RowMapper<Trade> TRADE_COMPLETE_ROW_MAPPER =
            (rs, i) ->{
                String cryptoId = rs.getString("crypto_code");
                Cryptocurrency crypto = Cryptocurrency.getInstance(
                        cryptoId,
                        rs.getString("commercial_name")
                );
                  return   new Trade.Builder(
                        rs.getInt("offer_id"),
                        rs.getString("buyer_uname"))
                        .withSellerUsername(rs.getString("seller_uname"))
                        .withTradeStatus( TradeStatus.valueOf(rs.getString("status")))
                        .withTradeId(rs.getInt("trade_id"))
                        .withQuantity(rs.getFloat("quantity"))
                        .withStartDate(rs.getTimestamp("start_date").toLocalDateTime())
                        .withAskedPrice(rs.getFloat("asking_price"))
                        .withCryptoCurrency(crypto)
                        .withRatedBuyer(rs.getBoolean("rated_buyer"))
                        .withRatedSeller(rs.getBoolean("rated_seller"))
                        .build();
                } ;
    @Autowired
    public TradeDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcTradeInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("trade").usingGeneratedKeyColumns("trade_id");

    }

    @Override
    public int makeTrade(int offerId, String buyerUsername, float quantity, TradeStatus status) {
        final String getUserIdQuery = "SELECT user_id FROM auth WHERE auth.uname = ?";
        Integer userId;
        try{
            userId = jdbcTemplate.queryForObject(getUserIdQuery, Integer.class, buyerUsername);
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }

        Map<String,Object> args = new HashMap<>();
        args.put("offer_id",offerId);
        args.put("buyer_id",userId);
        args.put("start_date", LocalDateTime.now());
        args.put("status",status.toString());
        args.put("quantity",quantity);

        try {
            return jdbcTradeInsert.executeAndReturnKey(args).intValue();
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }

    @Override
    public int makeTrade(Trade.Builder builder) {
        return makeTrade(builder.getOfferId(), builder.getBuyerUsername(), builder.getQuantity(), builder.getStatus());
    }

    @Override
    public void updateStatus(int tradeId, TradeStatus status) {
        final String query = "UPDATE trade SET status = ? WHERE trade_id = ?";

        try {
            jdbcTemplate.update(query, status.toString(), tradeId);
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }

    @Override
    public Optional<Trade> getTradeById(int tradeId) {
        String query = "SELECT * FROM trade_complete WHERE trade_id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(query, TRADE_COMPLETE_ROW_MAPPER, tradeId));
        } catch(EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }

    @Override
    public Collection<Trade> getSellingTradesByUsername(String username, int page, int pageSize) {
        final String query = "SELECT * FROM trade_complete WHERE seller_uname = ? LIMIT ? OFFSET ?";
        try {
            return Collections.unmodifiableCollection(jdbcTemplate.query(query, TRADE_COMPLETE_ROW_MAPPER, username, pageSize, pageSize * page));
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }

    @Override
    public Collection<Trade> getBuyingTradesByUsername(String username, int page, int pageSize) {
        final String query = "SELECT * FROM trade_complete WHERE buyer_uname = ? LIMIT ? OFFSET ?";

        try {
            return Collections.unmodifiableCollection(jdbcTemplate.query(query, TRADE_COMPLETE_ROW_MAPPER, username, pageSize, pageSize*page));
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }

    @Override
    public int getSellingTradesByUsernameCount(String username) {
        final String query = "SELECT COUNT(*) FROM trade_complete WHERE seller_uname = ?";

        try {
            return jdbcTemplate.queryForObject(query, new Object[]{username}, Integer.class);
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }

    @Override
    public int getBuyingTradesByUsername(String username) {
        final String query = "SELECT COUNT(*) FROM trade_complete WHERE buyer_uname = ?";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{username}, Integer.class);
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }

    @Override
    public Collection<Trade> getTradesByUsername(String username, int page, int pageSize) {
        final String query = "SELECT * FROM trade_complete WHERE (buyer_uname = ? OR seller_uname = ? ) LIMIT ? OFFSET ?";
        try {
            return Collections.unmodifiableCollection(jdbcTemplate.query(query, TRADE_COMPLETE_ROW_MAPPER, username, username, pageSize, pageSize * page));
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }

    @Override
    public int getTradesByUsernameCount(String username) {
        final String query = "SELECT COUNT(*) FROM trade_complete WHERE ( buyer_uname = ? OR seller_uname = ?)";

        try {
            return jdbcTemplate.queryForObject(query, new Object[]{username,username}, Integer.class);
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }
    @Override
    public void rateBuyer(int tradeId){
        final String query = "UPDATE trade SET rated_buyer = true WHERE trade_id = ?";
        try {
            jdbcTemplate.update(query,  tradeId);
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }
    @Override
    public void rateSeller(int tradeId){
        final String query = "UPDATE trade SET rated_seller = true WHERE trade_id = ?";
        try {
            jdbcTemplate.update(query, tradeId);
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }
}

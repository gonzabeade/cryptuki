package ar.edu.itba.paw.persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:tradeInitialState.sql"})
@ContextConfiguration(classes = TestConfig.class)
public class TradeJdbcDaoTest {
    private static final String TRADE_TABLE = "trade";

    private static final int TEST_INDEX = 0;

    private ArrayList<Trade.Builder> trades;
    private HashMap<String, Integer> users;

    @Autowired
    private DataSource ds;

    private TradeDaoImpl tradeJdbcDao;
    private SimpleJdbcInsert jdbcInsert;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(TRADE_TABLE)
                .usingGeneratedKeyColumns("trade_id");
        tradeJdbcDao = new TradeDaoImpl(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, TRADE_TABLE);

        trades = new ArrayList<>();
        trades.add(new Trade.Builder(0, "scastagnino")
                .withQuantity(3.4f)
                .withTradeStatus(TradeStatus.OPEN)
                .withTradeId(0)
                .withSellerUsername("gbeade"));
        trades.add(new Trade.Builder(1, "scastagnino")
                .withQuantity(5.6f)
                .withTradeStatus(TradeStatus.OPEN)
                .withTradeId(1)
                .withSellerUsername("gbeade"));
        trades.add(new Trade.Builder(2, "gbeade")
                .withQuantity(10.8f)
                .withTradeStatus(TradeStatus.OPEN)
                .withTradeId(2)
                .withSellerUsername("scastagnino"));

        users= new HashMap<>();
        users.put("gbeade", 0);
        users.put("scastagnino", 1);
    }

    @Test
    public void makeTradeTest(){
        // Set up
        JdbcTestUtils.deleteFromTables(jdbcTemplate, TRADE_TABLE);

        // Execute
        tradeJdbcDao.makeTrade(trades.get(TEST_INDEX));

        //Validations
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, TRADE_TABLE));

    }



    private void insertTrade(Trade trade){
        HashMap<String, Object> tradeMap = new HashMap<>();

        tradeMap.put("trade_id", trade.getTradeId());
        tradeMap.put("offer_id", trade.getOfferId());
        tradeMap.put("buyer_id", users.get(trade.getBuyerUsername()));
        tradeMap.put("seller_id", users.get(trade.getSellerUsername()));
        tradeMap.put("quantity", trade.getQuantity());
        tradeMap.put("status", trade.getStatus().toString());
        tradeMap.put("rated_buyer",false);
        tradeMap.put("rated_seller",false);

        jdbcInsert.execute(tradeMap);

    }
}

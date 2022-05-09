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
        trades.add(new Trade.Builder(0, "scastagnino@itba.edu.ar")
                .withQuantity((float)3.4)
                .withTradeStatus(TradeStatus.OPEN)
                .withTradeId(0)
                .withSellerUsername("gbeade@itba.edu.ar"));
        trades.add(new Trade.Builder(1, "scastagnino@itba.edu.ar")
                .withQuantity((float)5.6)
                .withTradeStatus(TradeStatus.OPEN)
                .withTradeId(1)
                .withSellerUsername("gbeade@itba.edu.ar"));
        trades.add(new Trade.Builder(2, "gbeade@itba.edu.ar")
                .withQuantity((float)10.8)
                .withTradeStatus(TradeStatus.OPEN)
                .withTradeId(2)
                .withSellerUsername("scastagnino@itba.edu.ar"));

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

    @Test
    public void getTradeById(){
        // Set up
        JdbcTestUtils.deleteFromTables(jdbcTemplate, TRADE_TABLE);
        for(Trade.Builder trade : trades){
            insertTrade(trade.build());
        }

        //Execute
        Optional<Trade> testedTrade = tradeJdbcDao.getTradeById(trades.get(TEST_INDEX).getTradeId());

        //Validations
        Assert.assertTrue(testedTrade.isPresent());
        Assert.assertEquals(trades.get(TEST_INDEX).build(), testedTrade);

    }

    @Test
    public void getSellingTradesByUsernamePageTest(){
        // Set up
        JdbcTestUtils.deleteFromTables(jdbcTemplate, TRADE_TABLE);
        for(Trade.Builder trade : trades){
            insertTrade(trade.build());
        }

        // Execute
        Collection<Trade> testedTrades = tradeJdbcDao.getSellingTradesByUsername("gbeade", 1, 2);

        //Validations
        Assert.assertNotNull(testedTrades);
        Assert.assertEquals(2, testedTrades.size());
        Assert.assertTrue(testedTrades.contains(trades.get(0).build()));
        Assert.assertTrue(testedTrades.contains(trades.get(1).build()));
    }

    @Test
    public void getSellingTradesByUsernameCountTest(){
        // Set up
        JdbcTestUtils.deleteFromTables(jdbcTemplate, TRADE_TABLE);
        for(Trade.Builder trade : trades){
            insertTrade(trade.build());
        }

        // Execute
        int testedCount = tradeJdbcDao.getSellingTradesByUsernameCount("scastagnino");

        //Validations
        Assert.assertEquals(1, testedCount);
    }

    @Test
    public void getBuyingTradesByUsernameByPageTest(){
        // Set up
        JdbcTestUtils.deleteFromTables(jdbcTemplate, TRADE_TABLE);
        for(Trade.Builder trade : trades){
            insertTrade(trade.build());
        }

        // Execute
        Collection<Trade> testedTrades = tradeJdbcDao.getBuyingTradesByUsername("gbeade", 1, 3);

        //Validations
        Assert.assertNotNull(testedTrades);
        Assert.assertEquals(1, testedTrades.size());
        Assert.assertTrue(testedTrades.contains(trades.get(2).build()));
    }

    @Test
    public void getBuyingTradesByUsernameTest(){
        // Set up
        JdbcTestUtils.deleteFromTables(jdbcTemplate, TRADE_TABLE);
        for(Trade.Builder trade : trades){
            insertTrade(trade.build());
        }

        // Execute
        int testedCount = tradeJdbcDao.getBuyingTradesByUsername("scastagnino");

        //Validations
        Assert.assertEquals(2, testedCount);
    }

    @Test
    public void getTradesByUsernameTest(){
        // Set up
        JdbcTestUtils.deleteFromTables(jdbcTemplate, TRADE_TABLE);
        for(Trade.Builder trade : trades){
            insertTrade(trade.build());
        }

        // Execute
        Collection<Trade> testedTrades = tradeJdbcDao.getTradesByUsername("scastagnino", 1, 2);

        //Validations
        Assert.assertNotNull(testedTrades);
        Assert.assertEquals(2, testedTrades.size());
        Assert.assertTrue(testedTrades.contains(trades.get(0).build()));
        Assert.assertTrue(testedTrades.contains(trades.get(1).build()));
    }

    @Test
    public void getTradesByUsernameCountTest(){
        // Set up
        JdbcTestUtils.deleteFromTables(jdbcTemplate, TRADE_TABLE);
        for(Trade.Builder trade : trades){
            insertTrade(trade.build());
        }

        // Execute
        int testedCount = tradeJdbcDao.getTradesByUsernameCount("gbeade");

        //Validations
        Assert.assertEquals(3, testedCount);
    }

    private void insertTrade(Trade trade){
        HashMap<String, Object> tradeMap = new HashMap<>();

        tradeMap.put("trade_id", trade.getTradeId());
        tradeMap.put("offer_id", trade.getOfferId());
        tradeMap.put("buyer_id", users.get(trade.getBuyerUsername()));
        tradeMap.put("seller_id", users.get(trade.getSellerUsername()));
        tradeMap.put("quantity", trade.getQuantity());
        tradeMap.put("status", trade.getStatus().toString());

        jdbcInsert.execute(tradeMap);

    }
}

package ar.edu.itba.paw.persistence;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:tradeCompleteInitialState.sql"})
@ContextConfiguration(classes = TestConfig.class)
public class TradeCompleteTest {

    private static final String TRADE_VIEW = "trade_complete";
    @Autowired
    private DataSource ds;

    @Autowired
    private TradeJdbcDao tradeJdbcDao;
    private JdbcTemplate jdbcTemplate;


    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
    }



    @Test
    public void getTradeById(){
        int tradeId = 0 ;

        //Execute
        Optional<Trade> testedTrade = tradeJdbcDao.getTradeById(tradeId);

        //Validations
        Assert.assertTrue(testedTrade.isPresent());
        Assert.assertEquals(tradeId, testedTrade.get().getTradeId());

    }


    @Test
    public void getAllTradesByUsername(){

        // Execute
        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate,TRADE_VIEW);
        String username = "gbeade";
        Collection<Trade> testedTrades = tradeJdbcDao.getTradesByUsername(username, 0, rows);

        //Validations
        Assert.assertNotNull(testedTrades);
        for(Trade trade : testedTrades)
            Assert.assertTrue(trade.getBuyerUsername().equals(username) || trade.getSellerUsername().equals(username));

    }



    @Test
    public void getTradesByUsernameCountTest(){

        // Execute
        int testedCount = tradeJdbcDao.getTradesByUsernameCount("gbeade");

        //Validations
        Assert.assertEquals(3, testedCount);
    }




}

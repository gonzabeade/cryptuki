package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.model.*;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:tradeCompleteInitialState.sql"})
@ContextConfiguration(classes = TestConfig.class)
public class TradeCompleteTest {

    private static final String TRADE_VIEW = "trade_complete";
    private static final String TRADE_TABLE = "trade";

    private ArrayList<User> users;
    private ArrayList<UserAuth> userAuths;
    private ArrayList<Offer> offers;
    private OfferFilter testingFilter;
    private Offer offer;
    private ArrayList<Trade> trades;

    @Autowired
    private DataSource ds;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private TradeHibernateDao tradeHibernateDao;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;


    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(TRADE_TABLE);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, TRADE_TABLE);

        userAuths = new ArrayList<>();
        userAuths.add(new UserAuth(0, "gbeade", "pass_gbeade", 1234));
        userAuths.add(new UserAuth(1, "scastagnino", "pass_scastagnino", 4321));
        userAuths.add(new UserAuth(2, "mdedeu", "pass_mdedeu", 4322));

        users = new ArrayList<>();
        users.add(new User("gbeade@itba.edu.ar", "87654321", 4, 20, Locale.forLanguageTag("en-US")));
        users.get(0).setUserAuth(userAuths.get(0));
        users.add(new User("scastagnino@itba.edu.ar", "12345678", 7, 14, Locale.forLanguageTag("en-US")));
        users.get(1).setUserAuth(userAuths.get(1));
        users.add(new User("mdedeu@itba.edu.ar", "87654321", 7, 14, Locale.forLanguageTag("en-US")));
        users.get(2).setUserAuth(userAuths.get(2));

        offer = new Offer.Builder(10, 50, 100).withSeller(users.get(0)).build();

        trades = new ArrayList<>();
        trades.add(new Trade(offer, users.get(1), 43));
        trades.add(new Trade(offer, users.get(2), 43));

        testingFilter = new OfferFilter();
    }

    @Test
    public void getTradeById(){
        // Set up
        JdbcTestUtils.deleteFromTables(jdbcTemplate, TRADE_TABLE);
        for(int i = 0; i < trades.size(); i++)
            insertTrade(trades.get(i), i);

        //Execute
        Optional<Trade> testedTrade = tradeHibernateDao.getTradeById(0);

        //Validations
        Assert.assertTrue(testedTrade.isPresent());
        Assert.assertEquals(0, testedTrade.get().getTradeId());
    }

    private void insertTrade(Trade trade, int id){
        HashMap<String, Object> tradeMap = new HashMap<>();

        tradeMap.put("trade_id", id);
        tradeMap.put("offer_id", trade.getOffer().getOfferId());
        tradeMap.put("buyer_id", trade.getBuyer().getId());
        tradeMap.put("quantity", trade.getQuantity());
        tradeMap.put("rated_buyer", true);
        tradeMap.put("rated_seller", true);
        tradeMap.put("q_unseen_msg_buyer", 0);
        tradeMap.put("q_unseen_msg_seller", 0);

        jdbcInsert.execute(tradeMap);

    }
}

package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.parameterObject.ComplainPO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:complainInitialState.sql"})
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Rollback
public class ComplainHibernateDaoTest {
    private static final String COMPLAIN_TABLE = "complain";
    private static final String COMPLAIN_VIEW="complain_complete";
    private static final int TEST_INDEX = 0;

    private ArrayList<Complain> complains;
    private ArrayList<User> users;
    private ArrayList<UserAuth> userAuths;
    private ComplainFilter filter;

    @Autowired
    private DataSource ds;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ComplainHibernateDao complainHibernateDao;
    private SimpleJdbcInsert jdbcInsert;
    private JdbcTemplate jdbcTemplate;
    private Offer complainedOffer;
    private Trade complainedTrade;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(COMPLAIN_TABLE);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, COMPLAIN_TABLE);

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

        complainedOffer = new Offer.Builder(10, 50, 100).withSeller(users.get(0)).build();
        complainedTrade = new Trade(complainedOffer, users.get(1), 43);

        complains = new ArrayList<>();
        complains.add(new Complain
                .Builder(complainedTrade, users.get(0))
                .withModerator(users.get(2))
                .withModeratorComments("The complaint will be answered soon")
                .withComplainerComments("There was a problem with the offer")
                .withStatus(ComplainStatus.CLOSED)
                .build()
        );

        complains.add(new Complain
                .Builder(complainedTrade, users.get(1))
                .withModeratorComments("The complaint will be answered soon")
                .withComplainerComments("There was a problem with the offer")
                .withStatus(ComplainStatus.PENDING)
                .build()
        );

        filter = new ComplainFilter();

    }

    @Test
    public void testGetComplainsBy(){
        // Set up
        JdbcTestUtils.deleteFromTables(jdbcTemplate, COMPLAIN_TABLE);
        for(int i = 0; i < complains.size(); i++){
            insertComplain(complains.get(i), i);
        }
        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate,COMPLAIN_VIEW);

        // Execute
        Collection<Complain> testedComplains = complainHibernateDao.getComplainsBy(filter.withPageSize(rows));

        // Validations
        Assert.assertNotNull(testedComplains);
        Assert.assertEquals(1, testedComplains.size());
    }

    @Test
    public void testGetComplainCount(){
        // Set up
        JdbcTestUtils.deleteFromTables(jdbcTemplate, COMPLAIN_TABLE);
        for(int i = 0; i < complains.size(); i++){
            insertComplain(complains.get(i), i);
        }

        em.flush();

        // Execute
        long testedCount = complainHibernateDao.getComplainCount(filter);

        // Validations
        Assert.assertEquals(1, testedCount);
    }

    @Test
    public void makeComplainTest(){
        // Set up
        JdbcTestUtils.deleteFromTables(jdbcTemplate, COMPLAIN_TABLE);

        // Execute
        Complain testedComplain = complainHibernateDao.makeComplain(new ComplainPO(TEST_INDEX, complains.get(TEST_INDEX).getComplainer().getUsername().get()));

        em.flush();

        // Validations
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate,COMPLAIN_TABLE));
        Assert.assertEquals(TEST_INDEX, testedComplain.getTrade().getTradeId());
        Assert.assertEquals(complains.get(TEST_INDEX).getComplainer().getUsername().get(), testedComplain.getComplainer().getUsername().get());
    }

    @Test
    public void testCloseComplain(){
        // Set up
        JdbcTestUtils.deleteFromTables(jdbcTemplate, COMPLAIN_TABLE);
        insertComplain(complains.get(1), 0);

        // Execute
        Complain testedComplain = complainHibernateDao.closeComplain(0, "mdedeu", "Se cerro la complaint").get();

        // Validations
        Assert.assertEquals(testedComplain.getModerator().get().getUsername(), users.get(2).getUsername());
        Assert.assertEquals(testedComplain.getStatus(), ComplainStatus.CLOSED);
    }

    private void insertComplain(Complain complain, int id){
        HashMap<String, Object> complainMap = new HashMap<>();

        complainMap.put("complain_id", id);
        complainMap.put("trade_id", complain.getTrade().getTradeId());
        complainMap.put("complainer_id", complain.getComplainer().getId());
        complainMap.put("complainer_comments", complain.getComplainerComments());
        complainMap.put("moderator_comments", complain.getModeratorComments());
        complainMap.put("complain_date", "2022-05-01 02:08:03");
        if(complain.getModerator().isPresent())
            complainMap.put("moderator_id", complain.getModerator().get().getId());
        complainMap.put("status", complain.getStatus().toString());

        jdbcInsert.execute(complainMap);

    }

}

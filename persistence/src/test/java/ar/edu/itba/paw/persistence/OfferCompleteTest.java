package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.*;
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
import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:offerCompleteInitialState.sql"})
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Rollback
public class OfferCompleteTest {

    private static final String OFFER_VIEW = "offer_complete";
    private static final String OFFER_TABLE = "offer";

    private ArrayList<User> users;
    private ArrayList<UserAuth> userAuths;
    private ArrayList<Offer> offers;
    private OfferFilter testingFilter;

    @Autowired
    private DataSource ds;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private OfferHibernateDao offerHibernateDao;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(OFFER_TABLE);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, OFFER_TABLE);

        userAuths = new ArrayList<>();
        userAuths.add(new UserAuth(0, "gbeade", "pass_gbeade", 1234));
        userAuths.add(new UserAuth(1, "scastagnino", "pass_scastagnino", 4321));

        users = new ArrayList<>();
        users.add(new User("gbeade@itba.edu.ar", "87654321", 4, 20, Locale.forLanguageTag("en-US")));
        users.get(0).setUserAuth(userAuths.get(0));
        users.add(new User("scastagnino@itba.edu.ar", "12345678", 7, 14, Locale.forLanguageTag("en-US")));
        users.get(1).setUserAuth(userAuths.get(1));

        offers = new ArrayList<>();
        offers.add(new Offer.Builder(10, 50, 100).withSeller(users.get(0)).build());
        offers.get(0).setOfferStatus(OfferStatus.APR);
        offers.add(new Offer.Builder(134, 5, 48).withSeller(users.get(1)).build());
        offers.get(1).setOfferStatus(OfferStatus.APR);

        testingFilter = new OfferFilter();
    }


    @Test
    public void TestGetOfferCount(){
        // Set up
        JdbcTestUtils.deleteFromTables(jdbcTemplate, OFFER_TABLE);
        for(int i = 0; i < offers.size(); i++)
            insertOffer(offers.get(i), i);

        // Exercise
        long tested_count = offerHibernateDao.getOfferCount(testingFilter);

        em.flush();

        // Validations
        Assert.assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate,OFFER_VIEW), tested_count);
    }

    @Test
    public void TestGetOffersByPageSize(){
        // Set up
        JdbcTestUtils.deleteFromTables(jdbcTemplate, OFFER_TABLE);
        for(int i = 0; i < offers.size(); i++)
            insertOffer(offers.get(i), i);
        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate,OFFER_VIEW);
        testingFilter=new OfferFilter().withPageSize(rows);

        // Exercise
        Collection<Offer> testedOffers = offerHibernateDao.getOffersBy(testingFilter);

        // Validations
        Assert.assertNotNull(testedOffers);
        Assert.assertEquals(rows,testedOffers.size());
    }

    @Test
    public void TestGetOffersByUser(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate, OFFER_TABLE);
        for(int i = 0; i < offers.size(); i++)
            insertOffer(offers.get(i), i);
        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate,OFFER_VIEW);
        testingFilter=new OfferFilter().withPageSize(rows).restrictedToUsername(userAuths.get(0).getUsername());
        OfferFilter filter = new OfferFilter().withPageSize(rows);

        // Exercise
        ArrayList<Offer> testedOffers = new ArrayList<>(offerHibernateDao.getOffersBy(filter));

        // Validations
        Assert.assertNotNull(testedOffers);
        Assert.assertEquals(1, testedOffers.size());
        Assert.assertEquals(userAuths.get(0).getUsername(), testedOffers.get(0).getSeller().getUsername());
    }

    @Test
    public void TestGetOffersByPaymentMethods(){
//        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate,OFFER_VIEW);
//        PaymentMethod paymentMethod = PaymentMethod.getInstance("mp","mercado pago");
//        testingFilter=new OfferFilter().withPageSize(rows).byPaymentMethod(paymentMethod.getName());
//
//        // Exercise
//        List<Offer> testedOffers = offerHibernateDao.getOffersBy(testingFilter).stream().collect(Collectors.toCollection(ArrayList::new));
//
//        // Validations
//        Assert.assertNotNull(testedOffers);
//        for(Offer offer : testedOffers)
//            Assert.assertTrue(offer.getPaymentMethods().contains(paymentMethod));
    }
    @Test
    public void testGetOffersByPageAndPageSize(){
//        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate,OFFER_VIEW);
//        testingFilter=new OfferFilter().withPageSize(rows-1).fromPage(0);
//
//        // Exercise
//        Collection<Offer> testedOffers = offerHibernateDao.getOffersBy(testingFilter);
//
//        // Validations
//        Assert.assertNotNull(testedOffers);
//        Assert.assertEquals(rows-1,testedOffers.size());
    }

    @Test
    public void testMakeOffer(){

    }

    @Test
    public void testChangeOfferStatus(){

    }


    private void insertOffer(Offer offer, int id){
        HashMap<String, Object> offerMap = new HashMap<>();

        offerMap.put("offer_id", id);
        offerMap.put("offer_date", "2022-05-01 02:08:03");
        offerMap.put("max_quantity", offer.getMaxInCrypto());
        offerMap.put("min_quantity", offer.getMinInCrypto());
        offerMap.put("status_code", offer.getOfferStatus());
        offerMap.put("asking_price", offer.getUnitPrice());

        jdbcInsert.execute(offerMap);

    }


}

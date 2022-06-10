package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.OfferFilter;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:offerCompleteInitialState.sql"})
@ContextConfiguration(classes = TestConfig.class)
public class OfferCompleteTest {
//
//    private static final String OFFER_VIEW = "offer_complete";
//    private static final String STATUS_CODE = "APR";
//    private static final String STATUS_DESC = "Approved";
//    private static final float DELTA = 0.0000000000001f;
//
//    private ArrayList<Offer> offers;
//    private OfferFilter testingFilter;
//
//    @Autowired
//    private DataSource ds;
//
//    @Autowired
//    private OfferJdbcDao offerJdbcDao;
//    private JdbcTemplate jdbcTemplate;
//
//    @Before
//    public void setUp(){
//        jdbcTemplate = new JdbcTemplate(ds);
//        testingFilter = new OfferFilter();
//    }
//
//
//    @Test
//    public void TestGetOfferCount(){
//
//        // Exercise
//        int tested_count = offerJdbcDao.getOfferCount(testingFilter);
//
//        // Validations
//        Assert.assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate,OFFER_VIEW), tested_count);
//    }
//
//    @Test
//    public void TestGetOffersByPageSize(){
//        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate,OFFER_VIEW);
//        testingFilter=new OfferFilter().withPageSize(rows);
//
//        // Exercise
//        Collection<Offer> testedOffers = offerJdbcDao.getOffersBy(testingFilter);
//
//        // Validations
//        Assert.assertNotNull(testedOffers);
//        Assert.assertEquals(rows,testedOffers.size());
//    }
//
//    @Test
//    public void TestGetOffersByUserId(){
//        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate,OFFER_VIEW);
//        int askedId = 0 ;
//        testingFilter=new OfferFilter().withPageSize(rows).byOfferId(askedId);
//
//        // Exercise
//        List<Offer> testedOffers = offerJdbcDao.getOffersBy(testingFilter).stream().collect(Collectors.toCollection(ArrayList::new));
//
//        // Validations
//        Assert.assertNotNull(testedOffers);
//        Assert.assertEquals(testedOffers.size(),1);
//        Assert.assertEquals(testedOffers.get(0).getId(),askedId);
//    }
//
//    @Test
//    public void TestGetOffersByPaymentMethods(){
//        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate,OFFER_VIEW);
//        PaymentMethod paymentMethod = PaymentMethod.getInstance("mp","mercado pago");
//        testingFilter=new OfferFilter().withPageSize(rows).byPaymentMethod(paymentMethod.getName());
//
//        // Exercise
//        List<Offer> testedOffers = offerJdbcDao.getOffersBy(testingFilter).stream().collect(Collectors.toCollection(ArrayList::new));
//
//        // Validations
//        Assert.assertNotNull(testedOffers);
//        for(Offer offer : testedOffers)
//            Assert.assertTrue(offer.getPaymentMethods().contains(paymentMethod));
//    }
//    @Test
//    public void testGetOffersByPageAndPageSize(){
//        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate,OFFER_VIEW);
//        testingFilter=new OfferFilter().withPageSize(rows-1).fromPage(0);
//
//        // Exercise
//        Collection<Offer> testedOffers = offerJdbcDao.getOffersBy(testingFilter);
//
//        // Validations
//        Assert.assertNotNull(testedOffers);
//        Assert.assertEquals(rows-1,testedOffers.size());
//    }




}

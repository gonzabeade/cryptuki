package ar.edu.itba.paw;

import ar.edu.itba.paw.persistence.OfferJdbcDao;
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
import java.util.Date;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class OfferJdbcDaoTest {

    @Autowired
    private DataSource ds;

    @Autowired
    private OfferJdbcDao offerJdbcDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"offers");
    }

    @Test
    public void TestMakeOffer(){
        Offer offer = new Offer(1,2,new Date(),"arg",15,16);

        Offer tableOffer= offerJdbcDao.makeOffer(offer.getSellerId(),offer.getDate(),offer.getCoin_id(),offer.getAskingPrice(),offer.getCoinAmount());

     //   Assert.assertEquals(offer.getOffer_id(),tableOffer.getOffer_id());
        Assert.assertEquals(1,JdbcTestUtils.countRowsInTable(jdbcTemplate,"offers"));

    }

    @Test
    public void TestgetAllOffers(){
    }




}

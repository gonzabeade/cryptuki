package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.OfferDigest;
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
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:offerInitialState.sql"})
@ContextConfiguration(classes = TestConfig.class)
public class OfferJdbcDaoTest {

    private static final String OFFER_TABLE = "offer";
    private static final String OFFER_VIEW = "offer_complete";
    private static final String STATUS_CODE = "APR";
    private static final String STATUS_DESC = "Approved";
    private static final float DELTA = 0.0000000000001f;

    private ArrayList<Offer> offers;
    private OfferFilter testingFilter;

    @Autowired
    private DataSource ds;

    @Autowired
    private OfferJdbcDao offerJdbcDao;
    private SimpleJdbcInsert jdbcInsert;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(OFFER_TABLE)
                .usingGeneratedKeyColumns("id");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,OFFER_TABLE);
        offers = new ArrayList<>(Arrays.asList(

                new Offer.Builder(0, new User.Builder("gbeade@itba.edu.ar").withId(0).build(),
                        Cryptocurrency.getInstance("ETH", "Ether"),
                        54.0f).withDate(LocalDateTime.now()).withStatus(OfferStatus.getInstance(STATUS_CODE, STATUS_DESC))
                        .withPaymentMethod(PaymentMethod.getInstance("Cash","Paper Money"))
                        .build(),
                new Offer.Builder(1, new User.Builder("shadad@itba.edu.ar").withId(1).build(),
                        Cryptocurrency.getInstance("ADA", "Cardano"),
                        9.0f).withDate(LocalDateTime.now()).withStatus(OfferStatus.getInstance(STATUS_CODE, STATUS_DESC))
                        .withPaymentMethod(PaymentMethod.getInstance("Cash","Paper Money"))
                        .build(),
                new Offer.Builder(2, new User.Builder("mdedeu@itba.edu.ar").withId(2).build(),
                        Cryptocurrency.getInstance("DOT", "Polkadot"),
                        2.65f).withDate(LocalDateTime.now()).withStatus(OfferStatus.getInstance(STATUS_CODE, STATUS_DESC))
                        .withPaymentMethod(PaymentMethod.getInstance("Cash","Paper Money"))
                        .build()
        ));

        testingFilter = new OfferFilter();

    }

    @Test
    public void TestMakeOffer(){
        // Setup
        JdbcTestUtils.deleteFromTables(jdbcTemplate, OFFER_TABLE);
        OfferDigest offerDigest = new OfferDigest.Builder( 0, "ETH", 54.0)
                .withMinQuantity(10)
                .withMaxQuantity(20)
                .withPaymentMethod("Cash")
                .withComments("This is a test for the offer dao")
                .build();

        // Exercise
        offerJdbcDao.makeOffer(offerDigest);

        // Validations
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, OFFER_TABLE));
    }


    private void insertOffer(Offer offer){
        HashMap<String, Object> offerMap = new HashMap<>();

        offerMap.put("offer_date", LocalDateTime.now());
        offerMap.put("crypto_code", offer.getCrypto().getCode());
        offerMap.put("status_code", offer.getStatus().getCode());
        offerMap.put("asking_price", offer.getAskingPrice());
        offerMap.put("min_quantity", offer.getMinQuantity());
        offerMap.put("max_quantity", offer.getMaxQuantity());
        offerMap.put("comments", offer.getComments());
        offerMap.put("seller_id", offer.getSeller().getId());
        jdbcInsert.executeAndReturnKey(offerMap).intValue();
    }

    private void assertOffer(Offer originalOffer, Offer testedOffer){
        Assert.assertEquals(originalOffer.getAskingPrice(), testedOffer.getAskingPrice(), DELTA);

        Assert.assertEquals(originalOffer.getSeller().getEmail(), testedOffer.getSeller().getEmail());
        Assert.assertEquals(originalOffer.getSeller().getId(), testedOffer.getSeller().getId());

        Assert.assertEquals(originalOffer.getCrypto().getCode(), testedOffer.getCrypto().getCode());
        Assert.assertEquals(originalOffer.getCrypto().getCommercialName(), testedOffer.getCrypto().getCommercialName());

        Assert.assertEquals(originalOffer.getMaxQuantity(), testedOffer.getMaxQuantity(), DELTA);
        Assert.assertEquals(originalOffer.getMaxQuantity(), testedOffer.getMaxQuantity(), DELTA);

        Assert.assertEquals(originalOffer.getComments(), testedOffer.getComments());
    }

}

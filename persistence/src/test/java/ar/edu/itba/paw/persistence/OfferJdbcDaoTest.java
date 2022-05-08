package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.OfferDigest;
import ar.edu.itba.paw.OfferFilter;
import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

//TODO: script sql especial para poblar la bd con tablas de las que dependa offer, por fk por ejemplo
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:offerInitialState.sql"})
@ContextConfiguration(classes = TestConfig.class)
public class OfferJdbcDaoTest {

    private static final String OFFER_TABLE = "offer";
    private static final String OFFER_VIEW = "offer_complete";
    private static final String STATUS_CODE = "APR";
    private static final int OFFER_COUNT = 3;
    private static final int TESTING_OFFER_INDEX = 2;
    private static final int TESTING_FILTER_INDEX= 0;

    private ArrayList<Offer> offers;
    private OfferFilter testingFitler;

    @Autowired
    private DataSource ds;

    private OfferJdbcDao offerJdbcDao;
    private SimpleJdbcInsert jdbcInsert;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(OFFER_TABLE)
                .usingGeneratedKeyColumns("id");
        offerJdbcDao = new OfferJdbcDao(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"offer");

        //TODO: mirar de agregar campos opcionales al builder
        //TODO: cuando se buildea una offer se esta craeando un Builder de la offer, este guarda el horario actual,
        //TODO: puede haber algun problema con el tema del determinismo (ya que las horas son diferentes)?
        //TODO: mirar que hacer con el date type que no me lo estaba tomando el hsqldb
        offers = new ArrayList<>(Arrays.asList(
                new Offer.Builder(0, new User.Builder("gbeade@itba.edu.ar").withId(0).build(),
                        Cryptocurrency.getInstance("ETH", "Ether"),
                        (float)54.0).build(),
                new Offer.Builder(1, new User.Builder("shadad@itba.edu.ar").withId(1).build(),
                        Cryptocurrency.getInstance("ADA", "Cardano"),
                        (float)9.0).build(),
                new Offer.Builder(2, new User.Builder("mdedeu@itba.edu.ar").withId(2).build(),
                        Cryptocurrency.getInstance("DOT", "Polkadot"),
                        (float)2.65).build(),
                new Offer.Builder(3, new User.Builder("scastagnino@itba.edu.ar").withId(3).build(),
                        Cryptocurrency.getInstance("BTC", "Bitcoin"),
                        (float)22.2).build()
        ));

        testingFitler = new OfferFilter();

    }

    @Test
    public void TestMakeOffer(){
        // Setup
        JdbcTestUtils.deleteFromTables(jdbcTemplate, OFFER_TABLE);
        OfferDigest offerDigest = new OfferDigest.Builder( 0, "ETH", 11.2).build();

        // Exercise
        int offerId = offerJdbcDao.makeOffer(offerDigest);

        // Validations
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, OFFER_TABLE));
    }

    @Test
    public void TestGetOfferCount(){
        // Setup
        JdbcTestUtils.deleteFromTables(jdbcTemplate, OFFER_TABLE);
        for(int i=0; i<OFFER_COUNT; i++){
            insertOffer(offers.get(i), i);
        }

        // Exercise
        int tested_count = offerJdbcDao.getOfferCount(testingFitler);

        // Validations
        Assert.assertEquals(1, tested_count);

    }

    @Test
    public void TestGetOffersBy(){
        // Setup
        JdbcTestUtils.deleteFromTables(jdbcTemplate, OFFER_TABLE);
        for(int i=0; i<OFFER_COUNT; i++){
            insertOffer(offers.get(i), i);
        }

        // Exercise
        Collection<Offer> testedOffers = offerJdbcDao.getOffersBy(testingFitler);

        // Validations
        Assert.assertNotNull(testedOffers);
        for (Offer testedOffer : testedOffers)
            Assert.assertNotNull(testedOffer);

        Iterator<Offer> originalIterator = offers.iterator();
        Iterator<Offer> testedIterator = testedOffers.iterator();
        while(originalIterator.hasNext()){
            Assert.assertEquals(originalIterator.hasNext(), testedIterator.hasNext());
            Offer testedOffer = testedIterator.next();
            System.out.println("Seller:" + testedOffer.getSeller());
            System.out.println("Offer id:" + testedOffer.getId());
            assertOffer(originalIterator.next(), testedOffer);
        }
        Assert.assertEquals(originalIterator.hasNext(), testedIterator.hasNext());
    }

    private void insertOffer(Offer offer, int i){
        HashMap<String, Object> offerMap = new HashMap<>();

        offerMap.put("seller_id", i);
        offerMap.put("offer_date", new java.sql.Timestamp(new java.sql.Date(0).getTime()));
        offerMap.put("crypto_code", offer.getCrypto().getCode());
        offerMap.put("status_code", STATUS_CODE);
        offerMap.put("asking_price", offer.getAskingPrice());
        //offerMap.put("quantity", offer.getCoinAmount());

        jdbcInsert.execute(offerMap);

    }

    //TODO: preguntar si esta bien usar un metodo auxiliar para no repetir codigo en los unit tests
    //TODO: mirar que tantos AssertNotNull tengo que poner
    //TODO: mirar que hacer con los metodos deprecados mirar que hacer con los metodos deprecados
    private void assertOffer(Offer originalOffer, Offer testedOffer){
        //Assert.assertEquals(originalOffer.getAskingPrice(), testedOffer.getAskingPrice());

        Assert.assertEquals(originalOffer.getSeller().getEmail(), testedOffer.getSeller().getEmail());
        Assert.assertEquals(originalOffer.getSeller().getId(), testedOffer.getSeller().getId());

        //Assert.assertEquals(originalOffer.getCrypto().getMarketPrice(), testedOffer.getCrypto().getMarketPrice());
        Assert.assertEquals(originalOffer.getCrypto().getCode(), testedOffer.getCrypto().getCode());
        //Assert.assertEquals(originalOffer.getCrypto().getName(), testedOffer.getCrypto().getName());

    }

    //CAMBIOS EN EL DAO: (1) agregar al make parametros que faltan (2) poner un tipo valido para el timestamp (3)ver que status_code es not nullable
}

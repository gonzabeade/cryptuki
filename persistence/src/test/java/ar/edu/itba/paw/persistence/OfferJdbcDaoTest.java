package ar.edu.itba.paw.persistence;

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
    private static final String STATUS_CODE = "APR";
    private static final int OFFER_COUNT = 4;
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
                Offer.builder(User.builder().id(0).email("gbeade@itba.edu.ar").build(),
                        Cryptocurrency.getInstance("ETH", "Ether", 54.3),
                        54.0).quantity(40).build(),
                Offer.builder(User.builder().id(1).email("shadad@itba.edu.ar").build(),
                        Cryptocurrency.getInstance("ADA", "Cardano", 10.4),
                        9).quantity(30).build(),
                Offer.builder(User.builder().id(2).email("mdedeu@itba.edu.ar").build(),
                        Cryptocurrency.getInstance("DOT", "Polkadot", 2.0),
                        2.65).quantity(20).build(),
                Offer.builder(User.builder().id(3).email("scastagnino@itba.edu.ar").build(),
                        Cryptocurrency.getInstance("BTC", "Bitcoin", 22.1),
                        22.2).quantity(10).build()
        ));

        testingFitler = new OfferFilter().byOfferId(0);
        testingFitler.close();

    }

    @Test
    public void TestMakeOffer(){
        // Setup
        JdbcTestUtils.deleteFromTables(jdbcTemplate, OFFER_TABLE);
        Offer.Builder offerBuilder = Offer.builder(offers.get(TESTING_OFFER_INDEX).getSeller(), offers.get(TESTING_OFFER_INDEX).getCrypto(), offers.get(TESTING_OFFER_INDEX).getAskingPrice()).quantity(offers.get(TESTING_OFFER_INDEX).getCoinAmount());

        // Exercise
        Offer testedOffer = offerJdbcDao.makeOffer(offerBuilder);

        // Validations
        System.out.println("Email de oferta:" + testedOffer.getSeller());
        Assert.assertNotNull(testedOffer);
        assertOffer(offers.get(TESTING_OFFER_INDEX), testedOffer);
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
        offerMap.put("quantity", offer.getCoinAmount());

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
        Assert.assertEquals(originalOffer.getCrypto().getName(), testedOffer.getCrypto().getName());

    }

    //CAMBIOS EN EL DAO: (1) agregar al make parametros que faltan (2) poner un tipo valido para el timestamp (3)ver que status_code es not nullable
}

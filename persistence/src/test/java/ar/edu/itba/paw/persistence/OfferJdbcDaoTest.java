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
import java.util.Arrays;
import java.util.List;

//TODO: script sql especial para poblar la bd con tablas de las que dependa offer, por fk por ejemplo
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class OfferJdbcDaoTest {

    private static final String OFFER_TABLE = "offer";
    private ArrayList<Offer> offers;

    @Autowired
    private DataSource ds;

    private OfferJdbcDao offerJdbcDao;
    private SimpleJdbcInsert jdbcInsert;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("offer")
                .usingGeneratedKeyColumns("id");
        offerJdbcDao = new OfferJdbcDao(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"offers");

        //TODO: mirar de agregar campos opcionales al builder
        //TODO: cuando se buildea una offer se esta craeando un Builder de la offer, este guarda el horario actual,
        //TODO: puede haber algun problema con el tema del determinismo (ya que las horas son diferentes)?
        offers = new ArrayList<>(Arrays.asList(
                offerJdbcDao.makeOffer(Offer.builder(User.builder().id(1).email("scastagnino@itba.edu.ar").build(),
                        Cryptocurrency.getInstance("1", "BTC", 22.1),
                        22.2)),
                offerJdbcDao.makeOffer(Offer.builder(User.builder().id(2).email("shadad@itba.edu.ar").build(),
                        Cryptocurrency.getInstance("3", "ADA", 10.4),
                        9)),
                offerJdbcDao.makeOffer(Offer.builder(User.builder().id(3).email("gbeade@itba.edu.ar").build(),
                        Cryptocurrency.getInstance("2", "ETH", 54.3),
                        54.0)),
                offerJdbcDao.makeOffer(Offer.builder(User.builder().id(4).email("mdedeu@itba.edu.ar").build(),
                        Cryptocurrency.getInstance("4", "DOT", 2.0),
                        2.65))
        ));
    }

    @Test
    public void TestMakeOffer(){
        // Setup
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "offer");
        Offer.Builder offerBuilder = Offer.builder(offers.get(1).getSeller(), offers.get(1).getCrypto(), offers.get(1).getAskingPrice());

        // Exercise
        Offer testedOffer = offerJdbcDao.makeOffer(offerBuilder);

        // Validations
        Assert.assertNotNull(testedOffer);
        assertOffer(offers.get(1), testedOffer);
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, OFFER_TABLE));
    }

    @Test
    public void TestGetAllOffers(){
        // Setup
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "offer");

        // Exercise
        List<Offer> testedOffers = offerJdbcDao.getAllOffers();

        // Validations
        Assert.assertNotNull(testedOffers);
        for (Offer testedOffer : testedOffers)
            Assert.assertNotNull(testedOffer);

        Assert.assertEquals(offers.size(), testedOffers.size());

        for(int i = 0; i < offers.size(); i++) {
            assertOffer(offers.get(i), testedOffers.get(i));
        }
    }

    @Test
    public void TestGetOffer(){
        // Setup
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "offer");

        // Exercise
        Offer testedOffer = offerJdbcDao.getOffer(1);

        // Validations
        Assert.assertNotNull(testedOffer);
        assertOffer(offers.get(1), testedOffer);
    }

    //TODO: preguntar si esta bien usar un metodo auxiliar para no repetir codigo en los unit tests
    //TODO: mirar que tantos AssertNotNull tengo que poner
    //TODO: mirar que hacer con los metodos deprecados mirar que hacer con los metodos deprecados
    private void assertOffer(Offer originalOffer, Offer testedOffer){
        Assert.assertEquals(originalOffer.getAskingPrice(), testedOffer.getAskingPrice());

        Assert.assertEquals(originalOffer.getSeller().getEmail(), testedOffer.getSeller().getEmail());
        Assert.assertEquals(originalOffer.getSeller().getId(), testedOffer.getSeller().getId());

        Assert.assertEquals(originalOffer.getCrypto().getMarketPrice(), testedOffer.getCrypto().getMarketPrice());
        Assert.assertEquals(originalOffer.getCrypto().getCode(), testedOffer.getCrypto().getCode());
        Assert.assertEquals(originalOffer.getCrypto().getName(), testedOffer.getCrypto().getName());
    }

}

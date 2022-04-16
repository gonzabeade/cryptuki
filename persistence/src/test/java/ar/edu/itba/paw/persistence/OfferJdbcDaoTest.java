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
        // 1. setup
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "offer");

        //TODO: estas 3 lineas de abajo en las que creo un builder (usando modelos) que voy a usar para el Dao, van en setup?

        //TODO: ver de agregar campos opcionales al builder

        //TODO: cuando se buildea una offer se esta craeando un Builder de la offer, este guarda el horario actual,
        //TODO: puede haber algun problema con el tema del determinismo (ya que las horas son diferentes)?
        Offer.Builder offerBuilder = Offer.builder(offers.get(1).getSeller(), offers.get(1).getCrypto(), offers.get(1).getAskingPrice());

        // 2. ejercitar
        Offer testedOffer = offerJdbcDao.makeOffer(offerBuilder);

        // 3. validaciones
        Assert.assertNotNull(testedOffer);
        //TODO: Juan valida que el User se haya creado correctamente, el tema es que en esta caso crar la Offer es muy parecido a por ejemplo crear una Crypto
        //TODO: y esto lo hice en setup ya que son modelos, deberia validar esto entonces, y la creacion de lo que esta en setup?

        //TODO: mirar que hacer con metodos deprecados, hay que agregar delta
        Assert.assertEquals(offers.get(1).getAskingPrice(), testedOffer.getAskingPrice());

        Assert.assertEquals(offers.get(1).getSeller().getEmail(), testedOffer.getSeller().getEmail());
        Assert.assertEquals(offers.get(1).getSeller().getId(), testedOffer.getSeller().getId());

        Assert.assertEquals(offers.get(1).getCrypto().getMarketPrice(), testedOffer.getCrypto().getMarketPrice());
        Assert.assertEquals(offers.get(1).getCrypto().getCode(), testedOffer.getCrypto().getCode());
        Assert.assertEquals(offers.get(1).getCrypto().getName(), testedOffer.getCrypto().getName());

        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, OFFER_TABLE));
    }

    @Test
    public void TestGetAllOffers(){
        // 1
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "offer");

        // 2
        List<Offer> testedOffers = offerJdbcDao.getAllOffers();

        // 3
        Assert.assertNotNull(testedOffers);
        for (Offer testedOffer : testedOffers)
            Assert.assertNotNull(testedOffer);
        Assert.assertEquals(offers.size(), testedOffers.size());

        for(int i = 0; i < offers.size(); i++) {
            Assert.assertEquals(offers.get(i).getAskingPrice(), testedOffers.get(i).getAskingPrice());

            Assert.assertEquals(offers.get(i).getSeller().getEmail(), testedOffers.get(i).getSeller().getEmail());
            Assert.assertEquals(offers.get(i).getSeller().getId(), testedOffers.get(i).getSeller().getId());

            Assert.assertEquals(offers.get(i).getCrypto().getMarketPrice(), testedOffers.get(i).getCrypto().getMarketPrice());
            Assert.assertEquals(offers.get(i).getCrypto().getCode(), testedOffers.get(i).getCrypto().getCode());
            Assert.assertEquals(offers.get(i).getCrypto().getName(), testedOffers.get(i).getCrypto().getName());
        }
    }

    @Test
    public void TestGetOffer(){
        // 1
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "offer");
        //TODO: creo una fila manualmente para el offer que voy a pedir, uso los mismos datos que user para testear la creacion
        //TODO: mirar como le seteo el user id, que en la original es un serial, lo pongo como default en 1

        // 2
        Offer testedOffer = offerJdbcDao.getOffer(1);

        // 3
        Assert.assertEquals(offers.get(1).getAskingPrice(), testedOffer.getAskingPrice());

        Assert.assertEquals(offers.get(1).getSeller().getEmail(), testedOffer.getSeller().getEmail());
        Assert.assertEquals(offers.get(1).getSeller().getId(), testedOffer.getSeller().getId());

        Assert.assertEquals(offers.get(1).getCrypto().getMarketPrice(), testedOffer.getCrypto().getMarketPrice());
        Assert.assertEquals(offers.get(1).getCrypto().getCode(), testedOffer.getCrypto().getCode());
        Assert.assertEquals(offers.get(1).getCrypto().getName(), testedOffer.getCrypto().getName());

    }




}

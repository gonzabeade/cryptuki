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

//TODO: script sql especial para poblar la bd con tablas de las que dependa offer, por fk por ejemplo
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class OfferJdbcDaoTest {

    private static final String USER_MAIL = "scastagnino@itba.edu.ar";
    private static final int USER_ID = 1;
    private static final String OFFER_TABLE = "offer";
    private static final String CRYPTO_NAME = "BTC";
    private static final double MARKET_PRICE = 22.1;
    private static final String CRYPTO_CODE = "1";
    private static final double ASKING_PRICE = 20.65;
    private static final int OFFER_ID = 1;

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
    }

    @Test
    public void TestMakeOffer(){
        // 1. setup
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "offer");

        //TODO: estas 3 lineas de abajo en las que creo un builder (usando modelos) que voy a usar para el Dao, van en setup?

        //TODO: ver de agregar campos opcionales al builder
        User user = User.builder().id(USER_ID).email(USER_MAIL).build();
        Cryptocurrency crypto = Cryptocurrency.getInstance(CRYPTO_CODE, CRYPTO_NAME, MARKET_PRICE);
        //TODO: cuando se buildea una offer se esta craeando un Builder de la offer, este guarda el horario actual,
        //TODO: puede haber algun problema con el tema del terminismo (ya que las horas sondiferentes)?
        Offer.Builder offerBuilder = Offer.builder(user, crypto, ASKING_PRICE);

        // 2. ejercitar
        Offer offer = offerJdbcDao.makeOffer(offerBuilder);

        // 3. validaciones
        Assert.assertNotNull(offer);
        //TODO: Juan valida que el User se haya creado correctamente, el tema es que en esta caso crar la Offer es muy parecido a por ejemplo crear una Crypto
        //TODO: y esto lo hice en setup ya que son modelos, deberia validar esto entonces, y la creacion de lo que esta en setup?

        //TODO: mirar que hacer con metodos deprecados, hay que agregar delta
        Assert.assertEquals(ASKING_PRICE, offer.getAskingPrice());

        Assert.assertEquals(USER_MAIL, offer.getSeller().getEmail());
        Assert.assertEquals(USER_ID, offer.getSeller().getId());

        Assert.assertEquals(MARKET_PRICE, offer.getCrypto().getMarketPrice());
        Assert.assertEquals(CRYPTO_CODE, offer.getCrypto().getCode());
        Assert.assertEquals(CRYPTO_NAME, offer.getCrypto().getName());

        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, OFFER_TABLE));
    }

    @Test
    public void TestGetAllOffers(){
        //TODO: ver si poner un sql aparte para
    }

    @Test
    public void TestGetOffer(){
        // 1
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "offer");
        //TODO: creo una fila manualmente para el offer que voy a pedir, uso los mismos datos que user para testear la creacion
        //TODO: mirar como le seteo el user id, que en la original es un serial, lo pongo como default en 1

        // 2
        Offer offer = offerJdbcDao.getOffer(OFFER_ID);

        // 3
        Assert.assertEquals(OFFER_ID, offer.getId());
        Assert.assertEquals(ASKING_PRICE, offer.getAskingPrice());

        Assert.assertEquals(USER_ID, offer.getSeller().getId());
        Assert.assertEquals(USER_MAIL, offer.getSeller().getEmail());

        Assert.assertEquals(MARKET_PRICE, offer.getCrypto().getMarketPrice());
        Assert.assertEquals(CRYPTO_CODE, offer.getCrypto().getCode());
        Assert.assertEquals(CRYPTO_NAME, offer.getCrypto().getName());
    }




}

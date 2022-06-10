package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Cryptocurrency;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CryptocurrencyJdbcDaoTest {

    private static final String CRYPTO_TABLE = "cryptocurrency";
    private static final int TESTED_INDEX = 1;
    private static final double MARKET_PRICE = 2.4;

    private ArrayList<Cryptocurrency> cryptos;

    @Autowired
    private DataSource ds;

    private SimpleJdbcInsert jdbcInsert;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
//        jdbcTemplate = new JdbcTemplate(ds);
//        jdbcInsert = new SimpleJdbcInsert(ds)
//                .withTableName(CRYPTO_TABLE);
//        cryptoJdbcDao = new CryptocurrencyJdbcDao(ds);
//        JdbcTestUtils.deleteFromTables(jdbcTemplate,CRYPTO_TABLE);
//
//        cryptos = new ArrayList<>();
//        cryptos.add(Cryptocurrency.getInstance("BTC", "Bitcoin"));
//        cryptos.add(Cryptocurrency.getInstance("ETH", "Ether"));
//        cryptos.add(Cryptocurrency.getInstance("ADA", "Cardano"));
//        cryptos.add(Cryptocurrency.getInstance("DOT", "Polkadot"));
    }

    @Test
    public void TestGetCryptocurrency(){
        //Setup
        JdbcTestUtils.deleteFromTables(jdbcTemplate, CRYPTO_TABLE);
        insertCrypto(cryptos.get(TESTED_INDEX));

        // Exercise
//        Optional<Cryptocurrency> testedCrypto = cryptoJdbcDao.getCryptocurrency(cryptos.get(TESTED_INDEX).getCode());
//
//        //Validations
//        Assert.assertTrue(testedCrypto.isPresent());
//        Assert.assertEquals(cryptos.get(TESTED_INDEX).getCode(), testedCrypto.get().getCode());
//        Assert.assertEquals(cryptos.get(TESTED_INDEX).getCommercialName(), testedCrypto.get().getCommercialName());
    }

    @Test
    public void TestGetAllCryptocurrencies(){
        // Setup
        JdbcTestUtils.deleteFromTables(jdbcTemplate, CRYPTO_TABLE);
        for(Cryptocurrency crypto : cryptos){
            insertCrypto(crypto);
        }

        // Exercise
//        Collection<Cryptocurrency> testedCryptos = cryptoJdbcDao.getAllCryptocurrencies();

        //Validations
//        Assert.assertTrue(testedCryptos.containsAll(cryptos));
    }

    private void insertCrypto(Cryptocurrency crypto){
        HashMap<String, Object> cryptoMap = new HashMap<>();

        cryptoMap.put("code", crypto.getCode());
        cryptoMap.put("market_price", MARKET_PRICE);
        cryptoMap.put("commercial_name", crypto.getCommercialName());

        jdbcInsert.execute(cryptoMap);

    }
}

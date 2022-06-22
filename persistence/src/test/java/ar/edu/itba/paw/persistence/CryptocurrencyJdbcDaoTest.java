package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Cryptocurrency;
import org.junit.Assert;
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
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CryptocurrencyJdbcDaoTest {

    private static final String CRYPTO_TABLE = "cryptocurrency";
    private static final String TEST_CODE = "ETH";

    private HashMap<String, String> cryptos;

    @Autowired
    private DataSource ds;

    @Autowired
    private CryptocurrencyHibernateDao cryptoHibernateDao;
    private SimpleJdbcInsert jdbcInsert;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(CRYPTO_TABLE);
        JdbcTestUtils.deleteFromTables(jdbcTemplate,CRYPTO_TABLE);

        cryptos = new HashMap<String, String>();
        cryptos.put("BTC", "Bitcoin");
        cryptos.put("ETH", "Ether");
        cryptos.put("ADA", "Cardano");
        cryptos.put("DOT", "Polkadot");
    }

    @Test
    public void TestGetCryptocurrency(){
        //Setup
        JdbcTestUtils.deleteFromTables(jdbcTemplate, CRYPTO_TABLE);
        insertCrypto(TEST_CODE, cryptos.get(TEST_CODE));

        // Exercise
        Optional<Cryptocurrency> testedCrypto = cryptoHibernateDao.getCryptocurrency(TEST_CODE);

        //Validations
        Assert.assertTrue(testedCrypto.isPresent());
        Assert.assertEquals(TEST_CODE, testedCrypto.get().getCode());
        Assert.assertEquals(cryptos.get(TEST_CODE), testedCrypto.get().getCommercialName());
    }

    @Test
    public void TestGetAllCryptocurrencies(){
        // Setup
        JdbcTestUtils.deleteFromTables(jdbcTemplate, CRYPTO_TABLE);
        Set<String> codes = cryptos.keySet();
        for(String code : codes){
            insertCrypto(code, cryptos.get(code));
        }

        // Exercise
        Collection<Cryptocurrency> testedCryptos = cryptoHibernateDao.getAllCryptocurrencies();

        //Validations
        Assert.assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate, CRYPTO_TABLE), testedCryptos.size());
    }

    private void insertCrypto(String code, String commercialName){
        HashMap<String, Object> cryptoMap = new HashMap<>();

        cryptoMap.put("code", code);
        cryptoMap.put("commercial_name", commercialName);

        jdbcInsert.execute(cryptoMap);

    }
}

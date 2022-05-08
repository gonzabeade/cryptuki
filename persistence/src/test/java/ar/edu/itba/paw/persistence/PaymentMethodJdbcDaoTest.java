package ar.edu.itba.paw.persistence;

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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class PaymentMethodJdbcDaoTest {
    private static final String PAYMENT_METHOD_TABLE= "payment_method";
    private static final int TESTED_INDEX = 1;

    private ArrayList<PaymentMethod> paymentMethods;

    @Autowired
    private DataSource ds;

    private PaymentMethodJdbcDao paymentMethodJdbcDao;
    private SimpleJdbcInsert jdbcInsert;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(PAYMENT_METHOD_TABLE)
                .usingGeneratedKeyColumns("id");
        paymentMethodJdbcDao = new PaymentMethodJdbcDao(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, PAYMENT_METHOD_TABLE);

        paymentMethods= new ArrayList<>();
        paymentMethods.add(PaymentMethod.getInstance("BTC", "Bitcoin"));
        paymentMethods.add(PaymentMethod.getInstance("BTC", "Bitcoin"));
        paymentMethods.add(PaymentMethod.getInstance("BTC", "Bitcoin"));
    }

    @Test
    public void TestGetCryptocurrency(){
        //Setup
        JdbcTestUtils.deleteFromTables(jdbcTemplate, PAYMENT_METHOD_TABLE);
        insertPaymentMethod(paymentMethods.get(TESTED_INDEX));

        // Exercise
        Optional<PaymentMethod> testedPaymentMethod = paymentMethodJdbcDao.getPaymentMethodByCode(paymentMethods.get(TESTED_INDEX).getName());

        //Validations
        Assert.assertNotNull(testedPaymentMethod);
        Assert.assertEquals(paymentMethods.get(TESTED_INDEX).getName(), testedPaymentMethod.get().getName());
        Assert.assertEquals(paymentMethods.get(TESTED_INDEX).getDescription(), testedPaymentMethod.get().getDescription());
    }

    @Test
    public void TestGetAllPaymentMethods(){
        // Setup
        JdbcTestUtils.deleteFromTables(jdbcTemplate, PAYMENT_METHOD_TABLE);
        for(PaymentMethod paymentMethod: paymentMethods){
            insertPaymentMethod(paymentMethod);
        }

        // Exercise
        Collection<PaymentMethod> testedCryptos = paymentMethodJdbcDao.getAllPaymentMethods();

        //Validations
    }

    private void insertPaymentMethod(PaymentMethod paymentMethod){
        HashMap<String, Object> offerMap = new HashMap<>();

        offerMap.put("code", paymentMethod.getName());
        offerMap.put("payment_description", paymentMethod.getDescription());

        jdbcInsert.execute(offerMap);

    }
}

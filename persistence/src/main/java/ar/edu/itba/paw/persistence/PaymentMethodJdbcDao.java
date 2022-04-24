package ar.edu.itba.paw.persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.LinkedList;

@Repository
public class PaymentMethodJdbcDao implements PaymentMethodDao {

    private JdbcTemplate jdbcTemplate;

    private final static RowMapper<PaymentMethod> PAYMENT_METHOD_ROW_MAPPER =
            (resultSet, i) -> PaymentMethod.getInstance(resultSet.getString("code"), resultSet.getString("payment_description"));


    @Autowired
    public PaymentMethodJdbcDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final static String query = "SELECT * FROM payment_method";

    @Override
    public Collection<PaymentMethod> getAllPaymentMethods() {
        return jdbcTemplate.query(query, PAYMENT_METHOD_ROW_MAPPER);
    }
}

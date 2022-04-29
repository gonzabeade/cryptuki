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
import java.util.Optional;

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

    private final static String queryByCode = "SELECT * FROM payment_method WHERE code = ?";


    @Override
    public Collection<PaymentMethod> getAllPaymentMethods() {
        return jdbcTemplate.query(query, PAYMENT_METHOD_ROW_MAPPER);
    }

    @Override
    public Optional<PaymentMethod> getPaymentMethodByCode(String code) {
        return Optional.of(jdbcTemplate.query(queryByCode, new Object[]{code}, PAYMENT_METHOD_ROW_MAPPER).iterator().next());
    }
}

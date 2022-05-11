package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.exception.UncategorizedPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Optional;

@Repository
public class CryptocurrencyJdbcDao implements CryptocurrencyDao {

    private JdbcTemplate jdbcTemplate;
    private final static RowMapper<Cryptocurrency> CRYPTOCURRENCY_ROW_MAPPER = (
            (resultSet, i) -> Cryptocurrency.getInstance(
                    resultSet.getString("code"),
                    resultSet.getString("commercial_name")
            )
    );

    @Autowired
    public CryptocurrencyJdbcDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Cryptocurrency> getCryptocurrency(String code) {
        final String query = "SELECT * FROM PUBLIC.cryptocurrency WHERE code = ?";

        try {
            return Optional.of(jdbcTemplate.queryForObject(query, CRYPTOCURRENCY_ROW_MAPPER, code));
        } catch (EmptyResultDataAccessException erde) {
            return Optional.empty();
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }

    @Override
    public Collection<Cryptocurrency> getAllCryptocurrencies() {
        final String query = "SELECT * FROM PUBLIC.cryptocurrency";

        try {
            return jdbcTemplate.query(query, CRYPTOCURRENCY_ROW_MAPPER);
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }
}

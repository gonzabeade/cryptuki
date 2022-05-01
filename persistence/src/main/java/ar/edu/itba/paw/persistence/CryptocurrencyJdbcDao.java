package ar.edu.itba.paw.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;

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
    public Cryptocurrency getCryptocurrency(String code) {
        final String query = "SELECT * FROM PUBLIC.cryptocurrency WHERE code = ?";
        final List<Cryptocurrency> cryptocurrencies = jdbcTemplate.query(query, CRYPTOCURRENCY_ROW_MAPPER, code);
        return cryptocurrencies.get(0); //should be unique
    }

    @Override
    public Collection<Cryptocurrency> getAllCryptocurrencies() {
        final String query = "SELECT * FROM PUBLIC.cryptocurrency";
        final List<Cryptocurrency> cryptocurrencies = jdbcTemplate.query(query, CRYPTOCURRENCY_ROW_MAPPER);
        return cryptocurrencies;
    }
}

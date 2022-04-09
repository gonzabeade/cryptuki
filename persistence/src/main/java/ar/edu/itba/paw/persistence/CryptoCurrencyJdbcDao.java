package ar.edu.itba.paw.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import javax.sql.DataSource;
import java.util.List;

@Repository
public class CryptoCurrencyJdbcDao implements CryptoCurrencyDao{

    private JdbcTemplate jdbcTemplate;
    private final static RowMapper<Cryptocurrency> CRYPTOCURRENCY_ROW_MAPPER = ((resultSet, i) -> new Cryptocurrency(resultSet.getString("coin_id"),resultSet.getString("description"),resultSet.getDouble("market_price")));


    @Autowired
    public CryptoCurrencyJdbcDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Cryptocurrency getCryptocurrency(String id) {
        final List<Cryptocurrency> cryptocurrencies = jdbcTemplate.query("SELECT * FROM PUBLIC.COINS WHERE coin_id = ?",CRYPTOCURRENCY_ROW_MAPPER,id);
        return cryptocurrencies.get(0); //should be unique
    }

    @Override
    public Iterable<Cryptocurrency> getAllCryptocurrencies() {
        final List<Cryptocurrency> cryptocurrencies = jdbcTemplate.query("SELECT * FROM PUBLIC.COINS",CRYPTOCURRENCY_ROW_MAPPER);
        return cryptocurrencies;
    }
}

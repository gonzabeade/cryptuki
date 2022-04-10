package ar.edu.itba.paw.persistence;

public interface CryptoCurrencyDao {

    Cryptocurrency getCryptocurrency(String id);
    Iterable<Cryptocurrency> getAllCryptocurrencies();

}

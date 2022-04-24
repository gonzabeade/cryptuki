package ar.edu.itba.paw.persistence;

public interface CryptocurrencyDao {

    Cryptocurrency getCryptocurrency(String id);
    Iterable<Cryptocurrency> getAllCryptocurrencies();

}

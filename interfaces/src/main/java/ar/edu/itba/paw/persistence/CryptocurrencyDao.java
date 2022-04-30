package ar.edu.itba.paw.persistence;

import java.util.Collection;

public interface CryptocurrencyDao {
    Cryptocurrency getCryptocurrency(String id);
    Collection<Cryptocurrency> getAllCryptocurrencies();
}

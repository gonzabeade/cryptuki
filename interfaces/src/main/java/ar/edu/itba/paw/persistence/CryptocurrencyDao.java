package ar.edu.itba.paw.persistence;

import java.util.Collection;

public interface CryptocurrencyDao {
    Cryptocurrency getCryptocurrency(String code);
    Collection<Cryptocurrency> getAllCryptocurrencies();
}

package ar.edu.itba.paw.persistence;

import java.util.Collection;
import java.util.Optional;

public interface CryptocurrencyDao {
    Optional<Cryptocurrency> getCryptocurrency(String code);
    Collection<Cryptocurrency> getAllCryptocurrencies();
}

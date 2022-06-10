package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Cryptocurrency;

import java.util.Collection;
import java.util.Optional;

public interface CryptocurrencyDao {
    Optional<Cryptocurrency> getCryptocurrency(String code);
    Collection<Cryptocurrency> getAllCryptocurrencies();
}

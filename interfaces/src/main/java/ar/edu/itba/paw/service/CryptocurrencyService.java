package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Cryptocurrency;
import java.util.Collection;
import java.util.Optional;

public interface CryptocurrencyService {

    Optional<Cryptocurrency> getCryptocurrency(String id);
    Collection<Cryptocurrency> getAllCryptocurrencies();

}

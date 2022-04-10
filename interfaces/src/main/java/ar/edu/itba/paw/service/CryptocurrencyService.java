package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Cryptocurrency;

public interface CryptocurrencyService {

    Cryptocurrency getCryptocurrency(String id);
    Iterable<Cryptocurrency> getAllCryptocurrencies();

}

package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.PersistenceException;
import ar.edu.itba.paw.exception.ServiceDataAccessException;
import ar.edu.itba.paw.persistence.Cryptocurrency;
import ar.edu.itba.paw.persistence.CryptocurrencyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class CryptocurrencyServiceImpl implements CryptocurrencyService {

    private final CryptocurrencyDao cryptoCurrencyDao;

    @Autowired
    public CryptocurrencyServiceImpl(CryptocurrencyDao cryptoCurrencyDao) {
        this.cryptoCurrencyDao = cryptoCurrencyDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cryptocurrency> getCryptocurrency(String id) {

        if (id == null)
            throw new NullPointerException("Cryptocurrency id cannot be null");

        try {
            return cryptoCurrencyDao.getCryptocurrency(id);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Cryptocurrency> getAllCryptocurrencies() {
        try {
            return cryptoCurrencyDao.getAllCryptocurrencies();
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }
}

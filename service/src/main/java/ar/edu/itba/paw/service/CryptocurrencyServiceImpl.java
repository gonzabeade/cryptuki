package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.CryptocurrencyDao;
import ar.edu.itba.paw.persistence.Cryptocurrency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CryptocurrencyServiceImpl implements CryptocurrencyService {

    @Autowired
    private CryptocurrencyDao cryptoCurrencyDao;

    @Override
    public Cryptocurrency getCryptocurrency(String id) {
        //TODO: validate
        return cryptoCurrencyDao.getCryptocurrency(id);
    }

    @Override
    public Iterable<Cryptocurrency> getAllCryptocurrencies() {
        //TODO: validate
        return cryptoCurrencyDao.getAllCryptocurrencies();
    }
}

package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.ServiceDataAccessException;
import ar.edu.itba.paw.exception.UncategorizedPersistenceException;
import ar.edu.itba.paw.persistence.Trade;
import ar.edu.itba.paw.persistence.TradeDao;
import ar.edu.itba.paw.persistence.TradeStatus;
import ar.edu.itba.paw.service.digests.BuyDigest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class TradeServiceImpl implements TradeService {

    private final ContactService<MailMessage> mailContactService;
    private final OfferService offerService;
    private final TradeDao tradeDao;

    @Autowired
    public TradeServiceImpl(ContactService<MailMessage> mailContactService, OfferService offerService, TradeDao tradeDao) {
        this.mailContactService = mailContactService;
        this.offerService = offerService;
        this.tradeDao = tradeDao;
    }


    @Override
    @Transactional
    public void makeTrade(Trade.Builder trade) {

        if (trade == null)
            throw new NullPointerException("Trade Builder cannot be null");

        try {
            tradeDao.makeTrade(trade);
        } catch (UncategorizedPersistenceException upe) {
            throw new ServiceDataAccessException(upe);
        }
    }

    @Override
    @Transactional
    public void updateStatus(int tradeId, TradeStatus status) {

        if (tradeId < 0)
            throw new IllegalArgumentException("Id can only be non negative");

        if (status == null)
            throw new NullPointerException("Status cannot be null");

        try {
            tradeDao.updateStatus(tradeId, status);
        } catch (UncategorizedPersistenceException upe) {
            throw new ServiceDataAccessException(upe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Trade> getTradeById(int tradeId) {

        if (tradeId < 0)
            throw new IllegalArgumentException("Id can only be non negative");

        try {
            return tradeDao.getTradeById(tradeId);
        } catch (UncategorizedPersistenceException upe) {
            throw new ServiceDataAccessException(upe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Trade> getSellingTradesByUsername(String username, int page, int pageSize) {

        if (page < 0 || pageSize < 0)
            throw new IllegalArgumentException("Both page and pageSize can only be non negative");

        if (username == null)
            throw new NullPointerException("Username cannot be null");

        try {
            return tradeDao.getSellingTradesByUsername(username, page, pageSize);
        } catch (UncategorizedPersistenceException upe) {
            throw new ServiceDataAccessException(upe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public int getSellingTradesByUsernameCount(String username) {

        if (username == null)
            throw new NullPointerException("Username cannot be null");

        try {
            return tradeDao.getSellingTradesByUsernameCount(username);
        } catch (UncategorizedPersistenceException upe) {
            throw new ServiceDataAccessException(upe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Trade> getBuyingTradesByUsername(String username, int page, int pageSize) {

        if (page < 0 || pageSize < 0)
            throw new IllegalArgumentException("Both page and pageSize can only be non negative");

        if (username == null)
            throw new NullPointerException("Username cannot be null");

        try {
            return tradeDao.getBuyingTradesByUsername(username, page, pageSize);
        } catch (UncategorizedPersistenceException upe) {
            throw new ServiceDataAccessException(upe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public int getBuyingTradesByUsernameCount(String username) {

        if (username == null)
            throw new NullPointerException("Username cannot be null");

        try {
            return tradeDao.getSellingTradesByUsernameCount(username);
        } catch (UncategorizedPersistenceException upe) {
            throw new ServiceDataAccessException(upe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Trade> getTradesByUsername(String username, int page, int pageSize) {

        if (page < 0 || pageSize < 0)
            throw new IllegalArgumentException("Both page and pageSize can only be non negative");

        if (username == null)
            throw new NullPointerException("Username cannot be null");

        try {
            return tradeDao.getTradesByUsername(username,page,pageSize);
        } catch (UncategorizedPersistenceException upe) {
            throw new ServiceDataAccessException(upe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public int getTradesByUsernameCount(String username) {

        if (username == null)
            throw new NullPointerException("Username cannot be null");

        try {
            return tradeDao.getTradesByUsernameCount(username);
        } catch (UncategorizedPersistenceException upe) {
            throw new ServiceDataAccessException(upe);
        }
    }
}

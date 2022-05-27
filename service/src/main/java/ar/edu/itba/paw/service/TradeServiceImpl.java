package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.*;
import ar.edu.itba.paw.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Service
public class TradeServiceImpl implements TradeService {

    private  OfferService offerService;
    private  TradeDao tradeDao;
    private  UserAuthDao userAuthDao;

    private  MessageSenderFacade messageSenderFacade;

    @Autowired
    public TradeServiceImpl(OfferService offerService,
                            TradeDao tradeDao,
                            UserAuthDao userAuthDao,
                            MessageSenderFacade messageSenderFacade) {
        this.offerService = offerService;
        this.tradeDao = tradeDao;
        this.userAuthDao = userAuthDao;
        this.messageSenderFacade = messageSenderFacade;
    }


    @Override
    @Transactional
    @Secured("ROLE_USER")
    @PreAuthorize("#trade.buyerUsername == authentication.principal.username")
    public int makeTrade(Trade.Builder trade) {

        if (trade == null)
            throw new NullPointerException("Trade Builder cannot be null");

        Optional<Offer> offerOptional;
        try {
            offerOptional =  offerService.getOfferById(trade.getOfferId());
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }

        if (!offerOptional.isPresent())
            throw new NoSuchOfferException(trade.getOfferId());
        Offer offer = offerOptional.get();


        Optional<UserAuth> userAuthOptional;
        try {
            userAuthOptional = userAuthDao.getUserAuthByEmail(offer.getSeller().getEmail());
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }

        if (!userAuthOptional.isPresent())
            throw new NoSuchUserException(offer.getSeller().getEmail());
        UserAuth userAuth = userAuthOptional.get();

        trade.withCryptoCurrency(offer.getCrypto())
            .withStartDate(LocalDateTime.now())
            .withTradeStatus(TradeStatus.PENDING)
            .withSellerUsername(userAuth.getUsername())
            .withSellerUsername(userAuth.getUsername());

        int tradeId;
        try {
//           offerService.decrementOfferMaxQuantity(offer, trade.getQuantity());
           tradeId = tradeDao.makeTrade(trade);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }

        messageSenderFacade.sendNewTradeNotification(trade.getSellerUsername(), trade, tradeId);
        return tradeId;
    }

    @Override
    @Transactional
//    @Secured("ROLE_ADMIN")
    public void updateStatus(int tradeId, TradeStatus status) {

        if (tradeId < 0)
            throw new IllegalArgumentException("Id can only be non negative");

        if (status == null)
            throw new NullPointerException("Status cannot be null");

        try {
            tradeDao.updateStatus(tradeId, status);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ROLE_ADMIN') or @customPreAuthorizer.isUserPartOfTrade(#tradeId, authentication.principal)")
    public Optional<Trade> getTradeById(int tradeId) {

        if (tradeId < 0)
            throw new IllegalArgumentException("Id can only be non negative");

        try {
            return tradeDao.getTradeById(tradeId);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ROLE_ADMIN') or #username == authentication.principal.username")
    public Collection<Trade> getSellingTradesByUsername(String username, int page, int pageSize) {

        if (page < 0 || pageSize < 0)
            throw new IllegalArgumentException("Both page and pageSize can only be non negative");

        if (username == null)
            throw new NullPointerException("Username cannot be null");

        try {
            return tradeDao.getSellingTradesByUsername(username, page, pageSize);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ROLE_ADMIN') or #username == authentication.principal.username")
    public int getSellingTradesByUsernameCount(String username) {

        if (username == null)
            throw new NullPointerException("Username cannot be null");

        try {
            return tradeDao.getSellingTradesByUsernameCount(username);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ROLE_ADMIN') or #username == authentication.principal.username")
    public Collection<Trade> getBuyingTradesByUsername(String username, int page, int pageSize) {

        if (page < 0 || pageSize < 0)
            throw new IllegalArgumentException("Both page and pageSize can only be non negative");

        if (username == null)
            throw new NullPointerException("Username cannot be null");

        try {
            return tradeDao.getBuyingTradesByUsername(username, page, pageSize);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ROLE_ADMIN') or #username == authentication.principal.username")
    public int getBuyingTradesByUsernameCount(String username) {

        if (username == null)
            throw new NullPointerException("Username cannot be null");

        try {
            return tradeDao.getSellingTradesByUsernameCount(username);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ROLE_ADMIN') or #username == authentication.principal.username")
    public Collection<Trade> getTradesByUsername(String username, int page, int pageSize) {

        if (page < 0 || pageSize < 0)
            throw new IllegalArgumentException("Both page and pageSize can only be non negative");

        if (username == null)
            throw new NullPointerException("Username cannot be null");

        try {
            return tradeDao.getTradesByUsername(username,page,pageSize);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ROLE_ADMIN') or #username == authentication.principal.username")
    public int getTradesByUsernameCount(String username) {

        if (username == null)
            throw new NullPointerException("Username cannot be null");

        try {
            return tradeDao.getTradesByUsernameCount(username);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional
    public void updateRatedSeller(int tradeId) {
        try{
            tradeDao.rateSeller(tradeId);
        }catch (PersistenceException pe){
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional
    public void updatedRatedBuyer(int tradeId) {
        try{
            tradeDao.rateBuyer(tradeId);
        }catch (PersistenceException pe){
            throw new ServiceDataAccessException(pe);
        }
    }


}

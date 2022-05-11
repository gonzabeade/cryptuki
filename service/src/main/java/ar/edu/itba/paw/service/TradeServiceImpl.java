package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.PersistenceException;
import ar.edu.itba.paw.exception.ServiceDataAccessException;
import ar.edu.itba.paw.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class TradeServiceImpl implements TradeService {

    private final OfferService offerService;
    private final TradeDao tradeDao;

    private final UserService userService;

    private final MessageSenderFacade messageSenderFacade;

    @Autowired
    public TradeServiceImpl(OfferService offerService,
                            TradeDao tradeDao,
                            UserService userService,
                            MessageSenderFacade messageSenderFacade) {
        this.offerService = offerService;
        this.tradeDao = tradeDao;
        this.userService = userService;
        this.messageSenderFacade = messageSenderFacade;
    }


    @Override
    @Transactional
    @Secured("ROLE_USER")
    @PreAuthorize("#trade.buyerUsername == authentication.principal.username")
    public int makeTrade(Trade.Builder trade) {

        if (trade == null)
            throw new NullPointerException("Trade Builder cannot be null");

        Offer offer = offerService.getOfferById(trade.getOfferId()).get();
        UserAuth userAuth = userService.getUserAuthByEmail(offer.getSeller().getEmail()).get();

        try {
           offerService.decrementOfferMaxQuantity(offer, trade.getQuantity());
            //create trade.
            trade.withTradeStatus(TradeStatus.OPEN)
                    .withSellerUsername(userAuth.getUsername());
            int tradeId = tradeDao.makeTrade(trade);


            messageSenderFacade.sendNewTradeNotification(
                    offer.getSeller().getEmail(),
                    trade.getSellerUsername(),
                    trade.getCryptoCurrency().getCode(),
                    trade.getQuantity(),
                    trade.getBuyerUsername(),
                    "",
                    "",
                    tradeId
            );

            return tradeId;
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
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

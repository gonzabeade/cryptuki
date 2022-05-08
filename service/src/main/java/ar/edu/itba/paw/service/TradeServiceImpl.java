package ar.edu.itba.paw.service;

import ar.edu.itba.paw.OfferDigest;
import ar.edu.itba.paw.exception.PersistenceException;
import ar.edu.itba.paw.exception.ServiceDataAccessException;
import ar.edu.itba.paw.exception.UncategorizedPersistenceException;
import ar.edu.itba.paw.persistence.*;
import ar.edu.itba.paw.service.digests.BuyDigest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class TradeServiceImpl implements TradeService {

    private final ContactService<MailMessage> mailContactService;
    private final OfferService offerService;
    private final TradeDao tradeDao;

    private final UserService userService;

    @Autowired
    public TradeServiceImpl(ContactService<MailMessage> mailContactService, OfferService offerService, TradeDao tradeDao, UserService userService) {
        this.mailContactService = mailContactService;
        this.offerService = offerService;
        this.tradeDao = tradeDao;
        this.userService = userService;
    }


    @Override
    @Transactional
    public int makeTrade(Trade.Builder trade) {

        if (trade == null)
            throw new NullPointerException("Trade Builder cannot be null");

        Offer offer = offerService.getOfferById(trade.getOfferId()).get();
        UserAuth userAuth = userService.getUserAuthByEmail(offer.getSeller().getEmail()).get();

        try {
            //modify offer
            OfferDigest.Builder digestBuilder = new OfferDigest.Builder(offer.getSeller().getId(),offer.getCrypto().getCode(),offer.getAskingPrice());
            digestBuilder.withId(offer.getId());
            digestBuilder.withComments(offer.getComments());
            offer.getPaymentMethods().forEach(paymentMethod -> digestBuilder.withPaymentMethod(paymentMethod.getName()));

            float remaining = offer.getMaxQuantity() - (trade.getQuantity()/offer.getAskingPrice());

            if( remaining <= 0){
                offerService.pauseOffer(offer.getId());
            }


            digestBuilder.withMaxQuantity(remaining);
            float newMin =(offer.getMinQuantity() > remaining ) ? 0 : (offer.getMinQuantity());
            if(newMin == 0){
                offerService.pauseOffer(offer.getId());
            }
            else{
                digestBuilder.withMinQuantity(newMin);
                offerService.modifyOffer(digestBuilder.build());
            }
            //create trade.
            trade.withTradeStatus(TradeStatus.OPEN)
                    .withSellerUsername(userAuth.getUsername());
            int tradeId = tradeDao.makeTrade(trade);


            //send email to seller
            MailMessage message = mailContactService.createMessage(offer.getSeller().getEmail());
            message.setSubject("Te compraron " +trade.getQuantity()/offer.getAskingPrice()+" "+offer.getCrypto().getCode());
            message.setBody("Quedan por vender "+ remaining +" "+offer.getCrypto().getCode() );
            mailContactService.sendMessage(message);

            return tradeId;
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
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
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional(readOnly = true)
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
    public int getTradesByUsernameCount(String username) {

        if (username == null)
            throw new NullPointerException("Username cannot be null");

        try {
            return tradeDao.getTradesByUsernameCount(username);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }
}

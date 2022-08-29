package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.NoSuchOfferException;
import ar.edu.itba.paw.exception.NoSuchTradeException;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomPreAuthorizer {

    private final UserDao userDao;
    private final OfferDao offerDao;
    private final TradeDao tradeDao;
    private final KycDao kycDao;

    @Autowired
    public CustomPreAuthorizer(UserDao userDao, OfferDao offerDao, TradeDao tradeDao, KycDao kycDao) {
        this.userDao = userDao;
        this.offerDao = offerDao;
        this.tradeDao = tradeDao;
        this.kycDao = kycDao;
    }

    public boolean canUserUploadOffer(UserDetails userDetails) {
        Optional<User> maybeUser = userDao.getUserByUsername(userDetails.getUsername());
        if (!maybeUser.isPresent())
            return false;
        User user = maybeUser.get();
        return user.getUserAuth().getUserStatus().equals(UserStatus.VERIFIED)
                && user.getKyc() != null
                && user.getKyc().get().getStatus().equals(KycStatus.APR);
    }

    public boolean canUserAlterOffer(UserDetails userDetails, int offerId) {
        Optional<Offer> maybeOffer = offerDao.getOffersBy(new OfferFilter().restrictedToId(offerId)).stream().findFirst();
        if (!maybeOffer.isPresent())
            throw new NoSuchOfferException(offerId);
        return maybeOffer.get().getSeller().getUsername().get().equals(userDetails.getUsername()) && maybeOffer.get().getSeller().getKyc().get().getStatus().equals(KycStatus.APR);
    }

    public boolean isUserOwnerOfTrade(UserDetails userDetails, int tradeId) {
        Optional<Trade> maybeTrade = tradeDao.getTradeById(tradeId);
        if (!maybeTrade.isPresent())
            throw new NoSuchTradeException(tradeId);
        return maybeTrade.get().getOffer().getSeller().getUsername().get().equals(userDetails.getUsername());
    }

    public boolean isUserBuyerOfTrade(UserDetails userDetails, int tradeId) {
        Optional<Trade> maybeTrade = tradeDao.getTradeById(tradeId);
        if (!maybeTrade.isPresent())
            throw new NoSuchTradeException(tradeId);
        return maybeTrade.get().getBuyer().getUsername().get().equals(userDetails.getUsername());
    }

    public boolean isUserPartOfTrade(UserDetails userDetails, int tradeId) {
        Optional<Trade> maybeTrade = tradeDao.getTradeById(tradeId);
        if (!maybeTrade.isPresent())
            throw new NoSuchTradeException(tradeId);
        return maybeTrade.get().getBuyer().getUsername().get().equals(userDetails.getUsername())
            || maybeTrade.get().getOffer().getSeller().getUsername().get().equals(userDetails.getUsername());
    }

    public boolean doesUserHaveCode(String username, int code) {
        Optional<User> maybeUser = userDao.getUserByUsername(username);
        if (!maybeUser.isPresent())
            return false;
        User user = maybeUser.get();
        return user.getUserAuth().getCode() == code;
    }
}
package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.NoSuchComplainException;
import ar.edu.itba.paw.exception.NoSuchOfferException;
import ar.edu.itba.paw.exception.NoSuchTradeException;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.parameterObject.ComplainPO;
import ar.edu.itba.paw.model.parameterObject.KycInformationPO;
import ar.edu.itba.paw.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Supplier;

@Component
public class CustomPreAuthorizer {

    private final UserDao userDao;
    private final OfferDao offerDao;
    private final TradeDao tradeDao;
    private final KycDao kycDao;

    private final ComplainDao complainDao;
    @Autowired
    public CustomPreAuthorizer(UserDao userDao, OfferDao offerDao, TradeDao tradeDao, KycDao kycDao, ComplainDao complainDao) {
        this.userDao = userDao;
        this.offerDao = offerDao;
        this.tradeDao = tradeDao;
        this.kycDao = kycDao;
        this.complainDao = complainDao;
    }

    public boolean canUserUploadOffer(String username) {
        User user = userDao.getUserByUsername(username).orElseThrow(()->new NoSuchUserException(username));
        return user.getUserAuth().getUserStatus().equals(UserStatus.VERIFIED)
                && user.getKyc().isPresent()
                && user.getKyc().get().getStatus().equals(KycStatus.APR);
    }

    public boolean canUserAlterOffer(String username, int offerId) {
        Offer offer = offerDao.getOffersBy(new OfferFilter().restrictedToId(offerId))
                .stream().findFirst().orElseThrow(()->new NoSuchOfferException(offerId));
        String seller =offer.getSeller().getUsername().orElseThrow(()->new NoSuchUserException(username));
        return seller.equals(username) && offer.getSeller().getKyc().isPresent()
                && offer.getSeller().getKyc().get().getStatus().equals(KycStatus.APR);
    }

    public boolean isUserOwnerOfTrade(String username, int tradeId) {
        Trade trade = tradeDao.getTradeById(tradeId).orElseThrow(()->new NoSuchTradeException(tradeId));
        String seller = trade.getOffer().getSeller().getUsername().orElseThrow(()->new NoSuchUserException(username));
        return seller.equals(username);
    }

    public boolean isUserBuyerOfTrade(String username, int tradeId) {
        Trade trade = tradeDao.getTradeById(tradeId).orElseThrow(()-> new NoSuchTradeException(tradeId));
        String buyer =trade.getBuyer().getUsername().orElseThrow(()-> new NoSuchUserException(username));
        return buyer.equals(username);
    }

    public boolean isUserPartOfTrade(String username, int tradeId) {
        Trade trade = tradeDao.getTradeById(tradeId).orElseThrow(()->new NoSuchTradeException(tradeId));
        return ( trade.getBuyer().getUsername().isPresent() &&
                trade.getBuyer().getUsername().get().equals(username) )
            || ( trade.getOffer().getSeller().getUsername().isPresent()
                && trade.getOffer().getSeller().getUsername().get().equals(username) );
    }

    public boolean doesUserHaveCode(String username, int code) {
        User user = userDao.getUserByUsername(username).orElseThrow(()-> new NoSuchUserException(username));
        return user.getUserAuth().getCode() == code;
    }

    public boolean canUserPeepComplain(String username, int complainId){
        Complain complain = complainDao.getComplainsBy(new ComplainFilter().restrictedToComplainId(complainId))
                .stream().findFirst().orElseThrow(()-> new NoSuchComplainException(complainId));
        return isUserPartOfTrade(username,complain.getTrade().getTradeId());
    }


    public boolean isUserPartOfTrade(String username, ComplainPO complainPO) {
        return complainPO!=null && isUserPartOfTrade(username,complainPO.getTradeId());
    }

    public boolean isKycFromUser(String username, KycInformationPO kycInformationPO){
        return kycInformationPO != null && username!=null && username.equals(kycInformationPO.getUsername());
    }



}
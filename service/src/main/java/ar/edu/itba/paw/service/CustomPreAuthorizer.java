package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.OfferFilter;
import ar.edu.itba.paw.model.Trade;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserAuth;
import ar.edu.itba.paw.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomPreAuthorizer {

    private final UserDao userDao;
    private final UserAuthDao userAuthDao;

    private final OfferDao offerDao;
    private final TradeDao tradeDao;


    @Autowired
    public CustomPreAuthorizer(UserDao userDao, UserAuthDao userAuthDao, OfferDao offerDao, TradeDao tradeDao) {
        this.userDao = userDao;
        this.userAuthDao = userAuthDao;
        this.offerDao = offerDao;
        this.tradeDao = tradeDao;
    }

    public boolean isUserAuthorized(int userId, UserDetails userDetails) {
        Optional<User> user = userDao.getUserByUsername(userDetails.getUsername());
        return user.isPresent() && user.get().getId() == userId;
    }

    public boolean isUserAuthorized(String username, UserDetails userDetails) {
        boolean v = username.equals(userDetails.getUsername());
        return v;
    }

    public boolean isUserOwnerOfOffer(int offerId, UserDetails userDetails) {
        String owner = offerDao.getOffersBy(new OfferFilter().restrictedToId(offerId)).stream().findFirst().get().getSeller().getUserAuth().getUsername();
        return owner != null && owner.equals(userDetails.getUsername());
    }

    public boolean isUserPartOfTrade(Integer tradeId, UserDetails userDetails) {
        if (tradeId == null) return true;
        Optional<Trade> trade = tradeDao.getTradeById(tradeId);
        return trade.isPresent() && (
                trade.get().getBuyer().getUserAuth().getUsername().equals(userDetails.getUsername())
                || trade.get().getOffer().getSeller().getUsername().equals(userDetails.getUsername())
        );
    }

    public boolean doesUserHaveCode(String username, int code) {
        Optional<UserAuth> user = userAuthDao.getUserAuthByUsername(username);
        boolean v = user.isPresent() && user.get().getCode() == code;
        return v;
    }

}
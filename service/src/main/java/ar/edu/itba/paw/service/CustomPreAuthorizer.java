package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.OfferDao;
import ar.edu.itba.paw.persistence.Trade;
import ar.edu.itba.paw.persistence.TradeDao;
import ar.edu.itba.paw.persistence.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomPreAuthorizer {

    private final UserDao userDao;
    private final OfferDao offerDao;
    private final TradeDao tradeDao;


    @Autowired
    public CustomPreAuthorizer(UserDao userDao, OfferDao offerDao, TradeDao tradeDao) {
        this.userDao = userDao;
        this.offerDao = offerDao;
        this.tradeDao = tradeDao;
    }

    public boolean isUserAuthorized(int userId, UserDetails userDetails) {
        Optional<User> user = userDao.getUserInformation(userDetails.getUsername());
        return user.isPresent() && user.get().getId() == userId;
    }

    public boolean isUserAuthorized(String username, UserDetails userDetails) {
        boolean v = username.equals(userDetails.getUsername());
        return v;
    }

    public boolean isUserOwnerOfOffer(int offerId, UserDetails userDetails) {
        Optional<String> owner = offerDao.getOwner(offerId);
        return owner.isPresent() && owner.get().equals(userDetails.getUsername());
    }

    public boolean isUserPartOfTrade(Integer tradeId, UserDetails userDetails) {
        if (tradeId == null) return true;
        Optional<Trade> trade = tradeDao.getTradeById(tradeId);
        return trade.isPresent() && (
                trade.get().getBuyerUsername().equals(userDetails.getUsername())
                || trade.get().getSellerUsername().equals(userDetails.getUsername())
        );
    }

}
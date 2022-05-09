package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Offer;
import ar.edu.itba.paw.persistence.OfferDao;
import ar.edu.itba.paw.persistence.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomPreAuthorizer {

    private final UserDao userDao;
    private final OfferDao offerDao;

    @Autowired
    public CustomPreAuthorizer(UserDao userDao, OfferDao offerDao) {
        this.userDao = userDao;
        this.offerDao = offerDao;
    }

    public boolean isUserAuthorized(int userId, UserDetails userDetails) {
        Optional<User> user = userDao.getUserInformation(userDetails.getUsername());
        return user.isPresent() && user.get().getId() == userId;
    }

    public boolean isUserOwnerOfOffer(int offerId, UserDetails userDetails) {
        Optional<String> owner = offerDao.getOwner(offerId);
        return owner.isPresent() && owner.get().equals(userDetails.getUsername());
    }

}
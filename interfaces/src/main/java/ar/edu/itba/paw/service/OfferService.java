package ar.edu.itba.paw.service;
import ar.edu.itba.paw.OfferDigest;
import ar.edu.itba.paw.persistence.Offer;
import ar.edu.itba.paw.OfferFilter;

import java.util.Collection;
import java.util.Optional;

public interface OfferService {

    // Todo: Immutable Collections
    void makeOffer(OfferDigest digest);
    Optional<Offer> getOfferById(int id);

    Collection<Offer> getOffersByUsername(String username);

    Collection<Offer> getOfferBy(OfferFilter filter);
    int countOffersBy(OfferFilter filter);

    int countOffersByUsername(String username);


    void modifyOffer(OfferDigest digest);
    void deleteOffer(int offerId);

    void hardPauseOffer(int offerId);
    void pauseOffer(int offerId);
    void resumeOffer(int offerId);


}

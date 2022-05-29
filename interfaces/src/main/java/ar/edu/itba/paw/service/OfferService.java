package ar.edu.itba.paw.service;
import ar.edu.itba.paw.OfferDigest;
import ar.edu.itba.paw.persistence.Offer;
import ar.edu.itba.paw.OfferFilter;

import java.util.Collection;
import java.util.Optional;

public interface OfferService {


    int makeOffer(OfferDigest digest);
    Optional<Offer> getOfferById(int id);


    Collection<Offer> getOfferBy(OfferFilter filter);
    int countOffersBy(OfferFilter filter);

    int countOffersByUsername(String username);
    Collection<Offer> getOffersByUsername(String username, int page, int pageSize);


    void modifyOffer(OfferDigest digest);
    void deleteOffer(int offerId);

    void hardPauseOffer(int offerId);
    void pauseOffer(int offerId);
    void resumeOffer(int offerId);

    void soldOffer(Offer offer, float sold, int tradeId);

    Optional<Offer> getOfferIfAuthorized(int offerId);
}

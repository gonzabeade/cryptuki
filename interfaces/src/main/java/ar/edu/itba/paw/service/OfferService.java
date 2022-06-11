package ar.edu.itba.paw.service;
import ar.edu.itba.paw.model.Offer;
import ar.edu.itba.paw.OfferFilter;
import ar.edu.itba.paw.parameterObject.OfferPO;

import java.util.Collection;
import java.util.Optional;

public interface OfferService {


    int makeOffer(OfferPO offerPO);
    Optional<Offer> getOfferById(int id);


    Collection<Offer> getOfferBy(OfferFilter filter);
    long countOffersBy(OfferFilter filter);

    Collection<Offer> getMarketOffersBy(OfferFilter filter, String buyerUsername);
    long countMarketOffersBy(OfferFilter filter, String buyerUsername);

    long countOffersByUsername(String username);
    Collection<Offer> getOffersByUsername(String username, int page, int pageSize);


    void modifyOffer(Offer offer);
    void deleteOffer(int offerId);

    void hardPauseOffer(int offerId);
    void pauseOffer(int offerId);
    void resumeOffer(int offerId);

    void soldOffer(Offer offer, float sold, int tradeId);

    Optional<Offer> getOfferIfAuthorized(int offerId);
}

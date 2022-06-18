package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.parameterObject.OfferPO;

import java.util.Collection;
import java.util.Optional;

public interface OfferDao {

    /** Offer manipulation and creation */
    Offer makeOffer(OfferPO offer);
    Offer modifyOffer(Offer offer);
    void deleteOffer(int offerId);
    Optional<Offer> changeOfferStatus(int offerId, OfferStatus offerStatus);

    /** Offer getter methods */
    long getOfferCount(OfferFilter filter);
    Collection<Offer> getOffersBy(OfferFilter filter);

    /**
     * Returns a collection of all locations and their offer count. The returned map is
     * ordered in descending order of the number of times each location appears in an offer
     */
    Collection<LocationCountWrapper> getOfferCountByLocation();

}

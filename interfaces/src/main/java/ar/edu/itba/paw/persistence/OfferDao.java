package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Offer;
import ar.edu.itba.paw.model.OfferStatus;
import ar.edu.itba.paw.OfferFilter;
import ar.edu.itba.paw.parameterObject.OfferPO;

import java.util.Collection;
import java.util.Optional;

public interface OfferDao {

    /** Offer getter methods */
    long getOfferCount(OfferFilter filter);
    Collection<Offer> getOffersBy(OfferFilter filter);

    /** Offer manipulation and creation */
    Offer makeOffer(OfferPO offer);
    Offer modifyOffer(Offer offer);
    void deleteOffer(int offerId);
    Optional<Offer> changeOfferStatus(int offerId, OfferStatus offerStatus);

}

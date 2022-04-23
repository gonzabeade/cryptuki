package ar.edu.itba.paw.service;
import ar.edu.itba.paw.persistence.Offer;
import ar.edu.itba.paw.persistence.OfferFilter;

import java.util.Collection;
import java.util.Optional;

public interface OfferService {

    // Todo: Immutable Collections
    Offer makeOffer(Offer.Builder builder);
    Optional<Offer> getOfferById(int id);

    Collection<Offer> getOfferBy(OfferFilter filter);
    int countOffersBy(OfferFilter filter);
}

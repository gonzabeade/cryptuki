package ar.edu.itba.paw.service;
import ar.edu.itba.paw.persistence.Offer;

import java.util.Collection;
import java.util.Optional;

public interface OfferService {

    // Todo: Immutable Collections
    Offer makeOffer(Offer.Builder builder);
    Optional<Offer> getOfferById(int id);

    Collection<Offer> getOfferByCrypto(int page, int pageSize, String cryptoCode);
    int countOffersByCrypto(String cryptoCode);
}

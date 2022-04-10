package ar.edu.itba.paw.service;
import ar.edu.itba.paw.persistence.Offer;

public interface OfferService {

    // Todo: Immutable Collections
    Offer makeOffer(Offer.Builder builder);
    Iterable<Offer> getPagedOffers(int page, int pageSize);
    Iterable<Offer> getAllOffers();
    Offer getOffer(int id);

}

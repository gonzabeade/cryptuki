package ar.edu.itba.paw.service;
import ar.edu.itba.paw.persistence.Offer;

public interface OfferService {

    // Todo: Immutable Collections
    Offer makeOffer(Offer.Builder builder);
    Iterable<Offer> getAllOffers();
    Offer getOffer(int id);
    int getOfferCount();

    Paginator<Offer> getPaginator();
}

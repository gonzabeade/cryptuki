package ar.edu.itba.paw.service;
import ar.edu.itba.paw.persistence.Offer;

public interface OfferService {

    Offer makeOffer(Offer.Builder builder);
    Iterable<Offer> getPagedOffers(int page, int pageSize);
    Iterable<Offer> getAllOffers();
    Offer getOffer(int id);

}

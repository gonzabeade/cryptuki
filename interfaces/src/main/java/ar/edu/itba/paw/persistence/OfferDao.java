package ar.edu.itba.paw.persistence;

import java.util.Date;
import java.util.List;

public interface OfferDao {

    Offer makeOffer(Offer.Builder builder);
    List<Offer> getAllOffers();


}

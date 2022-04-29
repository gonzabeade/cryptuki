package ar.edu.itba.paw.persistence;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface OfferDao {
    int getOfferCount(OfferFilter filter);
    Collection<Offer> getOffersBy(OfferFilter filter);

    Offer makeOffer(Offer.Builder builder);
}

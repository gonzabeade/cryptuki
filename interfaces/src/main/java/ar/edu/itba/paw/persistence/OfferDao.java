package ar.edu.itba.paw.persistence;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface OfferDao {

    int getOfferCount();

    Collection<Offer> getOffersBy(OfferFilter filter);


}

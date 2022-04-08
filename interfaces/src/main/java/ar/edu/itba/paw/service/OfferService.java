package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Offer;

import java.util.Date;
import java.util.List;

public interface OfferService {

    Offer makeOffer(Offer.Builder builder);

    List<Offer> getAllOffers();

}

package ar.edu.itba.paw.service;

import ar.edu.itba.paw.Offer;

import java.util.Date;
import java.util.List;

public interface OfferService {

    Offer makeOffer(int seller_id, Date offer_date,String coin_id, double asking_price, double coin_amount);

    List<Offer> getAllOffers();

}

package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.Offer;

import java.util.Date;
import java.util.List;

public interface OfferDao {

    int makeOffer(int seller_id, Date offer_date,String coin_id, double asking_price, double coin_amount);
    List<Offer> getAllOffers();


}

package ar.edu.itba.paw.service;

import ar.edu.itba.paw.Offer;
import ar.edu.itba.paw.persistence.OfferDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService{

    @Autowired
    private OfferDao offerDao;

//    @Autowired
//    public OfferServiceImpl(OfferDao offerDao) {
//        this.offerDao = offerDao;
//    }

    @Override
    public Offer makeOffer(int seller_id, Date offer_date,String coin_id, double asking_price, double coin_amount) {
        return offerDao.makeOffer(seller_id,offer_date,coin_id,asking_price,coin_amount);
    }

    @Override
    public List<Offer> getAllOffers() {
        return offerDao.getAllOffers();
    }
}

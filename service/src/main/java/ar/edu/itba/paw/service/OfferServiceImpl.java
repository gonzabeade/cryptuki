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

    @Override
    public int makeOffer(int sellerId, Date date, String coinId, double askingPrice, double coinAmount) {
        return offerDao.makeOffer(sellerId, date, coinId, askingPrice, coinAmount);
    }

    @Override
    public List<Offer> getAllOffers() {
        return offerDao.getAllOffers();
    }
}

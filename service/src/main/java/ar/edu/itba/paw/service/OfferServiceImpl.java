package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Offer;
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
    public Offer makeOffer(Offer.Builder builder) {
        return offerDao.makeOffer(builder);
    }

    @Override
    public List<Offer> getAllOffers() {
        return offerDao.getAllOffers();
    }
}

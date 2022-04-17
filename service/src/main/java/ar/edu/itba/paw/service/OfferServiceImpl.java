package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Offer;
import ar.edu.itba.paw.persistence.OfferDao;
import com.sun.org.apache.xml.internal.dtm.ref.DTMAxisIteratorBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    private OfferDao offerDao;

    @Override
    public Offer makeOffer(Offer.Builder builder) {
        // TODO: Back-end validation of builder
        return null; // offerDao.makeOffer(builder);
    }

    @Override
    public Iterable<Offer> getPagedOffers(int page, int pageSize) {
      //TODO: validations
        return offerDao.getPagedOffers(page,pageSize);
    }

    @Override
    public Iterable<Offer> getAllOffers() {
        Iterable<Offer> x = offerDao.getAllOffers();
        for ( Offer t: x) {
            System.out.println(t);
        }
        return x;
    }

    @Override
    public Offer getOffer(int id) {
        return offerDao.getOffer(id);
    }

    @Override
    public int getOfferCount() {
        return offerDao.getOfferCount();
    }
}

package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Offer;
import ar.edu.itba.paw.persistence.OfferDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class OfferServiceImpl implements OfferService {

    private OfferDao offerDao;
    private Paginator<Offer> offerPaginator;

    private class OfferPaginator implements Paginator<Offer> {
        private static final int PAGE_SIZE = 3;
        private static final int TTL = 1;
        int offerCount;
        LocalDateTime timeToLive = LocalDateTime.now();

        private void resetOfferCount() {
            if ( timeToLive.isAfter(LocalDateTime.now())) return;
            offerCount = getOfferCount();
            timeToLive = LocalDateTime.now().plusMinutes(TTL);
        }
        private OfferPaginator() {
            resetOfferCount();
        }
        @Override
        public boolean isPageValid(int pageNumber) {
            return pageNumber >= 0 && pageNumber <= getPageCount();
        }
        @Override
        public int getPageCount() {
            resetOfferCount();
            return 1 +  (offerCount-1) / PAGE_SIZE ;
        }

        @Override
        public Iterable<Offer> getPagedObjects(int pageNumber) {
            if (isPageValid(pageNumber))
                return offerDao.getPagedOffers(pageNumber, PAGE_SIZE);
            else
                return new ArrayList<>();  // Return empty, invalid pageNumber
        }
    }

    @Autowired
    public OfferServiceImpl(OfferDao offerDao) {
        this.offerDao = offerDao;
        this.offerPaginator = new OfferPaginator();
    }

    @Override
    public Offer makeOffer(Offer.Builder builder) {
        // TODO: Back-end validation of builder
        return null; // offerDao.makeOffer(builder);
    }
    @Override
    public Iterable<Offer> getAllOffers() {
        return offerDao.getAllOffers();
    }

    @Override
    public Offer getOffer(int id) {
        return offerDao.getOffer(id);
    }

    @Override
    public int getOfferCount() {
        return offerDao.getOfferCount();
    }

    @Override
    public Paginator<Offer> getPaginator() {
        return offerPaginator;
    }
}

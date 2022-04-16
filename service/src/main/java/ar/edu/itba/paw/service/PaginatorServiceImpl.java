package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaginatorServiceImpl implements PaginatorService<Offer>{
    OfferService offerService;

    private static final int PAGE_SIZE = 2;
    @Autowired
    public PaginatorServiceImpl(OfferServiceImpl offerService) {
        this.offerService = offerService;
    }

    @Override
    public boolean validatePage(int pageNumber) {
        return pageNumber >= 0;
    }

    @Override
    public int getPageCount() {
        return 1 +  (offerService.getOfferCount()-1) / PAGE_SIZE ;
    }

    @Override
    public Iterable<Offer> getPagedObjects(int pageNumber) {
        return offerService.getPagedOffers(pageNumber, PAGE_SIZE);
    }
}

package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Offer;
import ar.edu.itba.paw.persistence.OfferDao;
import ar.edu.itba.paw.persistence.OfferFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Filter;

@Service
public class OfferServiceImpl implements OfferService {

    private OfferDao offerDao;
    private Paginator<Offer> offerPaginator;

    @Autowired
    public OfferServiceImpl(OfferDao offerDao) {
        this.offerDao = offerDao;
    }

    @Override
    public Offer makeOffer(Offer.Builder builder) {
        // TODO: Back-end validation of builder
        return null; // offerDao.makeOffer(builder);
    }

    @Override
    public Optional<Offer> getOfferById(int id) {
        Collection<Offer> offer = offerDao.getOffersBy(new OfferFilter().byOfferId(id));
        if (offer.isEmpty())
            return Optional.empty();
        else
            return Optional.of(offer.iterator().next());
    }

    @Override
    public Collection<Offer> getOfferByCrypto(int page, int pageSize, String cryptoCode) {
        return offerDao.getOffersBy(new OfferFilter().byCryptoCode(cryptoCode).withPageSize(pageSize).fromPage(page));
    }

    @Override
    public int countOffersByCrypto(String cryptoCode) {
        return offerDao.getOfferCount(new OfferFilter().byCryptoCode(cryptoCode));
    }




}

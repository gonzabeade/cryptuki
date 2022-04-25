package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Offer;
import ar.edu.itba.paw.persistence.OfferDao;
import ar.edu.itba.paw.persistence.OfferFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {

    private OfferDao offerDao;

    @Autowired
    public OfferServiceImpl(OfferDao offerDao) {
        this.offerDao = offerDao;
    }

    @Override
    public Offer makeOffer(Offer.Builder builder) {
        return offerDao.makeOffer(builder);
    }

    @Override
    public Optional<Offer> getOfferById(int id) {
        Collection<Offer> offer = offerDao.getOffersBy(new OfferFilter().byOfferId(id));
        return offer.isEmpty() ? Optional.empty() : Optional.of(offer.iterator().next());
    }

    @Override
    public Collection<Offer> getOfferBy(OfferFilter filter) {
        return offerDao.getOffersBy(filter);
    }

    @Override
    public int countOffersBy(OfferFilter filter) {
        return offerDao.getOfferCount(filter);
    }




}

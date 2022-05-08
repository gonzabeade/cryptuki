package ar.edu.itba.paw.service;

import ar.edu.itba.paw.OfferDigest;
import ar.edu.itba.paw.exception.ServiceDataAccessException;
import ar.edu.itba.paw.exception.UncategorizedPersistenceException;
import ar.edu.itba.paw.persistence.Offer;
import ar.edu.itba.paw.persistence.OfferDao;
import ar.edu.itba.paw.OfferFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferDao offerDao;

    @Autowired
    public OfferServiceImpl(OfferDao offerDao) {
        this.offerDao = offerDao;
    }

    @Override
    @Transactional
    public int makeOffer(OfferDigest digest) {

        if (digest == null)
            throw new NullPointerException("Offer digest cannot be null");

        try {
            return offerDao.makeOffer(digest);
        } catch (UncategorizedPersistenceException upe) {
            throw new ServiceDataAccessException(upe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Offer> getOfferById(int id) {

        if (id < 0)
            throw new IllegalArgumentException("Offer id can only be non negative");

        try {
            Collection<Offer> offer = offerDao.getOffersBy(new OfferFilter().byOfferId(id));
            return offer.isEmpty() ? Optional.empty() : Optional.of(offer.iterator().next());
        } catch (UncategorizedPersistenceException upe) {
            throw new ServiceDataAccessException(upe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Offer> getOffersByUsername(String username) {

        if (username == null)
            throw new NullPointerException("Username cannot be null");

        try {
            return offerDao.getOffersBy(new OfferFilter().byUsername(username));
        } catch (UncategorizedPersistenceException upe) {
            throw new ServiceDataAccessException(upe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Offer> getOfferBy(OfferFilter filter) {

        if (filter == null)
            throw new NullPointerException("Offer filter cannot be null");

        try {
            return offerDao.getOffersBy(filter);
        } catch (UncategorizedPersistenceException upe) {
            throw new ServiceDataAccessException(upe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public int countOffersBy(OfferFilter filter) {

        try {
            return offerDao.getOfferCount(filter);
        } catch (UncategorizedPersistenceException upe) {
            throw new ServiceDataAccessException(upe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public int countOffersByUsername(String username) {

        if (username == null)
            throw new NullPointerException("Username cannot be null");

        try {
            return offerDao.getOfferCount(new OfferFilter().byUsername(username));
        } catch (UncategorizedPersistenceException upe) {
            throw new ServiceDataAccessException(upe);
        }
    }

    @Override
    @Transactional
    public void modifyOffer(OfferDigest digest) {

        if (digest == null)
            throw new NullPointerException("Offer digest cannot be null");

        try {
            offerDao.modifyOffer(digest);
        } catch (UncategorizedPersistenceException upe) {
            throw new ServiceDataAccessException(upe);
        }
    }

    @Override
    @Transactional
    public void deleteOffer(int offerId) {

        if (offerId < 0)
            throw new IllegalArgumentException("Id can only be non negative");

        try {
            offerDao.deleteOffer(offerId);
        } catch (UncategorizedPersistenceException upe) {
            throw new ServiceDataAccessException(upe);
        }
    }

    @Override
    @Transactional
    public void pauseOffer(int offerId) {

        if (offerId < 0)
            throw new IllegalArgumentException("Id can only be non negative");

        try {
            offerDao.pauseOffer(offerId);
        } catch (UncategorizedPersistenceException upe) {
            throw new ServiceDataAccessException(upe);
        }

    }

    @Override
    @Transactional
    public void hardPauseOffer(int offerId) {

        if (offerId < 0)
            throw new IllegalArgumentException("Id can only be non negative");

        try {
            offerDao.hardPauseOffer(offerId);
        } catch (UncategorizedPersistenceException upe) {
            throw new ServiceDataAccessException(upe);
        }
    }

    @Override
    @Transactional
    public void resumeOffer(int offerId) {

        if (offerId < 0)
            throw new IllegalArgumentException("Id can only be non negative");

        try {
            offerDao.resumeOffer(offerId);
        } catch (UncategorizedPersistenceException upe) {
            throw new ServiceDataAccessException(upe);
        }
    }


}

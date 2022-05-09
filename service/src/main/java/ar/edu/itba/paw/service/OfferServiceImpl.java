package ar.edu.itba.paw.service;

import ar.edu.itba.paw.OfferDigest;
import ar.edu.itba.paw.exception.ForbiddenServiceException;
import ar.edu.itba.paw.exception.PersistenceException;
import ar.edu.itba.paw.exception.ServiceDataAccessException;
import ar.edu.itba.paw.persistence.Offer;
import ar.edu.itba.paw.persistence.OfferDao;
import ar.edu.itba.paw.OfferFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    @Secured("ROLE_USER")
    @PreAuthorize("@customPreAuthorizer.isUserAuthorized(#digest.sellerId, authentication.principal)")
    public int makeOffer(OfferDigest digest) {

        if (digest == null)
            throw new NullPointerException("Offer digest cannot be null");

        try {
            return offerDao.makeOffer(digest);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Offer> getOfferById(int id) {

        if (id < 0)
            throw new IllegalArgumentException("Offer id can only be non negative");

        try {
            Collection<Offer> offer = offerDao.getOffersBy(new OfferFilter()
                    .byOfferId(id)
                    .byStatus("APR")
                    .byStatus("PSE")
                    .byStatus("PSU"));
            return offer.isEmpty() ? Optional.empty() : Optional.of(offer.iterator().next());
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Offer> getOffersByUsername(String username) {

        if (username == null)
            throw new NullPointerException("Username cannot be null");

        try {
            return offerDao.getOffersBy(new OfferFilter().byUsername(username));
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Offer> getOfferBy(OfferFilter filter) {

        if (filter == null)
            throw new NullPointerException("Offer filter cannot be null");

        try {
            return offerDao.getOffersBy(filter.byStatus("APR"));
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public int countOffersBy(OfferFilter filter) {

        if (filter == null)
            throw new NullPointerException("Filter object cannot be null");

        try {
            return offerDao.getOfferCount(filter.byStatus("APR"));
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public int countOffersByUsername(String username) {

        if (username == null)
            throw new NullPointerException("Username cannot be null");

        try {
            return offerDao.getOfferCount(new OfferFilter().byUsername(username));
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') or @customPreAuthorizer.isUserAuthorized(#digest.sellerId, authentication.principal)")
    public void modifyOffer(OfferDigest digest) {

        if (digest == null)
            throw new NullPointerException("Offer digest cannot be null");

        try {
            offerDao.modifyOffer(digest);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') or @customPreAuthorizer.isUserOwnerOfOffer(#offerId, authentication.principal)")
    public void deleteOffer(int offerId) {

        if (offerId < 0)
            throw new IllegalArgumentException("Id can only be non negative");

        try {
            offerDao.deleteOffer(offerId);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') or @customPreAuthorizer.isUserOwnerOfOffer(#offerId, authentication.principal)")
    public void pauseOffer(int offerId) {

        if (offerId < 0)
            throw new IllegalArgumentException("Id can only be non negative");

        try {
            offerDao.pauseOffer(offerId);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }

    }

    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public void hardPauseOffer(int offerId) {

        if (offerId < 0)
            throw new IllegalArgumentException("Id can only be non negative");

        try {
            offerDao.hardPauseOffer(offerId);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN') or @customPreAuthorizer.isUserOwnerOfOffer(#offerId, authentication.principal)")
    public void resumeOffer(int offerId) {

        if (offerId < 0)
            throw new IllegalArgumentException("Id can only be non negative");

        try {
            offerDao.resumeOffer(offerId);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }


}

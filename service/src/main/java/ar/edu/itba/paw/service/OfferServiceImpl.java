package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Offer;
import ar.edu.itba.paw.model.OfferStatus;
import ar.edu.itba.paw.model.TradeStatus;
import ar.edu.itba.paw.OfferFilter;
import ar.edu.itba.paw.exception.NoSuchOfferException;
import ar.edu.itba.paw.exception.PersistenceException;
import ar.edu.itba.paw.exception.ServiceDataAccessException;
import ar.edu.itba.paw.parameterObject.OfferPO;
import ar.edu.itba.paw.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {

    private  OfferDao offerDao;
    private  MessageSenderFacade messageSenderFacade;
    private TradeDao tradeDao ;

    @Autowired
    public OfferServiceImpl(OfferDao offerDao, MessageSenderFacade messageSenderFacade, TradeDao tradeDao) {
        this.offerDao = offerDao;
        this.messageSenderFacade = messageSenderFacade;
        this.tradeDao = tradeDao;
    }

    @Override
    @Transactional
    @Secured("ROLE_USER")
    @PreAuthorize("@customPreAuthorizer.isUserAuthorized(#digest.sellerId, authentication.principal)")
    public int makeOffer(OfferPO offerPO) {

        if (offerPO == null)
            throw new NullPointerException("Offer digest cannot be null");

        try {
            Offer offer = offerDao.makeOffer(offerPO);
            int offerId = offer.getOfferId();
            messageSenderFacade.sendOfferUploadedMessage(SecurityContextHolder.getContext().getAuthentication().getName(), offer);
            return offerId;
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
    public Collection<Offer> getOffersByUsername(String username, int page, int pageSize) {

        if (username == null)
            throw new NullPointerException("Username cannot be null");

        try {
            return offerDao.getOffersBy(new OfferFilter().byUsername(username).withPageSize(pageSize)
                    .byStatus("APR")
                    .byStatus("PSE")
                    .byStatus("PSU")
                    .fromPage(page)
            );
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
    public Collection<Offer> getMarketOffersBy(OfferFilter filter, String buyerUsername){
        filter.whereUsernameNot(buyerUsername);
        return getOfferBy(filter);
    }

    @Override
    @Transactional(readOnly = true)
    public int countMarketOffersBy(OfferFilter filter, String buyerUsername){
        filter.whereUsernameNot(buyerUsername);
        return countOffersBy(filter);
    }

    @Override
    @Transactional(readOnly = true)
    public int countOffersByUsername(String username) {

        if (username == null)
            throw new NullPointerException("Username cannot be null");

        try {
            return offerDao.getOfferCount(new OfferFilter().byUsername(username)
                    .byStatus("APR")
                    .byStatus("PSE")
                    .byStatus("PSU"));
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional
    @PreAuthorize("@customPreAuthorizer.isUserOwnerOfOffer(#digest.id, authentication.principal) OR hasRole('ROLE_ADMIN')")
    public void modifyOffer(Offer offer) {

        if (offer == null)
            throw new NullPointerException("Offer digest cannot be null");

        try {
            offerDao.modifyOffer(offer);
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
            offerDao.changeOfferStatus(offerId, OfferStatus.PSE);
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
            offerDao.changeOfferStatus(offerId, OfferStatus.PSU);
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
            offerDao.changeOfferStatus(offerId, OfferStatus.APR);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER')")
    public void soldOffer(Offer offer, float sold, int tradeId) {

//        tradeDao.updateStatus(tradeId, TradeStatus.SOLD);
//        float remaining = offer.getMaxQuantity() - (sold/offer.getunitPrice());
//        try {
//            if (remaining <= offer.getMinQuantity()){
//                Offer mergeOffer = offerDao.getOffersBy(new OfferFilter().byOfferId(offer.getId())).stream().findFirst().orElseThrow(()->new NoSuchOfferException(offer.getId()));
//                OfferStatus offerStatus = this.offerStatusDao.getOfferStatusByCode("PSE").get();
//                mergeOffer.setStatus(offerStatus);
//                mergeOffer.getAssociatedTrades().stream().forEach(trade -> {
//                    if(trade.getStatus().equals(TradeStatus.PENDING))
//                        trade.setStatus(TradeStatus.REJECTED);
//                });
//                mergeOffer.setMinQuantity(0);
//                mergeOffer.setMaxQuantity(remaining);
//            }
//            else
//                offerDao.setMaxQuantity(offer.getId(), remaining);
//        } catch (PersistenceException pe) {
//            throw new ServiceDataAccessException(pe);
//        }


    }

    @Override
    @PreAuthorize("@customPreAuthorizer.isUserOwnerOfOffer(#offerId, authentication.principal) OR hasRole('ROLE_ADMIN') ")
    public Optional<Offer> getOfferIfAuthorized(int offerId) {
        return getOfferById(offerId);
    }


}

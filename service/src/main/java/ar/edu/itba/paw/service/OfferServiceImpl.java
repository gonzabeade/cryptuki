package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.NoSuchOfferException;
import ar.edu.itba.paw.exception.UnmodifiableOfferException;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.exception.PersistenceException;
import ar.edu.itba.paw.exception.ServiceDataAccessException;
import ar.edu.itba.paw.model.parameterObject.OfferPO;
import ar.edu.itba.paw.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferDao offerDao;
    private final MessageSenderFacade messageSenderFacade;
    private final TradeDao tradeDao ;

    @Autowired
    public OfferServiceImpl(OfferDao offerDao, MessageSenderFacade messageSenderFacade, TradeDao tradeDao) {
        this.offerDao = offerDao;
        this.messageSenderFacade = messageSenderFacade;
        this.tradeDao = tradeDao;
    }

    @Override
    @Transactional
    @Secured("ROLE_USER")
    //@PreAuthorize("@customPreAuthorizer.isUserAuthorized(#offerPO.sellerId, authentication.principal)") TODO GONZA: Falla esto
    public Offer makeOffer(OfferPO offerPO) {

        if (offerPO == null)
            throw new NullPointerException("Offer digest cannot be null");

        Offer offer;
        try {
            offer = offerDao.makeOffer(offerPO);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }

        messageSenderFacade.sendOfferUploadedMessage(offer);
        return offer;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Offer> getOfferById(int id) {

        if (id < 0)
            throw new IllegalArgumentException("Offer id can only be non negative");

        try {
            Collection<Offer> offer = offerDao.getOffersBy(new OfferFilter()
                    .restrictedToId(id)
                    .withOfferStatus("APR")
                    .withOfferStatus("PSE")
                    .withOfferStatus("PSU"));
            return offer.isEmpty() ? Optional.empty() : Optional.of(offer.iterator().next());
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Offer> getOffersByUsername(String username, int page, int pageSize, Set<OfferStatus> status) {

        if (username == null)
            throw new NullPointerException("Username cannot be null");

        try {
            OfferFilter filter = new OfferFilter().restrictedToUsername(username).withPageSize(pageSize).withPage(page);
            for (OfferStatus s: status)
                filter.withOfferStatus(s);
            return offerDao.getOffersBy(filter);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Collection<Offer> getBuyableOffers(OfferFilter filter){
        filter.withOfferStatus(OfferStatus.APR);
        String buyerUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (buyerUsername != null)
            filter.excludeUsername(buyerUsername);
        return offerDao.getOffersBy(filter);
    }

    @Override
    @Transactional(readOnly = true)
    public long countBuyableOffers(OfferFilter filter){

        String buyerUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (buyerUsername != null)
            filter.excludeUsername(buyerUsername);
        return offerDao.getOfferCount(filter);
    }

    @Override
    @Transactional(readOnly = true)
    public long countOffersByUsername(String username, Set<OfferStatus> status) {

        if (username == null)
            throw new NullPointerException("Username cannot be null");

        try {
            OfferFilter filter = new OfferFilter().restrictedToUsername(username);
            for (OfferStatus s: status)
                    filter.withOfferStatus(s);
            return offerDao.getOfferCount(filter);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional
    @PreAuthorize("@customPreAuthorizer.isUserOwnerOfOffer(#offer.offerId, authentication.principal) OR hasRole('ROLE_ADMIN')")
    public Offer modifyOffer(OfferPO offerPO) {

        if (offerPO == null)
            throw new NullPointerException("Offer digest cannot be null");
        //trade DAO chequear que no tenga trades asociados a la oferta , de lo contrario explotar

        Integer offerId = offerPO.getOfferId().orElseThrow(IllegalArgumentException::new);
        if(tradeDao.getTradesAsSellerCount(SecurityContextHolder.getContext().getAuthentication().getName(), EnumSet.allOf(TradeStatus.class), offerId) > 0){
            throw new UnmodifiableOfferException(offerId);
        }

        try {
            Offer offer = getOfferIfAuthorized(offerId).orElseThrow(()-> new NoSuchOfferException(offerId));
            return offerDao.modifyOffer(OfferPO.mergeParameterObject(offer, offerPO));
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @customPreAuthorizer.isUserOwnerOfOffer(#offerId, authentication.principal)")
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
    @PreAuthorize("hasRole('ADMIN') or @customPreAuthorizer.isUserOwnerOfOffer(#offerId, authentication.principal)")
    public void sellerPauseOffer(int offerId) {

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
    public void adminPauseOffer(int offerId) {

        if (offerId < 0)
            throw new IllegalArgumentException("Id can only be non negative");

        try {
            offerDao.changeOfferStatus(offerId, OfferStatus.PSU);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    public Collection<LocationCountWrapper> getOfferCountByLocation() {
        return offerDao.getOfferCountByLocation();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @customPreAuthorizer.isUserOwnerOfOffer(#offerId, authentication.principal)")
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
    public void sellQuantityOfOffer(Offer offer, double sold, int tradeId) {

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
    @PreAuthorize("@customPreAuthorizer.isUserOwnerOfOffer(#offerId, authentication.principal) OR hasRole('ADMIN') ")
    public Optional<Offer> getOfferIfAuthorized(int offerId) {
        return getOfferById(offerId);
    }


}

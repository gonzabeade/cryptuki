package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.NoSuchOfferException;
import ar.edu.itba.paw.exception.UnmodifiableOfferException;
import ar.edu.itba.paw.model.*;
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
    @PreAuthorize("@customPreAuthorizer.canUserUploadOffer(authentication.principal)")
    public Offer makeOffer(OfferPO offerPO) {

        if (offerPO == null)
            throw new NullPointerException("Offer digest cannot be null");

        Offer offer = offerDao.makeOffer(offerPO);
        messageSenderFacade.sendOfferUploadedMessage(offer);
        return offer;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Offer> getOfferById(int id) {

        if (id < 0)
            throw new IllegalArgumentException("Offer id can only be non negative");

        Collection<Offer> offer = offerDao.getOffersBy(new OfferFilter().restrictedToId(id).withOfferStatus(EnumSet.allOf(OfferStatus.class)));
        return offer.stream().findFirst();
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Offer> getOffersByUsername(String username, int page, int pageSize, Set<OfferStatus> status) {
        if (username == null)
            throw new NullPointerException("Username cannot be null");
        OfferFilter filter = new OfferFilter().restrictedToUsername(username).withPageSize(pageSize).withPage(page);
        filter.withOfferStatus(status);
        return offerDao.getOffersBy(filter);
    }


    @Override
    @Transactional(readOnly = true)
    public Collection<Offer> getOffers(OfferFilter filter){
        return offerDao.getOffersBy(filter);
    }

    @Override
    @Transactional(readOnly = true)
    public long countOffers(OfferFilter filter){
        return offerDao.getOfferCount(filter);
    }

    @Override
    @Transactional(readOnly = true)
    public long countOffersByUsername(String username, Set<OfferStatus> status) {

        if (username == null)
            throw new NullPointerException("Username cannot be null");

        OfferFilter filter = new OfferFilter().restrictedToUsername(username);
        filter.withOfferStatus(status);
        return offerDao.getOfferCount(filter);
    }

    @Override
    @Transactional
    @PreAuthorize("@customPreAuthorizer.canUserAlterOffer(authentication.principal, #offerPO.offerId.get())")
    public Offer modifyOffer(OfferPO offerPO) {

        if (offerPO == null)
            throw new NullPointerException("Offer digest cannot be null");
        //trade DAO chequear que no tenga trades asociados a la oferta , de lo contrario explotar

        Integer offerId = offerPO.getOfferId().orElseThrow(IllegalArgumentException::new);
        if(tradeDao.getTradesAsSellerCount(SecurityContextHolder.getContext().getAuthentication().getName(), EnumSet.allOf(TradeStatus.class), offerId) > 0){
            throw new UnmodifiableOfferException(offerId);
        }

        Offer offer = getOfferIfAuthorized(offerId).orElseThrow(()-> new NoSuchOfferException(offerId));
        return offerDao.modifyOffer(OfferPO.mergeParameterObject(offer, offerPO));
    }

    @Override
    @Transactional
    @PreAuthorize("@customPreAuthorizer.canUserAlterOffer(authentication.principal, #offerId) OR hasRole('ADMIN')\n")
    public void deleteOffer(int offerId) {
        if (offerId < 0)
            throw new IllegalArgumentException("Id can only be non negative");
        offerDao.deleteOffer(offerId);
    }

    @Override
    @Transactional
    @PreAuthorize("@customPreAuthorizer.canUserAlterOffer(authentication.principal, #offerId) OR hasRole('ADMIN')\n")
    public void sellerPauseOffer(int offerId) {
        if (offerId < 0)
            throw new IllegalArgumentException("Id can only be non negative");
        offerDao.changeOfferStatus(offerId, OfferStatus.PSE);
    }

    @Override
    @Transactional
    @Secured("ROLE_ADMIN")
    public void adminPauseOffer(int offerId) {
        if (offerId < 0)
            throw new IllegalArgumentException("Id can only be non negative");
        offerDao.changeOfferStatus(offerId, OfferStatus.PSU);
    }

    @Override
    public Collection<LocationCountWrapper> getOfferCountByLocation() {
        return offerDao.getOfferCountByLocation();
    }

    @Override
    @Transactional
    @PreAuthorize("@customPreAuthorizer.canUserAlterOffer(authentication.principal, #offerId) OR hasRole('ADMIN')\n")
    public void resumeOffer(int offerId) {

        if (offerId < 0)
            throw new IllegalArgumentException("Id can only be non negative");

        offerDao.changeOfferStatus(offerId, OfferStatus.APR);
    }

    @Override
    @PreAuthorize("@customPreAuthorizer.canUserAlterOffer(authentication.principal, #offerId) OR hasRole('ADMIN')\n")
    public Optional<Offer> getOfferIfAuthorized(int offerId) {
        return getOfferById(offerId);
    }

}

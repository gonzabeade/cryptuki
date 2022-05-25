package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.OfferDigest;
import ar.edu.itba.paw.OfferFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Repository
public class OfferHibernateDao implements OfferDao{
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PaymentMethodDao paymentMethodDao;


    @Override
    public int getOfferCount(OfferFilter filter) {
        TypedQuery<Offer> query = entityManager.createQuery("from Offer",Offer.class);
        return query.getResultList().size();
    }

    @Override
    public Collection<Offer> getOffersBy(OfferFilter filter) {
        TypedQuery<Offer> query = entityManager.createQuery("from Offer",Offer.class);
        return query.getResultList();
    }

    @Override
    public int makeOffer(OfferDigest digest) {
        entityManager.persist(digest);
        for(String pmCode : digest.getPaymentMethods())
            entityManager.persist( new PaymentMethodAtOffer( digest.getId() , paymentMethodDao.getPaymentMethodByCode(pmCode).get().getName()) );
        return digest.getId();
    }

    @Override
    public void modifyOffer(OfferDigest digest) {

    }

    @Override
    public void deleteOffer(int offerId) {
        changeStatus(offerId,"DEL");
    }

    @Override
    public void hardPauseOffer(int offerId) {
        changeStatus(offerId,"PSU");
    }


    @Override
    public void pauseOffer(int offerId) {
        changeStatus(offerId,"PSE");
    }

    @Override
    public void resumeOffer(int offerId) {
        changeStatus(offerId,"APR");
    }



    @Override
    public Optional<String> getOwner(int offerId) {
        return Optional.empty();
    }

    @Override
    public void setMaxQuantity(int offerId, float newQuantity) {
        OfferDigest offerDigest = getOfferDigestById(offerId);
        offerDigest.setMaxQuantity(newQuantity);
        entityManager.persist(offerDigest);
    }

    private void changeStatus(int offerId,String statusCode){
        OfferDigest offerDigest = getOfferDigestById(offerId);
        offerDigest.setStatusCode(statusCode);
        entityManager.persist(offerDigest);
    }

    private OfferDigest getOfferDigestById(int offerId){
        TypedQuery<OfferDigest> query = entityManager.createQuery("from OfferDigest as od where od.id = :offerId ",OfferDigest.class);
        query.setParameter("offerId",offerId);
        return query.getSingleResult();
    }


}

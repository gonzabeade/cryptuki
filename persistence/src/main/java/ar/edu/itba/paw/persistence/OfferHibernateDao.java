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
        return 0;
    }

    @Override
    public Collection<Offer> getOffersBy(OfferFilter filter) {
        return new ArrayList<>();
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

    }

    @Override
    public void hardPauseOffer(int offerId) {

    }

    @Override
    public void pauseOffer(int offerId) {

    }

    @Override
    public void resumeOffer(int offerId) {

    }

    @Override
    public Optional<String> getOwner(int offerId) {
        return Optional.empty();
    }

    @Override
    public void setMaxQuantity(int offerId, float newQuantity) {

    }
}

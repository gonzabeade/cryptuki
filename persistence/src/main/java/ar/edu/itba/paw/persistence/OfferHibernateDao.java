package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.OfferDigest;
import ar.edu.itba.paw.OfferFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
public class OfferHibernateDao implements OfferDao{
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PaymentMethodDao paymentMethodDao;

    private static void addParameters(OfferFilter filter,TypedQuery<Offer> query) {
        query
                .setParameter("crypto_codes", filter.getCryptoCodes().isEmpty() ? null: filter.getCryptoCodes())
                .setParameter("offer_ids", filter.getIds().isEmpty() ? null : filter.getIds())
//                .setParameter("limit", filter.getPageSize())
//                .setParameter("offset", filter.getPage()*filter.getPageSize())
                .setParameter("min", filter.getMinPrice().isPresent() ? (float)filter.getMinPrice().getAsDouble() : null)
                .setParameter("max", filter.getMaxPrice().isPresent() ? (float)filter.getMaxPrice().getAsDouble() : null)
                .setParameter("uname", filter.getUsername().orElse(null))
                .setParameter("status", filter.getStatus().isEmpty() ? null: filter.getStatus());
    }


    @Override
    public int getOfferCount(OfferFilter filter) {
        return getOffersBy(filter).size();
    }

    @Override
    public Collection<Offer> getOffersBy(OfferFilter filter) {
            String paymentCodeQueryDescriptor=" from PaymentMethodAtOffer AS pmao WHERE ( COALESCE(:payment_codes,null) IS NULL OR pmao.paymentCode IN (:payment_codes) ) ";
            TypedQuery<PaymentMethodAtOffer> paymentCodeQuery = entityManager.createQuery(paymentCodeQueryDescriptor,PaymentMethodAtOffer.class);
            paymentCodeQuery.setParameter("payment_codes",filter.getPaymentMethods().isEmpty()?null:filter.getPaymentMethods());
            List<Integer> paymentMethodAtOffersList = paymentCodeQuery.getResultList().stream().map(paymentMethodAtOffer -> paymentMethodAtOffer.getOfferId()).collect(Collectors.toCollection(ArrayList::new));
            if(paymentMethodAtOffersList.isEmpty()) //no offers with such payment method
                return new ArrayList<>();

            String queryDescriptor="from Offer as offerRow" +
                "   WHERE ( COALESCE(:offer_ids, null) IS NULL OR offerRow.id IN (:offer_ids)) AND " +
                "( COALESCE(:crypto_codes, null) IS NULL OR offerRow.crypto.code IN (:crypto_codes)) AND "+
                "( COALESCE(:min,null) IS NULL OR :min >= offerRow.askingPrice*offerRow.minQuantity) AND "+
                "( COALESCE(:max,null) IS NULL OR :max <= offerRow.askingPrice*offerRow.maxQuantity) AND "+
                "( COALESCE(:uname, null) IS NULL or offerRow.seller.userAuth.username = :uname) AND "+
                "( COALESCE(:status, null) IS NULL or offerRow.status.code IN (:status)) AND" +
                "( offerRow.id IN ( :paymentMethodAtOffersList) )"+
                ")";

            TypedQuery<Offer> query = entityManager.createQuery(queryDescriptor,Offer.class);
            addParameters(filter,query);
            query.setParameter("paymentMethodAtOffersList", paymentMethodAtOffersList );
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

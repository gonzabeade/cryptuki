package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.OfferDigest;
import ar.edu.itba.paw.OfferFilter;
import ar.edu.itba.paw.OfferOrderCriteria;
import ar.edu.itba.paw.exception.NoSuchOfferException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
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
                .setParameter("min", filter.getMinPrice().isPresent() ? (float)filter.getMinPrice().getAsDouble() : null)
                .setParameter("max", filter.getMaxPrice().isPresent() ? (float)filter.getMaxPrice().getAsDouble() : null)
                .setParameter("uname", filter.getUsername().orElse(null))
                .setParameter("status", filter.getStatus().isEmpty() ? null: filter.getStatus())
                .setParameter("location", filter.getLocation().orElse(null))
                .setParameter("discardedUnames", filter.getDiscardedUsernames().isEmpty() ? null : filter.getDiscardedUsernames());
    }


    private List<Integer> filterByPaymentMethod(OfferFilter filter){
        String paymentCodeQueryDescriptor=" from PaymentMethodAtOffer AS pmao WHERE ( COALESCE(:payment_codes,null) IS NULL OR pmao.paymentCode IN (:payment_codes) ) ";
        TypedQuery<PaymentMethodAtOffer> paymentCodeQuery = entityManager.createQuery(paymentCodeQueryDescriptor,PaymentMethodAtOffer.class);
        paymentCodeQuery.setParameter("payment_codes",filter.getPaymentMethods().isEmpty()?null:filter.getPaymentMethods());
        return paymentCodeQuery.getResultList().stream().map(paymentMethodAtOffer -> paymentMethodAtOffer.getOfferId()).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public int getOfferCount(OfferFilter filter) {
        List<Integer> paymentMethodAtOffersList = filterByPaymentMethod(filter);
        if(paymentMethodAtOffersList.isEmpty() ) //no offers with such payment method
            return 0;
        return getOffersByIdList(filter,paymentMethodAtOffersList).size();
    }

    @Override
    public Collection<Offer> getOffersBy(OfferFilter filter) {
        List<Integer> paymentMethodAtOffersList = filterByPaymentMethod(filter);
        if(paymentMethodAtOffersList.isEmpty() ) //no offers with such payment method
        {
            return new ArrayList<>();
        }

        List<Integer> otherFilterIds =  getOffersByIdList(filter,paymentMethodAtOffersList).stream().map((o)->o.getId()).collect(Collectors.toCollection(ArrayList::new));
        if(otherFilterIds.isEmpty())
            return new ArrayList<>();

        String orderCriterion, modelOrderCriterion;

        switch (OfferOrderCriteria.valueOf(filter.getOrderCriteria().toString()).ordinal()) {
            case 2: case 3:
               orderCriterion = "asking_price";
               modelOrderCriterion = "o.askingPrice";
               break;
//            case 1:
//                orderCriterion = "last_login";
//                modelOrderCriterion = "o.seller.lastLogin";
//                break;
            case 1:
                orderCriterion = "rating";
                modelOrderCriterion = "o.seller.rating";
                break;
            default:
                orderCriterion = "offer_date";
                modelOrderCriterion = "o.date";
        }

        Query pagingQuery = entityManager.createNativeQuery("select tmp.offer_id from (" +
                "  select distinct offer_id, rating , asking_price, offer_date from offer_complete" +
                "  where offer_id in (:ids) order by " + orderCriterion + " " + filter.getOrderDirection().toString()+
                ") as tmp limit :limit offset :offset ");

                pagingQuery.setParameter("limit",filter.getPageSize());
        pagingQuery.setParameter("offset",filter.getPage()*filter.getPageSize());
        pagingQuery.setParameter("ids",otherFilterIds);
        List<Integer> offerPagedIds = (List<Integer>) pagingQuery.getResultList().stream().collect(Collectors.toCollection(ArrayList::new));

        Query query = entityManager.createQuery("from Offer as o where o.id in (:offerPagedIds) order by " +
                modelOrderCriterion + " " + filter.getOrderDirection().toString(), Offer.class);
        query.setParameter("offerPagedIds", offerPagedIds);
        return query.getResultList();
    }

    private Collection<Offer> getOffersByIdList(OfferFilter filter,List<Integer> idsAccepted){
        String queryDescriptor="from Offer as offerRow" +
                "   WHERE ( COALESCE(:offer_ids, null) IS NULL OR offerRow.id IN (:offer_ids)) AND " +
                "( COALESCE(:crypto_codes, null) IS NULL OR offerRow.crypto.code IN (:crypto_codes)) AND "+
                "( COALESCE(:min,null) IS NULL OR :min >= offerRow.askingPrice*offerRow.minQuantity) AND "+
                "( COALESCE(:max,null) IS NULL OR :max <= offerRow.askingPrice*offerRow.maxQuantity) AND "+
                "( COALESCE(:uname, null) IS NULL or offerRow.seller.userAuth.username = :uname) AND "+
                "( COALESCE(:location, null) IS NULL or offerRow.location IN(:location) )AND "+
                "( COALESCE(:status, null) IS NULL or offerRow.status.code IN (:status) ) AND" +
                "( COALESCE(:discardedUnames, null) IS NULL OR offerRow.seller.userAuth.username NOT IN (:discardedUnames)) AND "+
                "( offerRow.id IN ( :idsAccepted)  ) ";

        TypedQuery<Offer> query = entityManager.createQuery(queryDescriptor,Offer.class);
        addParameters(filter,query);
        query.setParameter("idsAccepted", idsAccepted );
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
        Offer offer = getOffersBy(new OfferFilter().byOfferId(digest.getId())).stream().findFirst().orElseThrow(()->new NoSuchOfferException(digest.getId()));
//        offer.clearPaymentMethodAtOffers();
//        for(String pmCode : digest.getPaymentMethods()){
//            PaymentMethodAtOffer pam = new PaymentMethodAtOffer(offer.getId(),pmCode);
//            pam.setOffer(offer);
//            offer.addPaymentMethodAtOffers(pam);
//        }
        offer.setMinQuantity((float) digest.getMinQuantity());
        offer.setMaxQuantity((float) digest.getMaxQuantity());
        offer.setAskingPrice((float) digest.getAskingPrice());
        offer.setComments(digest.getComments());
        offer.setLocation(digest.getLocation());
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
        String queryDescriptor = "from Offer as o where o.id = :offerId";
        TypedQuery<Offer> query = entityManager.createQuery(queryDescriptor,Offer.class);
        query.setParameter("offerId",offerId);
        return Optional.ofNullable(query.getSingleResult().getSeller().getUserAuth().getUsername());
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

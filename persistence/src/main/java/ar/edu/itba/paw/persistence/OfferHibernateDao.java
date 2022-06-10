package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Cryptocurrency;
import ar.edu.itba.paw.model.Offer;
import ar.edu.itba.paw.OfferFilter;
import ar.edu.itba.paw.OfferOrderCriteria;
import ar.edu.itba.paw.model.OfferStatus;
import ar.edu.itba.paw.parameterObject.OfferPO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;


@Repository
public class OfferHibernateDao implements OfferDao{
    @PersistenceContext
    private EntityManager em;


    /* TODO: REVISAR DE ACA HASTA ABAJO  VVVV */

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
//        String paymentCodeQueryDescriptor=" from PaymentMethodAtOffer AS pmao WHERE ( COALESCE(:payment_codes,null) IS NULL OR pmao.paymentCode IN (:payment_codes) ) ";
//        TypedQuery<PaymentMethodAtOffer> paymentCodeQuery = em.createQuery(paymentCodeQueryDescriptor,PaymentMethodAtOffer.class);
//        paymentCodeQuery.setParameter("payment_codes",filter.getPaymentMethods().isEmpty()?null:filter.getPaymentMethods());
//        return paymentCodeQuery.getResultList().stream().map(paymentMethodAtOffer -> paymentMethodAtOffer.getOfferId()).collect(Collectors.toCollection(ArrayList::new));
        return new LinkedList<>();
    }

    @Override
    public int getOfferCount(OfferFilter filter) {
        List<Integer> paymentMethodAtOffersList = filterByPaymentMethod(filter);
        if(paymentMethodAtOffersList.isEmpty() ) //no offers with such payment method
            return 0;
        return getOffersByIdList(filter,paymentMethodAtOffersList).size();
    }

    public Collection<Offer> getOffersBy2(OfferFilter filter) {
        List<Integer> paymentMethodAtOffersList = filterByPaymentMethod(filter);
        if(paymentMethodAtOffersList.isEmpty() ) //no offers with such payment method
        {
            return new ArrayList<>();
        }

        List<Integer> otherFilterIds =  getOffersByIdList(filter,paymentMethodAtOffersList).stream().map((o)->o.getOfferId()).collect(Collectors.toCollection(ArrayList::new));
        if(otherFilterIds.isEmpty())
            return new ArrayList<>();

        String orderCriterion, modelOrderCriterion;

        switch (OfferOrderCriteria.valueOf(filter.getOrderCriteria().toString()).ordinal()) {
            case 2: case 3:
               orderCriterion = "asking_price";
               modelOrderCriterion = "o.unitPrice";
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

        Query pagingQuery = em.createNativeQuery("select tmp.offer_id from (" +
                "  select distinct offer_id, rating , asking_price, offer_date from offer_complete" +
                "  where offer_id in (:ids) order by " + orderCriterion + " " + filter.getOrderDirection().toString()+
                ") as tmp limit :limit offset :offset ");

                pagingQuery.setParameter("limit",filter.getPageSize());
        pagingQuery.setParameter("offset",filter.getPage()*filter.getPageSize());
        pagingQuery.setParameter("ids",otherFilterIds);
        List<Integer> offerPagedIds = (List<Integer>) pagingQuery.getResultList().stream().collect(Collectors.toCollection(ArrayList::new));

        Query query = em.createQuery("from Offer as o where o.id in (:offerPagedIds) order by " +
                modelOrderCriterion + " " + filter.getOrderDirection().toString(), Offer.class);
        query.setParameter("offerPagedIds", offerPagedIds);
        return query.getResultList();
    }

    private Collection<Offer> getOffersByIdList(OfferFilter filter,List<Integer> idsAccepted){
        String queryDescriptor="from Offer as offerRow" +
                "   WHERE ( COALESCE(:offer_ids, null) IS NULL OR offerRow.id IN (:offer_ids)) AND " +
                "( COALESCE(:crypto_codes, null) IS NULL OR offerRow.crypto.code IN (:crypto_codes)) AND "+
                "( COALESCE(:min,null) IS NULL OR :min >= offerRow.unitPrice*offerRow.minQuantity) AND "+
                "( COALESCE(:max,null) IS NULL OR :max <= offerRow.unitPrice*offerRow.maxQuantity) AND "+
                "( COALESCE(:uname, null) IS NULL or offerRow.seller.userAuth.username = :uname) AND "+
                "( COALESCE(:location, null) IS NULL or offerRow.location IN(:location) )AND "+
                "( COALESCE(:status, null) IS NULL or offerRow.status.code IN (:status) ) AND" +
                "( COALESCE(:discardedUnames, null) IS NULL OR offerRow.seller.userAuth.username NOT IN (:discardedUnames)) AND "+
                "( offerRow.id IN ( :idsAccepted)  ) ";

        TypedQuery<Offer> query = em.createQuery(queryDescriptor,Offer.class);
        addParameters(filter,query);
        query.setParameter("idsAccepted", idsAccepted );
        return query.getResultList();
    }

    /* TODO: REVISAR DE ACA PARA ARRIBA ^^^^^^ */

    @Override
    public Collection<Offer> getOffersBy(OfferFilter filter) {
        Offer offer = em.find(Offer.class, 22);
        LinkedList<Offer> offers = new LinkedList<>();
        offers.add(offer);
        return offers;
    }


    @Override
    public Offer makeOffer(OfferPO offer) {
        Cryptocurrency crypto = em.getReference(Cryptocurrency.class, offer.getCryptoCode());
        User seller = em.getReference(User.class, offer.getSellerId());
        Offer newOffer = offer.toBuilder(crypto, seller).build();
        em.persist(newOffer);
        return newOffer;
    }

    @Override
    public Offer modifyOffer(Offer offer) {
        em.persist(offer);
        return offer;
    }

    @Override
    public void deleteOffer(int offerId) {
        changeOfferStatus(offerId, OfferStatus.DEL);
    }

    @Override
    public Optional<Offer> changeOfferStatus(int offerId, OfferStatus offerStatus) {
        Offer offer = em.find(Offer.class, offerId);
        offer.setOfferStatus(offerStatus);
        em.persist(offer);
        return Optional.ofNullable(offer); // TODO: check offer null?
    }


}

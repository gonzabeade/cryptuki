package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Cryptocurrency;
import ar.edu.itba.paw.model.Offer;
import ar.edu.itba.paw.OfferFilter;
import ar.edu.itba.paw.model.OfferStatus;
import ar.edu.itba.paw.parameterObject.OfferPO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;


@Repository
public class OfferHibernateDao implements OfferDao{

    @PersistenceContext
    private EntityManager em;

    private static void testAndSet(Collection<?> collection, Map<String, Object> args, String argName, StringBuilder sqlQueryBuilder, String queryPiece) {
        if (!collection.isEmpty()) {
            sqlQueryBuilder.append(queryPiece);
            args.put(argName, collection);
        }
    }

    // TODO(anyone): check if this function can be modularized
    // TODO: test
    /**
     * Fills a StringBuilder with strings of the form "AND [field] IN [collection] " given an OfferFilter object.
     * Returns all necessary arguments in a map that is to be passed as a parameter
     */
    private static void fillQueryBuilderFilter(OfferFilter filter, Map<String, Object> args, StringBuilder sqlQueryBuilder) {
        testAndSet(filter.getRestrictedToIds(), args, "ids", sqlQueryBuilder, "AND offer_id IN (:ids) ");
        testAndSet(filter.getExcludedUsernames(), args, "resUnames", sqlQueryBuilder, "AND uname NOT IN (:resUnames) ");
        testAndSet(filter.getRestrictedToUsernames(), args, "unames", sqlQueryBuilder, "AND uname IN (:unames) ");
        testAndSet(filter.getRestrictedToUsernames(), args, "pms", sqlQueryBuilder, "AND payment_method IN (:pms) ");
        testAndSet(filter.getCryptoCodes(), args, "cryptoCodes", sqlQueryBuilder, "AND crypto_code IN (:cryptoCodes) ");
        testAndSet(filter.getStatus().stream().map(s -> s.toString()).collect(Collectors.toList()), args, "status", sqlQueryBuilder, "AND status_code IN (:status) ");
        testAndSet(filter.getLocations().stream().map(s -> s.toString()).collect(Collectors.toList()), args, "locations", sqlQueryBuilder, "AND location IN (:locations) ");
    }

    @Override
    public Collection<Offer> getOffersBy(OfferFilter filter) {

        Map<String, Object> map = new HashMap<>();
        StringBuilder sqlQueryBuilder = new StringBuilder();

        // Filter Criteria
        sqlQueryBuilder.append("SELECT offer_id FROM (SELECT DISTINCT offer_id FROM offer_complete WHERE TRUE ");
        fillQueryBuilderFilter(filter, map, sqlQueryBuilder);

        // Paging
        sqlQueryBuilder.append(") as foo LIMIT :limit OFFSET :offset");
        map.put("limit", filter.getPageSize());
        map.put("offset", filter.getPageSize()*filter.getPage());

        // Ordering
        // TODO!!

        Query query = em.createNativeQuery(sqlQueryBuilder.toString());
        for(Map.Entry<String, Object> entry : map.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        Collection<Integer> ids = (Collection<Integer>) query.getResultList();

        TypedQuery<Offer> typedFinalQuery = em.createQuery("from Offer as u where u.id in :ids", Offer.class);
        typedFinalQuery.setParameter("ids", ids);
        return typedFinalQuery.getResultList();
    }

    @Override
    public long getOfferCount(OfferFilter filter) {
        Map<String, Object> map = new HashMap<>();
        StringBuilder sqlQueryBuilder = new StringBuilder();

        sqlQueryBuilder.append("SELECT COUNT(DISTINCT offer_id) FROM offer_complete WHERE TRUE ");
        fillQueryBuilderFilter(filter, map, sqlQueryBuilder);

        Query query = em.createNativeQuery(sqlQueryBuilder.toString());
        for(Map.Entry<String, Object> entry : map.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return ((BigInteger) query.getSingleResult()).longValue();
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

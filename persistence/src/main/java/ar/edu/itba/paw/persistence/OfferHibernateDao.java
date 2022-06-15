package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.parameterObject.OfferPO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;


@Repository
public class OfferHibernateDao implements OfferDao{

    @PersistenceContext
    private EntityManager em;

    private Map<OfferOrderCriteria, String> orderSqlMap = new EnumMap<>(OfferOrderCriteria.class);

    public OfferHibernateDao() {
        orderSqlMap.put(OfferOrderCriteria.DATE, "offer_date DESC ");
        orderSqlMap.put(OfferOrderCriteria.PRICE_LOWER, "asking_price ASC ");
        orderSqlMap.put(OfferOrderCriteria.PRICE_UPPER, "asking_price DESC ");
        orderSqlMap.put(OfferOrderCriteria.LAST_LOGIN, "last_login DESC ");
        orderSqlMap.put(OfferOrderCriteria.RATE, "rating DESC ");
    }

    private static void testAndSet(Collection<?> collection, Map<String, Object> args, String argName, StringBuilder sqlQueryBuilder, String queryPiece) {
        if (!collection.isEmpty()) {
            sqlQueryBuilder.append(queryPiece);
            args.put(argName, collection);
        }
    }

    /**
     * Fills a StringBuilder with strings of the form "AND [field] (NOT) IN [collection] " given an OfferFilter object.
     * Returns all necessary arguments in a map that is to be passed as a parameter
     */
    private void fillQueryBuilderFilter(OfferFilter filter, Map<String, Object> args, StringBuilder sqlQueryBuilder) {
        sqlQueryBuilder.append("WHERE TRUE ");
        testAndSet(filter.getRestrictedToIds(), args, "ids", sqlQueryBuilder, "AND offer_id IN (:ids) ");
        testAndSet(filter.getExcludedUsernames(), args, "resUnames", sqlQueryBuilder, "AND uname NOT IN (:resUnames) ");
        testAndSet(filter.getRestrictedToUsernames(), args, "unames", sqlQueryBuilder, "AND uname IN (:unames) ");
        //testAndSet(filter.getRestrictedToUsernames(), args, "pms", sqlQueryBuilder, "AND payment_method IN (:pms) ");
        testAndSet(filter.getCryptoCodes(), args, "cryptoCodes", sqlQueryBuilder, "AND crypto_code IN (:cryptoCodes) ");
        testAndSet(filter.getStatus().stream().map(s -> s.toString()).collect(Collectors.toList()), args, "status", sqlQueryBuilder, "AND status_code IN (:status) ");
        testAndSet(filter.getLocations().stream().map(s -> s.toString()).collect(Collectors.toList()), args, "locations", sqlQueryBuilder, "AND location IN (:locations) ");
        // Not using it right now -> testAndSet(filter.getRestrictedToUsernames(), args, "pms", sqlQueryBuilder, "AND payment_method IN (:pms) ");
    }

    /**
     * Fills a StringBuilder with native SQL syntax for ordering given an OfferFilter object
     */
    private void fillQueryBuilderOrder(OfferFilter filter, StringBuilder sqlQueryBuilder) {
        sqlQueryBuilder.append("ORDER BY ");
        sqlQueryBuilder.append(orderSqlMap.get(filter.getOrderCriteria()));
    }

    @Override
    public Collection<Offer> getOffersBy(OfferFilter filter) {

        Map<String, Object> map = new HashMap<>();
        StringBuilder sqlQueryBuilder = new StringBuilder();

        // Filtering
        sqlQueryBuilder.append("SELECT * FROM offer JOIN users ON offer.seller_id = users.id JOIN auth ON users.id = auth.user_id ");
        fillQueryBuilderFilter(filter, map, sqlQueryBuilder);

        // Ordering
        fillQueryBuilderOrder(filter, sqlQueryBuilder);

        // Paging
        sqlQueryBuilder.append("LIMIT :limit OFFSET :offset");
        map.put("limit", filter.getPageSize());
        map.put("offset", filter.getPageSize()*filter.getPage());

        Query query = em.createNativeQuery(sqlQueryBuilder.toString(), Offer.class);
        for(Map.Entry<String, Object> entry : map.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        return (Collection<Offer>) query.getResultList();
    }

    @Override
    public long getOfferCount(OfferFilter filter) {
        Map<String, Object> map = new HashMap<>();
        StringBuilder sqlQueryBuilder = new StringBuilder();

        sqlQueryBuilder.append("SELECT COUNT(offer_id) FROM offer JOIN users ON offer.seller_id = users.id JOIN auth ON users.id = auth.user_id ");
        fillQueryBuilderFilter(filter, map, sqlQueryBuilder);

        Query query = em.createNativeQuery(sqlQueryBuilder.toString());
        for(Map.Entry<String, Object> entry : map.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return ((BigInteger) query.getSingleResult()).longValue(); // never returns null
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
        if (offer == null) {
            offer.setOfferStatus(offerStatus);
            em.persist(offer);
            return Optional.of(offer);
        } else
            return Optional.empty();
    }


}

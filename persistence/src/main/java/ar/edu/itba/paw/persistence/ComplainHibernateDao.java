package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.Trade;
import ar.edu.itba.paw.model.parameterObject.ComplainPO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.*;

@Repository
public class ComplainHibernateDao implements ComplainDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Complain makeComplain(ComplainPO complain) {
        Trade trade = em.getReference(Trade.class, complain.getTradeId());
        TypedQuery<User> tq = em.createQuery("from User AS u WHERE u.userAuth.username = :username", User.class);
        tq.setParameter("username", complain.getComplainerUsername());
        User complainer = tq.getSingleResult();
        Complain newComplain = complain.toBuilder(trade, complainer, null).build();
        em.persist(newComplain);
        return newComplain;
    }

    @Override
    public Complain modifyComplain(Complain complain) {
        em.persist(complain);
        return complain;
    }

    @Override
    public Optional<Complain> closeComplain(int complainId, String moderator, String moderatorComments) {
        Complain complain = em.find(Complain.class, complainId);
        if (complain == null)
            return Optional.empty();
        TypedQuery<User> tq = em.createQuery("from User AS u WHERE u.userAuth.username = :username", User.class);
        tq.setParameter("username", moderator);
        User moderatorUser = tq.getSingleResult();
        complain.setModerator(moderatorUser);
        complain.setModeratorComments(moderatorComments);
        complain.setStatus(ComplainStatus.CLOSED);
        em.persist(complain);
        return Optional.of(complain);
    }

    @Override
    public Collection<Complain> getComplainsBy(ComplainFilter filter) {
        Map<String, Object> map = new HashMap<>();
        StringBuilder sqlQueryBuilder = new StringBuilder();

        sqlQueryBuilder.append("SELECT complain_id FROM complain_complete ");

        // Filtering
        fillQueryBuilderFilter(filter, map, sqlQueryBuilder);

        // Paging
        sqlQueryBuilder.append("LIMIT :limit OFFSET :offset");
        map.put("limit", filter.getPageSize());
        map.put("offset", filter.getPageSize()*filter.getPage());

        Query query = em.createNativeQuery(sqlQueryBuilder.toString());
        for(Map.Entry<String, Object> entry : map.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        List<Integer> ids = query.getResultList();
        if (ids.isEmpty())
            return Collections.emptyList();

        TypedQuery<Complain> tq = em.createQuery("from Complain c where c.complainId in :ids order by c.date asc", Complain.class);
        tq.setParameter("ids", ids);
        return tq.getResultList();
    }

    @Override
    public long getComplainCount(ComplainFilter filter) {
        Map<String, Object> map = new HashMap<>();
        StringBuilder sqlQueryBuilder = new StringBuilder();

        sqlQueryBuilder.append("SELECT COUNT(complain_id) FROM complain_complete ");
        fillQueryBuilderFilter(filter, map, sqlQueryBuilder);

        Query query = em.createNativeQuery(sqlQueryBuilder.toString());
        for(Map.Entry<String, Object> entry : map.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return ((Number) query.getSingleResult()).longValue(); // never returns null
    }

    private static void testAndSet(Collection<?> collection, Map<String, Object> args, String argName, StringBuilder sqlQueryBuilder, String queryPiece) {
        if (!collection.isEmpty()) {
            sqlQueryBuilder.append(queryPiece);
            args.put(argName, collection);
        }
    }

    private void fillQueryBuilderFilter(ComplainFilter filter, Map<String, Object> args, StringBuilder sqlQueryBuilder) {
        sqlQueryBuilder.append("WHERE TRUE ");

        sqlQueryBuilder.append("AND status IN (:status)");
        args.put("status", filter.getComplainStatus().toString());

        testAndSet(filter.getRestrictedToComplainerUsernames(), args, "complainerUnames", sqlQueryBuilder, "AND complainer_uname IN (:complainerUnames) ");
        testAndSet(filter.getRestrictedToModeratorUsernames(), args, "moderatorUnames", sqlQueryBuilder, "AND moderator_uname IN (:moderatorUnames) ");
        testAndSet(filter.getRestrictedToTradeIds(), args, "tradeIds", sqlQueryBuilder, "AND trade_id IN (:tradeIds) ");
        testAndSet(filter.getRestrictedToOfferIds(), args, "offerIds", sqlQueryBuilder, "AND offer_id IN (:offerIds) ");
        testAndSet(filter.getRestrictedToComplainIds(), args, "complainIds", sqlQueryBuilder, "AND complain_id IN (:complainIds) ");

    }


}

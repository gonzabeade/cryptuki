package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.exception.NoSuchOfferException;
import ar.edu.itba.paw.exception.NoSuchTradeException;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ar.edu.itba.paw.model.Trade;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class TradeHibernateDao implements TradeDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Trade makeTrade(int offerId, int buyerId, double quantity) {
        Offer offer = em.getReference(Offer.class, offerId);
        if (offer == null)
            throw new NoSuchOfferException(offerId);
        User buyer = em.getReference(User.class, buyerId);
        if (buyer == null)
            throw new NoSuchUserException(buyerId);
        Trade newTrade = new Trade(offer, buyer, quantity);
        em.persist(newTrade);
        return newTrade;
    }

    @Override
    public Trade changeTradeStatus(int tradeId, TradeStatus status) {
        Trade trade = em.find(Trade.class, tradeId);
        if (trade == null)
            throw new NoSuchTradeException(tradeId);
        trade.setStatus(status);
        trade.setLastModified(LocalDateTime.now());
        em.merge(trade);
        return trade;
    }

    @Override
    public Trade modifyTrade(Trade trade) {
        trade.setLastModified(LocalDateTime.now());
        em.merge(trade);
        return trade;
    }

    @Override
    public void deleteTrade(int tradeId) {
        changeTradeStatus(tradeId, TradeStatus.DELETED);
    }

    @Override
    public Optional<Trade> getTradeById(int tradeId) {
        return Optional.ofNullable(em.find(Trade.class, tradeId));
    }

    @Override
    public Collection<Trade> getTradesAsSeller(String username, int page, int pageSize, Set<TradeStatus> status, int offerId) {

        Query nativeQuery = em.createNativeQuery("SELECT trade_id FROM trade_complete WHERE status IN (:status) AND seller_uname = :uname ORDER BY start_date LIMIT :limit OFFSET :offset");
        return getTrades(username, page, pageSize, status, nativeQuery);
    }

    @Override
    public long getTradesAsSellerCount(String username, Set<TradeStatus> status, int offerId) {
        Query query = em.createQuery("select count(*) from Trade t where status in (:status) and t.offer.seller.userAuth.username = :username and t.offer.offerId = :offerId");
        query.setParameter("status", status);
        query.setParameter("offerId", offerId);
        query.setParameter("username", username);
        return (Long)query.getSingleResult();
    }

    @Override
    public Collection<Trade> getMostRecentTradesAsSeller(String username, int quantity) {

        Query nativeQuery = em.createNativeQuery("SELECT trade_id FROM trade_complete WHERE seller_uname = :uname ORDER BY start_date LIMIT :limit OFFSET 0");
        nativeQuery.setParameter("uname", username);
        nativeQuery.setParameter("limit", quantity);

        List<Integer> ids = nativeQuery.getResultList();
        if (ids.isEmpty())
            return Collections.emptyList();

        TypedQuery<Trade> typedQuery = em.createQuery("from Trade t where t.tradeId in (:ids) ORDER BY t.lastModified DESC", Trade.class);
        typedQuery.setParameter("ids", ids);
        return typedQuery.getResultList();
    }

    @Override
    public Collection<Trade> getTradesAsBuyer(String username, int page, int pageSize, Set<TradeStatus> status) {
        Query nativeQuery = em.createNativeQuery("SELECT trade_id FROM trade_complete WHERE status IN (:status) AND buyer_uname = :uname ORDER BY start_date LIMIT :limit OFFSET :offset");
        return getTrades(username, page, pageSize, status, nativeQuery);
    }

    private Collection<Trade> getTrades(String username, int page, int pageSize, Set<TradeStatus> status, Query nativeQuery) {
        nativeQuery.setParameter("status", status.stream().map(s->s.toString()).collect(Collectors.toList()));
        nativeQuery.setParameter("uname", username);
        nativeQuery.setParameter("limit", pageSize);
        nativeQuery.setParameter("offset", page*pageSize);

        List<Integer> ids = (List<Integer>) nativeQuery.getResultList();
        if (ids.isEmpty())
            return Collections.emptyList();

        TypedQuery<Trade> typedQuery = em.createQuery("from Trade t where t.tradeId in (:ids) order by t.lastModified DESC", Trade.class);
        typedQuery.setParameter("ids", ids);
        return typedQuery.getResultList();
    }

    @Override
    public long getTradesAsBuyerCount(String username, Set<TradeStatus> status) {
        Query query = em.createQuery("select count(*) from Trade t where status in :status and t.buyer.userAuth.username = :username");
        query.setParameter("status", status);
        query.setParameter("username", username);
        return (Long)query.getSingleResult();
    }
}

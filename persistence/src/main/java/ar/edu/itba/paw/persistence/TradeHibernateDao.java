package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.exception.NoSuchOfferException;
import ar.edu.itba.paw.exception.NoSuchTradeException;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ar.edu.itba.paw.model.Trade;
import javax.persistence.*;
import java.util.Collection;
import java.util.Optional;

@Repository
public class TradeHibernateDao implements TradeDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Trade makeTrade(int offerId, int buyerId, float quantity) {
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
        em.persist(trade);
        return trade;
    }

    @Override
    public Trade modifyTrade(Trade trade) {
        em.persist(trade);
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
    public Collection<Trade> getTradesAsSeller(String username, int page, int pageSize, TradeStatus status) {
        TypedQuery<Trade> typedQuery = em.createQuery("from Trade t where status = :status and t.offer.seller.userAuth.username = :username", Trade.class);
        typedQuery.setFirstResult(page*pageSize);
        typedQuery.setMaxResults(pageSize);
        typedQuery.setParameter("status", status);
        typedQuery.setParameter("username", username);
        return typedQuery.getResultList();
    }

    @Override
    public long getTradesAsSellerCount(String username, TradeStatus status) {
        Query query = em.createQuery("select count(*) from Trade t where status = :status and t.offer.seller.userAuth.username = :username");
        query.setParameter("status", status);
        query.setParameter("username", username);
        return (Long)query.getSingleResult();
    }

    @Override
    public Collection<Trade> getTradesAsBuyer(String username, int page, int pageSize, TradeStatus status) {
        TypedQuery<Trade> typedQuery = em.createQuery("from Trade t where status = :status and t.buyer.userAuth.username = :username", Trade.class);
        typedQuery.setFirstResult(page*pageSize);
        typedQuery.setMaxResults(pageSize);
        typedQuery.setParameter("status", status);
        typedQuery.setParameter("username", username);
        return typedQuery.getResultList();
    }

    @Override
    public long getTradesAsBuyerCount(String username, TradeStatus status) {
        Query query = em.createQuery("select count(*) from Trade t where status = :status and t.buyer.userAuth.username = :username");
        query.setParameter("status", status);
        query.setParameter("username", username);
        return (Long)query.getSingleResult();
    }

    @Override
    public long getTotalTradesCount(String username, TradeStatus status) {
        Query query = em.createQuery("select count(*) from Trade t where t.offer.seller.userAuth.username = :username");
        query.setParameter("username", username);
        return (Long)query.getSingleResult();    }

}

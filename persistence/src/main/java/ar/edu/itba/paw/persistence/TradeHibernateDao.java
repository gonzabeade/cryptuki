package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.exception.NoSuchTradeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.Optional;

@Repository
public class TradeHibernateDao implements TradeDao{
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserAuthDao userAuthDao;
    @Override
    public int makeTrade(int offerId, String buyerUsername, float quantity, TradeStatus status) {
        Trade.Builder builder = new Trade.Builder(offerId,buyerUsername);
        builder.withQuantity(quantity)
                .withTradeStatus(status);
        return makeTrade(builder);
    }

    @Override
    public int makeTrade(Trade.Builder builder) {
        int buyerId =userAuthDao.getUserAuthByUsername(builder.getBuyerUsername()).get().getId();
        builder.withBuyerId(buyerId);
        entityManager.persist(builder);
        return builder.getTradeId();
    }

    @Override
    public void updateStatus(int tradeId, TradeStatus status) {
        Trade trade = getTradeById(tradeId).orElseThrow(()-> new NoSuchTradeException(tradeId));
        trade.setStatus(status);
        entityManager.persist(trade);
    }

    @Override
    public Optional<Trade> getTradeById(int tradeId) {
        TypedQuery<Trade> query = entityManager.createQuery("from Trade as t where t.tradeId = :tradeId",Trade.class);
        query.setParameter("tradeId",tradeId);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public Collection<Trade> getSellingTradesByUsername(String username, int page, int pageSize) {
        TypedQuery<Trade> query  = entityManager.createQuery("from Trade as t where t.offer.seller.userAuth.username = :username",Trade.class);
        query.setParameter("username",username);
        query.setMaxResults(pageSize);
        query.setFirstResult(page*pageSize);
        return query.getResultList();
    }

    @Override
    public int getSellingTradesByUsernameCount(String username) {
        TypedQuery<Trade> query = entityManager.createQuery("from Trade as t where t.offer.seller.userAuth.username = :username",Trade.class);
        query.setParameter("username",username);
        return query.getResultList().size();
    }

    @Override
    public Collection<Trade> getBuyingTradesByUsername(String username, int page, int pageSize) {
        TypedQuery<Trade> query = entityManager.createQuery("from Trade as t where t.user.userAuth.username = :username",Trade.class);
        query.setParameter("username",username);
        query.setMaxResults(pageSize);
        query.setFirstResult(page*pageSize);
        return query.getResultList();
    }

    @Override
    public int getBuyingTradesByUsername(String username) {
        TypedQuery<Trade> query = entityManager.createQuery("from Trade as t where t.user.userAuth.username = :username",Trade.class);
        query.setParameter("username",username);
        return query.getResultList().size();
    }

    @Override
    public Collection<Trade> getSellingTradesByUsername(String username, int page, int pageSize, TradeStatus status) {
        TypedQuery<Trade> query  = entityManager.createQuery("from Trade as t where t.offer.seller.userAuth.username = :username and t.status= :status",Trade.class);
        query.setParameter("username",username);
        query.setParameter("status",status);
        query.setMaxResults(pageSize);
        query.setFirstResult(page*pageSize);
        return query.getResultList();
    }

    @Override
    public int getSellingTradesByUsernameCount(String username, TradeStatus status) {
        TypedQuery<Trade> query = entityManager.createQuery("from Trade as t where t.offer.seller.userAuth.username = :username and t.status= :status",Trade.class);
        query.setParameter("username",username);
        query.setParameter("status",status);
        return query.getResultList().size();
    }

    @Override
    public Collection<Trade> getBuyingTradesByUsername(String username, int page, int pageSize, TradeStatus status) {
        TypedQuery<Trade> query = entityManager.createQuery("from Trade as t where t.user.userAuth.username = :username and t.status= :status",Trade.class);
        query.setParameter("username",username);
        query.setParameter("status",status);
        return query.getResultList();
    }

    @Override
    public int getBuyingTradesByUsername(String username, TradeStatus status) {
        TypedQuery<Trade> query = entityManager.createQuery("from Trade as t where t.user.userAuth.username = :username and t.status= :status",Trade.class);
        query.setParameter("username",username);
        query.setParameter("status",status);
        return query.getResultList().size();
    }

    @Override
    public Collection<Trade> getTradesByUsername(String username, int page, int pageSize) {
        TypedQuery<Trade> query = entityManager.createQuery("from Trade as t where (t.user.userAuth.username = :username or t.offer.seller.userAuth.username=:username)",Trade.class);
        query.setParameter("username",username);
        query.setMaxResults(pageSize);
        query.setFirstResult(page*pageSize);
        return query.getResultList();
    }

    @Override
    public int getTradesByUsernameCount(String username) {
        TypedQuery<Trade> query = entityManager.createQuery("from Trade as t where (t.user.userAuth.username = :username or t.offer.seller.userAuth.username=:username)",Trade.class);
        query.setParameter("username",username);
        return query.getResultList().size();
    }

    @Override
    public void rateSeller(int tradeId) {
        Trade trade = getTradeById(tradeId).orElseThrow(()-> new NoSuchTradeException(tradeId));
        trade.setRatedSeller(true);
        entityManager.persist(trade);
    }

    @Override
    public void rateBuyer(int tradeId) {
        Trade trade = getTradeById(tradeId).orElseThrow(()-> new NoSuchTradeException(tradeId));
        trade.setRatedBuyer(true);
        entityManager.persist(trade);
    }


}

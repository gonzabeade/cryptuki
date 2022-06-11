package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.exception.NoSuchTradeException;
import ar.edu.itba.paw.model.Message;
import ar.edu.itba.paw.model.TradeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
        int buyerId = userAuthDao.getUserAuthByUsername(builder.getBuyerUsername()).get().getUserId();
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
        TypedQuery<Trade> query = entityManager.createQuery("from Trade as t where t.tradeId = :tradeId", Trade.class);
        query.setParameter("tradeId",tradeId);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException nre) {
            return Optional.empty();
        }
    }

    @Override
    public Collection<Trade> getSellingTradesByUsername(String username, int page, int pageSize) {
        return getTradeCollection(username,page,pageSize,"from Trade as t where t.offer.seller.userAuth.username = :username order by t.startDate desc");
    }

    @Override
    public int getSellingTradesByUsernameCount(String username) {
      return getTradeCollection(username,"from Trade as t where t.offer.seller.userAuth.username = :username").size();
    }

    @Override
    public Collection<Trade> getBuyingTradesByUsername(String username, int page, int pageSize) {
        return getTradeCollection(username,page,pageSize,"from Trade as t where t.user.userAuth.username = :username order by t.startDate desc");
    }

    @Override
    public int getBuyingTradesByUsername(String username) {
        return getTradeCollection(username,"from Trade as t where t.user.userAuth.username = :username").size();
    }

    @Override
    public Collection<Trade> getSellingTradesByUsername(String username, int page, int pageSize, TradeStatus status) {
        return getTradeCollection(username,page,pageSize,status,"from Trade as t where t.offer.seller.userAuth.username = :username and t.status= :status order by t.startDate desc");
    }

    @Override
    public int getSellingTradesByUsernameCount(String username, TradeStatus status) {
        return getTradeCollection(username,status,"from Trade as t where t.offer.seller.userAuth.username = :username and t.status= :status").size();
    }

    @Override
    public Collection<Trade> getBuyingTradesByUsername(String username, int page, int pageSize, TradeStatus status) {
        return getTradeCollection(username,page,pageSize,status,"from Trade as t where t.user.userAuth.username = :username and t.status= :status order by t.startDate desc");
    }

    @Override
    public int getBuyingTradesByUsername(String username, TradeStatus status) {
        return getTradeCollection(username,status,"from Trade as t where t.user.userAuth.username = :username and t.status= :status").size();
    }

    @Override
    public Collection<Trade> getTradesByUsername(String username, int page, int pageSize) {
        return getTradeCollection(username,page,pageSize,"from Trade as t where (t.user.userAuth.username = :username or t.offer.seller.userAuth.username=:username) order by t.startDate desc");
    }

    @Override
    public int getTradesByUsernameCount(String username) {
        return getTradeCollection(username,"from Trade as t where (t.user.userAuth.username = :username or t.offer.seller.userAuth.username=:username)").size();
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

    @Override
    public void deleteTrade(int tradeId) {
        Trade trade = getTradeById(tradeId).orElseThrow(()->new NoSuchTradeException(tradeId));
        Collection<Message> messages = trade.getMessageCollection();
        for(Message m : messages)
            entityManager.remove(m);
        entityManager.remove(trade);
    }

    @Override
    public void setBuyerUnseenMessageCount(int tradeId, int value) {
        Trade trade = getTradeById(tradeId).orElseThrow(()-> new NoSuchTradeException(tradeId));
        trade.setqUnseenMessagesBuyer(value);
        entityManager.persist(trade);
    }

    @Override
    public void setSellerUnseenMessageCount(int tradeId, int value) {
        Trade trade = getTradeById(tradeId).orElseThrow(()-> new NoSuchTradeException(tradeId));
        trade.setqUnseenMessagesSeller(value);
        entityManager.persist(trade);
    }


    private Collection<Trade> getTradeCollection(String username, int page, int pageSize,TradeStatus status,String query){
        TypedQuery<Trade> typedQuery = entityManager.createQuery(query,Trade.class);
        typedQuery.setParameter("username",username);
        typedQuery.setParameter("status",status);
        typedQuery.setMaxResults(pageSize);
        typedQuery.setFirstResult(page*pageSize);
        return typedQuery.getResultList();
    }
    private Collection<Trade> getTradeCollection(String username, TradeStatus status,String query){
        TypedQuery<Trade> typedQuery = entityManager.createQuery(query,Trade.class);
        typedQuery.setParameter("username",username);
        typedQuery.setParameter("status",status);
        return typedQuery.getResultList();
    }

    private Collection<Trade> getTradeCollection(String username, String query){
        TypedQuery<Trade> typedQuery = entityManager.createQuery(query,Trade.class);
        typedQuery.setParameter("username",username);
        return typedQuery.getResultList();
    }

    private Collection<Trade> getTradeCollection(String username, int page, int pageSize,String query){
        TypedQuery<Trade> typedQuery = entityManager.createQuery(query,Trade.class);
        typedQuery.setParameter("username",username);
        typedQuery.setMaxResults(pageSize);
        typedQuery.setFirstResult(page*pageSize);
        return typedQuery.getResultList();
    }



}

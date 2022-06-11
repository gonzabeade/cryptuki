package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.ComplainFilter;
import ar.edu.itba.paw.model.ComplainStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;

@Repository
public class ComplainHibernateDao implements ComplainDao{

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserAuthDao userAuthDao;


    private static void addParameters(TypedQuery<Complain> query,ComplainFilter filter) {
        query
                .setParameter("complainer_uname", filter.getComplainerUsername().orElse(null))
                .setParameter("from_date", filter.getFrom().isPresent()? filter.getFrom().get().atStartOfDay(): null)
                .setParameter("to_date", filter.getTo().isPresent()? filter.getTo().get().atStartOfDay(): null)
                .setParameter("complain_status", filter.getComplainStatus().isPresent() ? filter.getComplainStatus().get() : null)
                .setParameter("moderator_uname", filter.getModeratorUsername().orElse(null))
                .setParameter("offer_id", filter.getOfferId().isPresent() ? filter.getOfferId().getAsInt() : null)
                .setParameter("trade_id", filter.getTradeId().isPresent() ? filter.getTradeId().getAsInt() : null)
                .setParameter("complain_id", filter.getComplainId().isPresent() ? filter.getComplainId().getAsInt() : null);
    }

    //TODO SALVA: mirar que hacer con el filtrado por moderator
    @Override
    public Collection<Complain> getComplainsBy(ComplainFilter filter) {
        String moderatorUname = (filter.getModeratorUsername().orElse(null) == null) ? "'a'" : "c.moderator.userAuth.username";
        String filterQuery="from Complain as c where" +
                "((COALESCE(:trade_id, null) IS NULL OR c.trade.tradeId = :trade_id) AND" +
                "(COALESCE(:complain_status, null) IS NULL OR c.status = :complain_status) AND"+
                "(COALESCE(:moderator_uname, null) IS NULL OR (" + moderatorUname + " = :moderator_uname)) AND" +
                "(COALESCE(:complainer_uname, null) IS NULL OR c.complainer.userAuth.username = :complainer_uname) AND" +
                "(COALESCE(:from_date, null) IS NULL OR  c.date >= :from_date) AND" +
                "(COALESCE(:to_date, null) IS NULL OR  c.date <= :to_date) AND" +
                "(COALESCE(:offer_id, null) IS NULL OR  c.trade.offer.id = :offer_id) AND" +
                "(COALESCE(:complain_id, null) IS NULL OR c.complainId = :complain_id))" ;

        TypedQuery<Complain> query = entityManager.createQuery(filterQuery,Complain.class);
        query.setMaxResults(filter.getPageSize());
        query.setFirstResult(filter.getPage()*filter.getPageSize());
        addParameters(query,filter);
        return query.getResultList();
    }

    @Override
    public int countComplainsBy(ComplainFilter filter) {
        return getComplainsBy(filter).size();
    }

    @Override
    public void makeComplain(Complain.Builder complain) {
        complain.withComplainerId(userAuthDao.getUserAuthByUsername(complain.getComplainer()).get().getUserId());
        entityManager.persist(complain);
    }

    @Override
    public void updateComplainStatus(int complainId, ComplainStatus complainStatus) {
        Complain complain = getComplainById(complainId);
        complain.setStatus(complainStatus);
        entityManager.persist(complain);
    }

    //TODO SALVA:mirar que hacer con ese null
    @Override
    public void updateModerator(int complainId, String username) {
        Complain complain = getComplainById(complainId);
        if(username == null)
            complain.setModerator(null);
        else complain.setModerator(userAuthDao.getUserAuthByUsername(username).get().getUser());
        entityManager.persist(complain);
    }

    @Override
    public void updateModeratorComment(int complainId, String comments) {
        Complain complain = getComplainById(complainId);
        complain.setModeratorComments(comments);
        entityManager.persist(complain);
    }

    private Complain getComplainById(int complainId){
        TypedQuery<Complain> query = entityManager.createQuery("from Complain as c where c.complainId = :complainId",Complain.class);
        query.setParameter("complainId",complainId);
        return query.getSingleResult();
    }

}

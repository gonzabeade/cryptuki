package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.ComplainFilter;
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

    @Override
    public Collection<Complain> getComplainsBy(ComplainFilter filter) {
        TypedQuery<Complain> query = entityManager.createQuery("from Complain",Complain.class);
        return query.getResultList();
    }

    @Override
    public int countComplainsBy(ComplainFilter filter) {
        return getComplainsBy(filter).size();
    }

    @Override
    public void makeComplain(Complain.Builder complain) {
        complain.withComplainerId(userAuthDao.getUserAuthByUsername(complain.getComplainer()).get().getId());
        entityManager.persist(complain);
    }

    @Override
    public void updateComplainStatus(int complainId, ComplainStatus complainStatus) {
        Complain complain = getComplainById(complainId);
        complain.setStatus(complainStatus);
        entityManager.persist(complain);
    }

    @Override
    public void updateModerator(int complainId, String username) {
        Complain complain = getComplainById(complainId);
        complain.setModerator(userAuthDao.getUserAuthByUsername(username).get().getUser());
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

package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.ComplainFilter;
import ar.edu.itba.paw.model.Complain;
import ar.edu.itba.paw.model.Cryptocurrency;
import ar.edu.itba.paw.parameterObject.ComplainPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Collections;

@Repository
public class ComplainHibernateDao implements ComplainDao{

    @PersistenceContext
    private EntityManager em;

    private final UserAuthDao userAuthDao;

    @Autowired
    public ComplainHibernateDao(UserAuthDao userAuthDao) {
        this.userAuthDao = userAuthDao;
    }

    @Override
    public Complain makeComplain(ComplainPO complain) {
        Trade trade = em.getReference(Trade.class, complain.getTradeId());
        User complainer = em.getReference(User.class, complain.getComplainerUsername());
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
    public Collection<Complain> getComplainsBy(ComplainFilter filter) {
        return Collections.emptyList();
    }

    @Override
    public long getComplainCount(ComplainFilter filter) {
        return 0;
    }


}

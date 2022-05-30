package ar.edu.itba.paw.persistence;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class OfferStatusHibernateDao implements OfferStatusDao{
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Optional<OfferStatus> getOfferStatusByCode(String code) {
        TypedQuery<OfferStatus> query = entityManager.createQuery("from OfferStatus as os where os.code=:code",OfferStatus.class);
        query.setParameter("code",code);
        return Optional.ofNullable(query.getSingleResult());
    }
}

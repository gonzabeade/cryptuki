package ar.edu.itba.paw.persistence;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;

@Repository
public class LocationHibernateDao implements LocationDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Collection<Country> getAllCountries() {
        final TypedQuery<Country> query = entityManager.createQuery("from Country as c ORDER BY c.name ASC", Country.class);
        return query.getResultList();
    }
}

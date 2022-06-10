package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Cryptocurrency;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class CryptocurrencyHibernateDao implements  CryptocurrencyDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Cryptocurrency> getCryptocurrency(String code) {
        TypedQuery<Cryptocurrency> query = entityManager.createQuery("from Cryptocurrency as c where c.code = :code",Cryptocurrency.class);
        query.setParameter("code",code);
        List<Cryptocurrency> cryptocurrencies = query.getResultList();
        return cryptocurrencies.isEmpty() ? Optional.empty() : Optional.of(cryptocurrencies.get(0));
    }

    @Override
    public Collection<Cryptocurrency> getAllCryptocurrencies() {
        final TypedQuery<Cryptocurrency> query = entityManager.createQuery("from Cryptocurrency as c",Cryptocurrency.class);
        return query.getResultList();
    }
}

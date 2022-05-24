package ar.edu.itba.paw.persistence;

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
        return Optional.ofNullable(entityManager.find(Cryptocurrency.class,code));
    }

    @Override
    public Collection<Cryptocurrency> getAllCryptocurrencies() {
        final TypedQuery<Cryptocurrency> query = entityManager.createQuery("from Cryptocurrency as c",Cryptocurrency.class);
        List<Cryptocurrency> cryptocurrencies = query.getResultList();
        return cryptocurrencies;

    }
}

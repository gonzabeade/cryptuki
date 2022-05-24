package ar.edu.itba.paw.persistence;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.Optional;

@Repository
public class PaymentMethodHibernateDao implements PaymentMethodDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Collection<PaymentMethod> getAllPaymentMethods() {
        final TypedQuery<PaymentMethod> query = entityManager.createQuery("from PaymentMethod as pm",PaymentMethod.class);
        return query.getResultList();
    }

    @Override
    public Optional<PaymentMethod> getPaymentMethodByCode(String code) {
        return Optional.ofNullable(entityManager.find(PaymentMethod.class,code));
    }
}

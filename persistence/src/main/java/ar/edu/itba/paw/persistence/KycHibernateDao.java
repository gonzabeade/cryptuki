package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.KycStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class KycHibernateDao implements KycDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void newKycRequest(KycInformation.KycInformationBuilder builder) {
        entityManager.persist(builder);
    }

    @Override
    public void setKycRequestStatus(KycStatus status, String username) {
        KycInformation kyc = entityManager.find(KycInformation.class, entityManager);
        kyc.setStatus(status);
        entityManager.persist(kyc);
    }

    @Override
    public Optional<KycInformation> getKycRequest(String username) {
        return Optional.ofNullable(entityManager.find(KycInformation.class, entityManager));
    }
}

package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.KycInformation;
import ar.edu.itba.paw.model.KycStatus;
import ar.edu.itba.paw.exception.NoSuchKycException;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.parameterObject.KycInformationPO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;

@Repository
public class KycHibernateDao implements KycDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void newKycRequest(KycInformationPO kycInformationPO) {
        TypedQuery<User> tq = entityManager.createQuery("from User AS user WHERE user.userAuth.username = :username", User.class);
        tq.setParameter("username", kycInformationPO.getUsername());
        User user = tq.getSingleResult();
        KycInformation kycInformation = new KycInformation(kycInformationPO, user);
        entityManager.persist(kycInformation);
    }

    @Override
    public void setKycRequestStatus(KycStatus status, int kycId) {
        KycInformation kyc = entityManager.find(KycInformation.class, kycId);
        if ( kyc == null )
            throw new NoSuchKycException(kycId);
        kyc.setStatus(status);
        entityManager.persist(kyc);
    }

    @Override
    public Collection<KycInformation> getKycRequestsByStatus(String username, KycStatus status) {
        TypedQuery<KycInformation> tq = entityManager.createQuery("from KycInformation AS kyc WHERE kyc.user.userAuth.username = :username AND kyc.status = :status", KycInformation.class);
        tq.setParameter("username", username);
        tq.setParameter("status", status);
        return tq.getResultList();
    }

    @Override
    public Collection<KycInformation> getKycRequestsByStatus(KycStatus status, int page, int pageSize) {
        TypedQuery<KycInformation> tq = entityManager.createQuery("from KycInformation AS kyc WHERE kyc.status = :status ORDER BY kyc.kycDate ASC", KycInformation.class);
        tq.setParameter("status", status);
        tq.setFirstResult(page*pageSize);
        tq.setMaxResults(pageSize);
        return tq.getResultList();
    }

    @Override
    public long countKycRequestsByStatus(KycStatus status) {
        TypedQuery<Long> tq = entityManager.createQuery("select count(kyc) from KycInformation AS kyc WHERE kyc.status = :status", Long.class);
        tq.setParameter("status", status);
        return tq.getSingleResult();
    }
}

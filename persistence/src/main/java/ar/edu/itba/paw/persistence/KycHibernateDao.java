package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Complain;
import ar.edu.itba.paw.model.KycInformation;
import ar.edu.itba.paw.model.KycStatus;
import ar.edu.itba.paw.exception.NoSuchKycException;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.parameterObject.KycInformationPO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;

@Repository
public class KycHibernateDao implements KycDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void newKycRequest(KycInformationPO kycInformationPO) {
        TypedQuery<User> tq = em.createQuery("from User AS user WHERE user.userAuth.username = :username", User.class);
        tq.setParameter("username", kycInformationPO.getUsername());
        User user = tq.getSingleResult();
        KycInformation kycInformation = new KycInformation(kycInformationPO, user);
        em.persist(kycInformation);
    }

    @Override
    public void setKycRequestStatus(KycStatus status, int kycId) {
        KycInformation kyc = em.find(KycInformation.class, kycId);
        if ( kyc == null )
            throw new NoSuchKycException(kycId);
        kyc.setStatus(status);
        em.persist(kyc);
    }

    @Override
    public Collection<KycInformation> getKycRequestsByStatus(String username, KycStatus status) {
        TypedQuery<KycInformation> tq = em.createQuery("from KycInformation AS kyc WHERE kyc.user.userAuth.username = :username AND kyc.status = :status", KycInformation.class);
        tq.setParameter("username", username);
        tq.setParameter("status", status);
        return tq.getResultList();
    }

    @Override
    public Collection<KycInformation> getKycRequestsByStatus(KycStatus status, int page, int pageSize) {

        String nativeQuery = "SELECT kyc_id FROM kyc WHERE kyc.status = :status ORDER BY kyc_date ASC LIMIT :limit OFFSET :offset";
        Query query = em.createNativeQuery(nativeQuery);
        query.setParameter("status", status.toString());
        query.setParameter("limit", pageSize);
        query.setParameter("offset", pageSize*page);
        List<Integer> ids = (List<Integer>) query.getResultList();

        TypedQuery<KycInformation> tq = em.createQuery("from KycInformation AS kyc WHERE kyc.kycId in :ids ORDER BY kyc.kycDate ASC", KycInformation.class);
        tq.setParameter("ids", ids);
        return tq.getResultList();
    }

    @Override
    public long countKycRequestsByStatus(KycStatus status) {
        TypedQuery<Long> tq = em.createQuery("select count(kyc) from KycInformation AS kyc WHERE kyc.status = :status", Long.class);
        tq.setParameter("status", status);
        return tq.getSingleResult();
    }
}

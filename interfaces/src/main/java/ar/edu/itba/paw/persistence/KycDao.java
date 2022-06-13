package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.KycInformation;
import ar.edu.itba.paw.model.KycStatus;
import ar.edu.itba.paw.parameterObject.KycInformationPO;

import java.util.Collection;


public interface KycDao {

    /** Kyc Request creation and manipulation */
    void newKycRequest(KycInformationPO kycInformationPO);
    void setKycRequestStatus(KycStatus status, int kycId);

    /** Kyc Request and count getters */
    Collection<KycInformation> getKycRequestsByStatus(String username, KycStatus status);
    Collection<KycInformation> getKycRequestsByStatus(KycStatus status, int page, int pageSize);
    long countKycRequestsByStatus(String username, KycStatus status);
}

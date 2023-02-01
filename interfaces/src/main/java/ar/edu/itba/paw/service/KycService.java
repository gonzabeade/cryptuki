package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Country;
import ar.edu.itba.paw.model.KycInformation;
import ar.edu.itba.paw.model.parameterObject.KycInformationPO;

import java.util.Collection;
import java.util.Optional;


public interface KycService {

    /** Kyc Request creation */
    void newKycRequest(KycInformationPO kycInformationPO);

    /** Kyc Request manipulation */
    void validateKycRequest(int kycId);
    void rejectKycRequest(int kycId, String reason);

    /** Kyc Request getters */
    Optional<KycInformation> getPendingKycRequest(String username);
    Optional<KycInformation> getKycRequest(String username);
    Collection<KycInformation> getPendingKycRequests(int page, int pageSize);
    long getPendingKycRequestsCount();

    /** Get all emission countries that are supported by the KYC service*/
    Collection<Country> getAllCountries();

}

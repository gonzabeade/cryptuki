package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.KycInformation;

import java.util.Optional;

public interface KycService {

    void newKycRequest(KycInformation.KycInformationBuilder builder);
    Optional<KycInformation> getPendingKycRequest(String username);

    boolean canRequestNewKyc(String username);
    void validateKycRequest(int kycId);
    void rejectKycRequest(int kycId);

    boolean isValidated(String username);

}

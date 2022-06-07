package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.KycInformation;

import java.util.Optional;

public interface KycService {

    void newKycRequest(KycInformation.KycInformationBuilder builder);
    Optional<KycInformation> getKycRequestFromUsername(String username);

    void validateIdentity(String username);

}

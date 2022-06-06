package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.KycStatus;

import java.util.Optional;


public interface KycDao {

    void newKycRequest(KycInformation.KycInformationBuilder builder);
    void setKycRequestStatus(KycStatus status, String username);
    Optional<KycInformation> getKycRequest(String username);

}

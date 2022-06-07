package ar.edu.itba.paw.service;

import ar.edu.itba.paw.KycStatus;
import ar.edu.itba.paw.persistence.KycDao;
import ar.edu.itba.paw.persistence.KycInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class KycServiceImpl implements KycService {

    private final KycDao kycDao;

    @Autowired
    public KycServiceImpl(KycDao kycDao) {
        this.kycDao = kycDao;
    }

    @Override
    @Transactional
    public void newKycRequest(KycInformation.KycInformationBuilder builder) {
        kycDao.newKycRequest(builder);
    }

    @Override
    @Transactional
    public Optional<KycInformation> getKycRequestFromUsername(String username) {
        return kycDao.getKycRequest(username);
    }

    @Override
    @Transactional
    public void validateIdentity(String username) {
        kycDao.setKycRequestStatus(KycStatus.APR, username);
    }

}

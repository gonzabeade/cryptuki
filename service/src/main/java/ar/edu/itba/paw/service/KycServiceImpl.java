package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Country;
import ar.edu.itba.paw.model.KycInformation;
import ar.edu.itba.paw.model.KycStatus;
import ar.edu.itba.paw.model.parameterObject.KycInformationPO;
import ar.edu.itba.paw.persistence.KycDao;
import ar.edu.itba.paw.persistence.EmissionCountryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class KycServiceImpl implements KycService {

    private final KycDao kycDao;
    private final EmissionCountryDao emissionCountryDao;
    private final MessageSenderFacade messageSenderFacade;

    @Autowired
    public KycServiceImpl(KycDao kycDao, EmissionCountryDao emissionCountryDao, MessageSenderFacade messageSenderFacade) {
        this.kycDao = kycDao;
        this.emissionCountryDao = emissionCountryDao;
        this.messageSenderFacade = messageSenderFacade;
    }

    @Override
    @Transactional
    public void newKycRequest(KycInformationPO kycInformationPO) {
        kycDao.newKycRequest(kycInformationPO);
    }

    @Override
    @Transactional
    public Optional<KycInformation> getPendingKycRequest(String username) {
        return kycDao.getKycRequestsByStatus(username, KycStatus.PEN).stream().findFirst();
    }

    @Override
    @Transactional
    public Collection<KycInformation> getPendingKycRequests(int page, int pageSize) {
        return kycDao.getKycRequestsByStatus(KycStatus.PEN, page, pageSize);
    }

    @Override
    public long getPendingKycRequestsCount() {
        return kycDao.countKycRequestsByStatus(KycStatus.PEN);
    }

    @Override
    @Transactional
    public void validateKycRequest(int kycId) {
        KycInformation kycInformation = kycDao.setKycRequestStatus(KycStatus.APR, kycId);
        messageSenderFacade.sendIdentityVerified(kycInformation.getUser());
    }

    @Override
    @Transactional
    public void rejectKycRequest(int kycId, String reason) {
        KycInformation kycInformation = kycDao.setKycRequestStatus(KycStatus.REJ, kycId);
        messageSenderFacade.sendIdentityRequestRejected(kycInformation.getUser(),reason);
    }

    @Override
    @Transactional
    public Collection<Country> getAllCountries() {
        return emissionCountryDao.getAllCountries();
    }

}

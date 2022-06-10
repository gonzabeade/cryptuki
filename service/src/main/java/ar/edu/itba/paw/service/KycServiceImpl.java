package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.KycStatus;
import ar.edu.itba.paw.persistence.KycDao;
import ar.edu.itba.paw.persistence.KycInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
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
    public Optional<KycInformation> getPendingKycRequest(String username) {
        return kycDao.getKycRequestsByStatus(username, KycStatus.PEN).stream().findFirst();
    }

    @Override
    @Transactional
    public Collection<KycInformation> getPendingKycRequests(int page, int pageSize) {
        return kycDao.getKycRequestsByStatus(KycStatus.PEN, page, pageSize);
    }

    @Override
    @Transactional
    public boolean canRequestNewKyc(String username) {
        return kycDao.countKycRequestsByStatus(username, KycStatus.PEN) == 0
                && kycDao.countKycRequestsByStatus(username, KycStatus.APR) == 0;
    }

    @Override
    @Transactional
    public void validateKycRequest(int kycId) {
        // TODO: enviar mail con confirmacion
        kycDao.setKycRequestStatus(KycStatus.APR, kycId);
    }

    @Override
    @Transactional
    public void rejectKycRequest(int kycId, String reason) {
        // TODO: enviar mail con la razon
        kycDao.setKycRequestStatus(KycStatus.REJ, kycId);
    }

    @Override
    public boolean isValidated(String username) {
        return kycDao.countKycRequestsByStatus(username, KycStatus.APR) == 1;
    }

}

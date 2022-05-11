package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.PersistenceException;
import ar.edu.itba.paw.exception.ServiceDataAccessException;
import ar.edu.itba.paw.persistence.PaymentMethod;
import ar.edu.itba.paw.persistence.PaymentMethodDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    private final PaymentMethodDao paymentMethodDao;

    @Autowired
    public PaymentMethodServiceImpl(PaymentMethodDao paymentMethodDao) {
        this.paymentMethodDao = paymentMethodDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<PaymentMethod> getAllPaymentMethods() {
        try {
            return paymentMethodDao.getAllPaymentMethods();
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentMethod> getPaymentMethodByCode(String code) {
        try {
            return paymentMethodDao.getPaymentMethodByCode(code);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }


}

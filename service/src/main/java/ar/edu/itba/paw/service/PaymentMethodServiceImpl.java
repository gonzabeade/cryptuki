package ar.edu.itba.paw.service;
import ar.edu.itba.paw.persistence.PaymentMethod;
import ar.edu.itba.paw.persistence.PaymentMethodDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

    private PaymentMethodDao paymentMethodDao;

    @Autowired
    public PaymentMethodServiceImpl(PaymentMethodDao paymentMethodDao) {
        this.paymentMethodDao = paymentMethodDao;
    }

    @Override
    public Collection<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodDao.getAllPaymentMethods();
    }

    @Override
    public Optional<PaymentMethod> getPaymentMethodByCode(String code) {
        return paymentMethodDao.getPaymentMethodByCode(code);
    }


}

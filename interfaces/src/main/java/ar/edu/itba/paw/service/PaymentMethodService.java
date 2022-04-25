package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.PaymentMethod;
import java.util.Collection;
import java.util.Optional;

public interface PaymentMethodService {

    Collection<PaymentMethod> getAllPaymentMethods();
    Optional<PaymentMethod> getPaymentMethodByCode(String code);
}

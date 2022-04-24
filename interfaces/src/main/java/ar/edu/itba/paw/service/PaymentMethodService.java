package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.PaymentMethod;
import java.util.Collection;

public interface PaymentMethodService {

    Collection<PaymentMethod> getAllPaymentMethods();
}

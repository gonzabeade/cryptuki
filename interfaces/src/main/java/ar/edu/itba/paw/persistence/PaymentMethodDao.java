package ar.edu.itba.paw.persistence;
import java.util.Collection;

public interface PaymentMethodDao {

    Collection<PaymentMethod> getAllPaymentMethods();
}

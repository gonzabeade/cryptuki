package ar.edu.itba.paw.persistence;
import java.util.Collection;
import java.util.Optional;

public interface PaymentMethodDao {

    Collection<PaymentMethod> getAllPaymentMethods();

    Optional<PaymentMethod> getPaymentMethodByCode(String code);

}

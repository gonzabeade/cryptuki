package ar.edu.itba.paw.cryptuki.validator;

import ar.edu.itba.paw.cryptuki.annotation.validation.AmountCheck;
import ar.edu.itba.paw.model.Offer;
import ar.edu.itba.paw.service.OfferService;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@Component
public class AmountValidator implements ConstraintValidator<AmountCheck, Object> {

    @Autowired
    private OfferService offerService;

    private String amount;
    private String offerId;

    public void initialize(AmountCheck constraintAnnotation) {
        this.amount = constraintAnnotation.amount();
        this.offerId = constraintAnnotation.offerId();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        Integer offerIdInt;
        Double amountNumber;
        try {
            offerIdInt = (Integer) new BeanWrapperImpl(value).getPropertyValue(offerId);
        } catch (ClassCastException classCastException) {
            throw new IllegalArgumentException("offerId must be an Integer.", classCastException);
        }
        try {
            amountNumber = (Double) new BeanWrapperImpl(value).getPropertyValue(amount);
        } catch (ClassCastException classCastException) {
            throw new IllegalArgumentException("amount must be a Double.", classCastException);
        }

        Optional<Offer> offerOptional = offerService.getOfferById(offerIdInt);

        if (!offerOptional.isPresent())
            return false;
        Offer offer = offerOptional.get();
        double offerPrice = offer.getUnitPrice();
        return amountNumber != null && amountNumber.compareTo(offerPrice * offer.getMinInCrypto()) >= 0  && amountNumber.compareTo(offerPrice * offer.getMaxInCrypto()) <= 0;
    }
}
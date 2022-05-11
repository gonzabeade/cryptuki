package ar.edu.itba.paw.cryptuki.form.validator;

import ar.edu.itba.paw.cryptuki.form.annotation.AmountCheck;
import ar.edu.itba.paw.cryptuki.form.OfferBuyForm;
import ar.edu.itba.paw.cryptuki.form.annotation.EqualFields;
import ar.edu.itba.paw.persistence.Offer;
import ar.edu.itba.paw.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@Component
public class AmountValidator implements ConstraintValidator<AmountCheck, Float> {

    @Autowired
    private OfferService offerService;

    private String offerId;

    public void initialize(AmountCheck constraintAnnotation) {
        this.offerId = constraintAnnotation.offerId();
    }

    @Override
    public boolean isValid(Float amount, ConstraintValidatorContext constraintValidatorContext) {
        Integer offerId = Integer.getInteger(this.offerId);
        if (offerId == null)
            return false;
        Optional<Offer> offerOptional = offerService.getOfferById(offerId);
        if (!offerOptional.isPresent())
            return false;
        Offer offer = offerOptional.get();
        float offerPrice = offer.getAskingPrice();
        return ( amount != null && offerPrice * offer.getMinQuantity() <= amount && amount <= offerPrice * offer.getMaxQuantity());
    }
}
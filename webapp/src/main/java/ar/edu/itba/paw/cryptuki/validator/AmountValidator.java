package ar.edu.itba.paw.cryptuki.validator;

import ar.edu.itba.paw.cryptuki.annotation.AmountCheck;
import ar.edu.itba.paw.cryptuki.form.OfferBuyForm;
import ar.edu.itba.paw.persistence.Offer;
import ar.edu.itba.paw.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class AmountValidator implements ConstraintValidator<AmountCheck,Object> {

    @Autowired
    private OfferService offerService;

    @Override
    public void initialize(AmountCheck amountCheck) {
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        OfferBuyForm form = (OfferBuyForm) o;
        Offer offer = offerService.getOfferById(form.getOfferId()).get();
        float offerPrice = offer.getAskingPrice();
        if(offerPrice * offer.getMinQuantity() <= form.getAmount() && form.getAmount() <= offerPrice * offer.getMaxQuantity() )
            return true;
        return false;
    }
}
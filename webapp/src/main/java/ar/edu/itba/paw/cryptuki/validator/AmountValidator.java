package ar.edu.itba.paw.cryptuki.validator;

import ar.edu.itba.paw.cryptuki.annotation.AmountCheck;
import ar.edu.itba.paw.cryptuki.form.OfferBuyForm;
import ar.edu.itba.paw.persistence.Offer;
import ar.edu.itba.paw.service.OfferService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AmountValidator implements ConstraintValidator<AmountCheck,Object> {

    private final OfferService offerService;

    public AmountValidator(OfferService offerService) {
        this.offerService = offerService;
    }

    @Override
    public void initialize(AmountCheck amountCheck) {
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        OfferBuyForm form = (OfferBuyForm) o;
        Offer offer = offerService.getOfferById(form.getOfferId()).get();
        float offerPrice = offer.getAskingPrice();
        if(offerPrice * offer.getMinQuantity() < form.getAmount() && form.getAmount() < offerPrice * offer.getMaxQuantity() )
            return true;
        return false;
    }
}
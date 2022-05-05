package ar.edu.itba.paw.cryptuki.validator;

import ar.edu.itba.paw.cryptuki.annotation.MaxAmount;
import ar.edu.itba.paw.cryptuki.form.OfferBuyForm;
import ar.edu.itba.paw.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AmountValidator implements ConstraintValidator<MaxAmount,Float> {

    private Float maxAmount;
    private Float actualAmount;
    private Float minAmount;


    @Override
    public void initialize(MaxAmount maxAmount) {

    }

    @Override
    public boolean isValid(Float aFloat, ConstraintValidatorContext constraintValidatorContext) {
        return true;
    }
}
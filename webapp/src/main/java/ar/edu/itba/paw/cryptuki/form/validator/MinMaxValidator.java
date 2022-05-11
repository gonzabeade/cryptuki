package ar.edu.itba.paw.cryptuki.form.validator;

import ar.edu.itba.paw.cryptuki.form.UploadOfferForm;

import ar.edu.itba.paw.cryptuki.form.annotation.MinMax;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class MinMaxValidator implements ConstraintValidator<MinMax,Object> {


    @Override
    public void initialize(MinMax minMax) {
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        UploadOfferForm form = (UploadOfferForm) o;
        return (form.getMinAmount()!=null && form.getMaxAmount()!=null && form.getMinAmount() <= form.getMaxAmount());
    }
}
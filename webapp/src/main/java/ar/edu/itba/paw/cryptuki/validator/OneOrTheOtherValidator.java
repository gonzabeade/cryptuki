package ar.edu.itba.paw.cryptuki.validator;

import ar.edu.itba.paw.cryptuki.annotation.validation.MinLessThanMax;
import ar.edu.itba.paw.cryptuki.annotation.validation.OneOrTheOther;
import ar.edu.itba.paw.service.OfferService;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OneOrTheOtherValidator implements ConstraintValidator<OneOrTheOther, Object> {

    private String field1;
    private String field2;

    public void initialize(OneOrTheOther oneOrTheOther) {
        field1 = oneOrTheOther.field1();
        field2 = oneOrTheOther.field2();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        Object field1 = new BeanWrapperImpl(value).getPropertyValue(this.field1);
        Object field2 = new BeanWrapperImpl(value).getPropertyValue(this.field2);
        return field1 == null ^ field2 == null;
    }
}
package ar.edu.itba.paw.cryptuki.form.legacy.validator;

import ar.edu.itba.paw.cryptuki.form.legacy.annotation.MinLessThanMax;
import ar.edu.itba.paw.service.OfferService;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class MinLessThanMaxValidator implements ConstraintValidator<MinLessThanMax, Object> {

    @Autowired
    private OfferService offerService;

    private String min;
    private String max;

    public void initialize(MinLessThanMax constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        Double min;
        Double max;
        try {
            min = (Double) new BeanWrapperImpl(value).getPropertyValue(this.min);
        } catch (ClassCastException classCastException) {
            throw new IllegalArgumentException("min must be a Double.", classCastException);
        }
        try {
            max = (Double) new BeanWrapperImpl(value).getPropertyValue(this.max);
        } catch (ClassCastException classCastException) {
            throw new IllegalArgumentException("max must be a Double.", classCastException);
        }

        return min != null && max != null && min < max;
    }
}
package ar.edu.itba.paw.cryptuki.form.validator;

import ar.edu.itba.paw.cryptuki.form.annotation.EqualFields;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class EqualFieldsValidator implements ConstraintValidator<EqualFields, Object> {

    private String field1;
    private String field2;

    @Override
    public void initialize(EqualFields constraintAnnotation) {
        this.field1 = constraintAnnotation.field1();
        this.field2 = constraintAnnotation.field2();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {

        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field1);
        Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(field2);

        boolean isValid = Objects.equals(fieldValue, fieldMatchValue);
        return isValid;
    }

}

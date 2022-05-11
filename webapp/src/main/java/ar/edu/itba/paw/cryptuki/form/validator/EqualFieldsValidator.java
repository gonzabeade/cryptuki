package ar.edu.itba.paw.cryptuki.form.validator;

import ar.edu.itba.paw.cryptuki.form.annotation.EqualFields;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class EqualFieldsValidator implements ConstraintValidator<EqualFields, Object> {

    private String field;
    private String fieldMatch;

    public void initialize(EqualFields constraintAnnotation) {
        this.field = constraintAnnotation.field1();
        this.fieldMatch = constraintAnnotation.field2();
    }

    public boolean isValid(final Object value, final ConstraintValidatorContext context) {

        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);

        boolean isValid = Objects.equals(fieldValue, fieldMatchValue);
        return isValid;
    }

}

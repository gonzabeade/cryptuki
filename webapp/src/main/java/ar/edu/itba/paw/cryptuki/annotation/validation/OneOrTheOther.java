package ar.edu.itba.paw.cryptuki.annotation.validation;
import ar.edu.itba.paw.cryptuki.validator.MinLessThanMaxValidator;
import ar.edu.itba.paw.cryptuki.validator.OneOrTheOtherValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = OneOrTheOtherValidator.class)
@Target( ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OneOrTheOther {
    //error message
    public String message() default "Exactly one of the following must be present: {field1} or {field2}";
    //represents group of constraints
    public Class<?>[] groups() default {};
    //represents additional information about annotation
    public Class<? extends Payload>[] payload() default {};

    String field1();
    String field2();
}
package ar.edu.itba.paw.cryptuki.form.legacy.annotation;

import ar.edu.itba.paw.cryptuki.form.legacy.validator.EqualFieldsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy = EqualFieldsValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface EqualFields {

    String message() default "Fields must be equal";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String field1();

    String field2();

}
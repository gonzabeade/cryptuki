package ar.edu.itba.paw.cryptuki.form.annotation;

import ar.edu.itba.paw.cryptuki.form.validator.DuplicateUsernameValidator;
import ar.edu.itba.paw.cryptuki.form.validator.EmailRegisteredValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {EmailRegisteredValidator.class})
public @interface EmailRegistered {

    String message() default "User with that email does not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
}
package ar.edu.itba.paw.cryptuki.form.annotation;

import ar.edu.itba.paw.cryptuki.form.validator.EmailVerifiedValidator;
import ar.edu.itba.paw.cryptuki.form.validator.MultipartCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {MultipartCheckValidator.class})
public @interface MultipartCheck {

    String message() default "You must upload a file";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
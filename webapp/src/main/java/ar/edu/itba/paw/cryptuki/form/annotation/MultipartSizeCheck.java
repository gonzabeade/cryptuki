package ar.edu.itba.paw.cryptuki.form.annotation;

import ar.edu.itba.paw.cryptuki.form.validator.MultipartSizeCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {MultipartSizeCheckValidator.class})
public @interface MultipartSizeCheck {

    String message() default "File is too large";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int maxSize();
}
package ar.edu.itba.paw.cryptuki.form.annotation;

import ar.edu.itba.paw.cryptuki.form.validator.AmountValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = AmountValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AmountCheck {
    //error message
    public String message() default "Debe enviar una cantidad mayor o igual al mínimo y menor o igual al máximo";
    //represents group of constraints
    public Class<?>[] groups() default {};
    //represents additional information about annotation
    public Class<? extends Payload>[] payload() default {};

    String offerId();
    String amount();

}
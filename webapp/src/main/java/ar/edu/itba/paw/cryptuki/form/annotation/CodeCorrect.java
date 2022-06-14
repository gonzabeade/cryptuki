package ar.edu.itba.paw.cryptuki.form.annotation;

import ar.edu.itba.paw.cryptuki.form.validator.CodeCorrectValidator;
import ar.edu.itba.paw.cryptuki.form.validator.MinLessThanMaxValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CodeCorrectValidator.class)
@Target( ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CodeCorrect {
    //error message
    public String message() default "El codigo ingresado no es correcto";
    //represents group of constraints
    public Class<?>[] groups() default {};
    //represents additional information about annotation
    public Class<? extends Payload>[] payload() default {};

    String usernameField();
    String codeField();
}
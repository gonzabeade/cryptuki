package ar.edu.itba.paw.cryptuki.annotation;

import ar.edu.itba.paw.cryptuki.validator.ValueOfEnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Arrays;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ValueOfEnumValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RUNTIME)
//@Documented
public @interface ValueOfEnum {
    Class<? extends Enum<?>> enumClass();
    String message() default "must be any of {enumClass}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
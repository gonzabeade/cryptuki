package ar.edu.itba.paw.cryptuki.annotation.validation;

import ar.edu.itba.paw.cryptuki.validator.CollectionOfEnumValidator;
import ar.edu.itba.paw.model.TradeStatus;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.stream.Collectors;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = CollectionOfEnumValidator.class)
public @interface CollectionOfEnum {
    Class<? extends Enum<?>> enumClass();
    String message() default "some element in collection does not belong to class {enumClass}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
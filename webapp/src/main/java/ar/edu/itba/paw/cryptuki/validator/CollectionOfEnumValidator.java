package ar.edu.itba.paw.cryptuki.validator;

import ar.edu.itba.paw.cryptuki.annotation.CollectionOfEnum;
import ar.edu.itba.paw.cryptuki.annotation.ValueOfEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionOfEnumValidator implements ConstraintValidator<CollectionOfEnum, Collection<String>> {
    private List<String> acceptedValues;

    @Override
    public void initialize(CollectionOfEnum annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(Collection<String> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return acceptedValues.containsAll(value);
    }
}
package ar.edu.itba.paw.cryptuki.validator;

import ar.edu.itba.paw.cryptuki.annotation.DuplicateUsername;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DuplicateUsernameValidator implements ConstraintValidator<DuplicateUsername, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(DuplicateUsername constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !userService.getUserByUsername(value).isPresent();
    }
}
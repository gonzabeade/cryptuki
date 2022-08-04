package ar.edu.itba.paw.cryptuki.form.legacy.validator;

import ar.edu.itba.paw.cryptuki.form.legacy.annotation.DuplicateEmail;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DuplicateEmailValidator implements ConstraintValidator<DuplicateEmail, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(DuplicateEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !userService.getUserByEmail(value).isPresent();
    }
}
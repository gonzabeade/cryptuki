package ar.edu.itba.paw.cryptuki.form.validator;

import ar.edu.itba.paw.cryptuki.form.annotation.DuplicateEmail;
import ar.edu.itba.paw.cryptuki.form.annotation.EmailRegistered;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailRegisteredValidator implements ConstraintValidator<EmailRegistered, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(EmailRegistered constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userService.getUserAuthByEmail(value).isPresent();
    }
}
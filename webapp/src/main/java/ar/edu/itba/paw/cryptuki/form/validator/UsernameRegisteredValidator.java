package ar.edu.itba.paw.cryptuki.form.validator;

import ar.edu.itba.paw.cryptuki.form.annotation.UsernameRegistered;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UsernameRegisteredValidator implements ConstraintValidator<UsernameRegistered, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(UsernameRegistered constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return userService.getUserByUsername(value).isPresent();
    }
}
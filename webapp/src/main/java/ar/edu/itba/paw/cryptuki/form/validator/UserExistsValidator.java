package ar.edu.itba.paw.cryptuki.form.validator;

import ar.edu.itba.paw.cryptuki.form.RegisterForm;
import ar.edu.itba.paw.cryptuki.form.annotation.AmountCheck;
import ar.edu.itba.paw.cryptuki.form.OfferBuyForm;
import ar.edu.itba.paw.cryptuki.form.annotation.UserExists;
import ar.edu.itba.paw.persistence.Offer;
import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.service.OfferService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UserExistsValidator implements ConstraintValidator<UserExists,Object> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(UserExists userExists) {
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        RegisterForm form = (RegisterForm) o;
        return !userService.userExists(form.getUsername(), form.getEmail());
   }
}
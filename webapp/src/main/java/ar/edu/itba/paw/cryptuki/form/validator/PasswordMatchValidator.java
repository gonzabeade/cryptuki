package ar.edu.itba.paw.cryptuki.form.validator;

import ar.edu.itba.paw.cryptuki.form.RegisterForm;
import ar.edu.itba.paw.cryptuki.form.annotation.PasswordMatch;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch,Object> {


    @Override
    public void initialize(PasswordMatch passwordMatch) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        RegisterForm form = (RegisterForm) o;
        return ( form.getPassword()!= null && form.getPassword().equals(form.getRepeatPassword()));
    }
}
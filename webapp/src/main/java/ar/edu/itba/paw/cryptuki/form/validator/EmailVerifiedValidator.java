package ar.edu.itba.paw.cryptuki.form.validator;

import ar.edu.itba.paw.cryptuki.form.annotation.EmailVerified;
import ar.edu.itba.paw.persistence.UserAuth;
import ar.edu.itba.paw.persistence.UserStatus;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class EmailVerifiedValidator implements ConstraintValidator<EmailVerified, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(EmailVerified constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Optional<UserAuth> userAuthOptional = userService.getUserAuthByEmail(value);
        return userAuthOptional.isPresent() && userAuthOptional.get().getUserStatus().equals(UserStatus.VERIFIED);
    }
}
package ar.edu.itba.paw.cryptuki.form.legacy.validator;

import ar.edu.itba.paw.cryptuki.form.legacy.annotation.CodeCorrect;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@Component
public class CodeCorrectValidator implements ConstraintValidator<CodeCorrect, Object> {

    @Autowired
    private UserService userService;
    private String codeField;
    private String usernameField;

    @Override
    public void initialize(CodeCorrect constraintAnnotation) {
        this.codeField = constraintAnnotation.codeField();
        this.usernameField = constraintAnnotation.usernameField();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {

        Integer code;
        String username;
        try {
            code = (Integer) new BeanWrapperImpl(value).getPropertyValue(this.codeField);
        } catch (ClassCastException classCastException) {
            throw new IllegalArgumentException("code must be a int.", classCastException);
        }
        try {
            username = (String) new BeanWrapperImpl(value).getPropertyValue(this.usernameField);
        } catch (ClassCastException classCastException) {
            throw new IllegalArgumentException("username must be a String.", classCastException);
        }
        Optional<User> user = userService.getUserByUsername(username);
        return user.isPresent() && user.get().getUserAuth().getCode().equals(code);
    }

}

package ar.edu.itba.paw.cryptuki.form.validator;

import ar.edu.itba.paw.cryptuki.form.annotation.EmailVerified;
import ar.edu.itba.paw.cryptuki.form.annotation.MultipartCheck;
import ar.edu.itba.paw.persistence.UserAuth;
import ar.edu.itba.paw.persistence.UserStatus;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class MultipartCheckValidator implements ConstraintValidator<MultipartCheck, MultipartFile> {



    @Override
    public void initialize(MultipartCheck multipartCheck) {
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        return !multipartFile.isEmpty();
    }
}
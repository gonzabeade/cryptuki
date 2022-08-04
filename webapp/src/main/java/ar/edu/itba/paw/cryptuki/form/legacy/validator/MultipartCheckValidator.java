package ar.edu.itba.paw.cryptuki.form.legacy.validator;

import ar.edu.itba.paw.cryptuki.form.legacy.annotation.MultipartCheck;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultipartCheckValidator implements ConstraintValidator<MultipartCheck, MultipartFile> {

    @Override
    public void initialize(MultipartCheck multipartCheck) {
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        return !multipartFile.isEmpty();
    }
}
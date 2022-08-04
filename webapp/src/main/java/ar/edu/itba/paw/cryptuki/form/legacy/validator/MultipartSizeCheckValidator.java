package ar.edu.itba.paw.cryptuki.form.legacy.validator;

import ar.edu.itba.paw.cryptuki.form.legacy.annotation.MultipartSizeCheck;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultipartSizeCheckValidator implements ConstraintValidator<MultipartSizeCheck, MultipartFile> {

    private int maxSize;

    @Override
    public void initialize(MultipartSizeCheck multipartCheck) {
        this.maxSize = multipartCheck.maxSize();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        return !multipartFile.isEmpty() && multipartFile.getSize() < maxSize;
    }
}
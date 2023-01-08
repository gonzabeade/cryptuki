package ar.edu.itba.paw.cryptuki.dto;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.validation.ValidationException;
import java.util.Arrays;

public class ValidationErrorDto {

    private String message;
    private String path;

    public static ValidationErrorDto fromValidationException(final ConstraintViolation violation) {
        final ValidationErrorDto dto = new ValidationErrorDto();
        dto.message = violation.getMessageTemplate();
        Path.Node leaf = null;
        for (Path.Node p: violation.getPropertyPath())
            leaf = p;
        if (leaf != null && !leaf.toString().equals(""))
            dto.path = String.valueOf(leaf);
        return dto;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

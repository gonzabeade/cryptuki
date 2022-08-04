package ar.edu.itba.paw.cryptuki.dto;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

// TODO: Think and play with the path value! Trunctae, look at examples, etc.
public class ValidationErrorDto {

    private String message;
    private String path;

    public static ValidationErrorDto fromValidationException(final ConstraintViolation violation) {
        final ValidationErrorDto dto = new ValidationErrorDto();
        dto.message = violation.getMessage();
        dto.path = violation.getPropertyPath().toString();
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

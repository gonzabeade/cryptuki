package ar.edu.itba.paw.cryptuki.dto;

import org.springframework.expression.AccessException;

public class AuthorizationErrorDto {

    private String message;

    public static AuthorizationErrorDto fromAccessException(final AccessException accessException) {
        AuthorizationErrorDto dto = new AuthorizationErrorDto();
        dto.message = accessException.getMessage();
        return dto;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

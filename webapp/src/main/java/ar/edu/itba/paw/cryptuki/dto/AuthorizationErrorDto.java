package ar.edu.itba.paw.cryptuki.dto;

import org.springframework.security.access.AccessDeniedException;

public class AuthorizationErrorDto {

    private String message;

    public static AuthorizationErrorDto fromAccessDeniedException(final AccessDeniedException accessDeniedException) {
        AuthorizationErrorDto dto = new AuthorizationErrorDto();
        dto.message = accessDeniedException.getMessage();
        return dto;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

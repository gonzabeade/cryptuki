package ar.edu.itba.paw.cryptuki.dto;

public class UserNonceDto {

    private static final String MESSAGE = "A nonce has been sent to %s";

    private String message;

    public static UserNonceDto fromEmail(String email) {
        UserNonceDto dto = new UserNonceDto();
        dto.message = String.format(MESSAGE, email);
        return dto;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

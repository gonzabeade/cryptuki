package ar.edu.itba.paw.cryptuki.dto;

public class NotFoundErrorDto {

    private String message;

    public static NotFoundErrorDto fromMessage(String message) {
        NotFoundErrorDto dto = new NotFoundErrorDto();
        dto.message = message;
        return dto;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

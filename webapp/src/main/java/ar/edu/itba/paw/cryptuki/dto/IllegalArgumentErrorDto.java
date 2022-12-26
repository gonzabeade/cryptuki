package ar.edu.itba.paw.cryptuki.dto;

public class IllegalArgumentErrorDto {

    private String message;

    public static IllegalArgumentErrorDto fromMessage(String message) {
        IllegalArgumentErrorDto dto = new IllegalArgumentErrorDto();
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

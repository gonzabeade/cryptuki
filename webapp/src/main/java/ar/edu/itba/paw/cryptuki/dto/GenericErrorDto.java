package ar.edu.itba.paw.cryptuki.dto;

public class GenericErrorDto {

    private String message;

    public static GenericErrorDto fromMessage(String message) {
        GenericErrorDto dto = new GenericErrorDto();
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

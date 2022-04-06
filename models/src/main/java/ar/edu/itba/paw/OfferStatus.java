package ar.edu.itba.paw;

public class OfferStatus {

    private String code;
    private String description;

    public OfferStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }
    public String getDescription() {
        return description;
    }

}

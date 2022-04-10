package ar.edu.itba.paw.persistence;

public final class OfferStatus {

    private String code;
    private String description;

    protected OfferStatus(String code, String description) {
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

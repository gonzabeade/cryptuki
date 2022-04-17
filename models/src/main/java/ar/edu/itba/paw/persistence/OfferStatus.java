package ar.edu.itba.paw.persistence;

import java.util.HashMap;
import java.util.Map;

public final class OfferStatus {

    private String code;
    private String description;

    private static Map<String, OfferStatus> cache = new HashMap<>();

    private OfferStatus(String code, String description) {
        this.code = code;
        this.description = description;
        cache.put(code, this);
    }

    protected static OfferStatus getInstance(String code, String description) {
        return cache.getOrDefault(code, new OfferStatus(code, description));
    }

    public String getCode() {
        return code;
    }
    public String getDescription() {
        return description;
    }

}

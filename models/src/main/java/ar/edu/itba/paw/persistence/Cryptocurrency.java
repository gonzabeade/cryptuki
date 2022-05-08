package ar.edu.itba.paw.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Cryptocurrency {

    private String code;
    private String commercialName;

    private static Map<String, Cryptocurrency> cache = new HashMap<>();

    private Cryptocurrency(String code, String commercialName) {
        this.code = code;
        this.commercialName = commercialName;
        cache.putIfAbsent(code, this);
    }

    protected static Cryptocurrency getInstance(String code, String name) {
        return cache.getOrDefault(code, new Cryptocurrency(code, name));
    }

    public String getCode() {
        return code;
    }
    public String getCommercialName() {
        return commercialName;
    }

    public boolean equals(Object object){
        if(object == this)
            return true;
        if(!(object instanceof Cryptocurrency))
            return false;
        Cryptocurrency testedCrypto = (Cryptocurrency) object;
        return testedCrypto.getCode().equals(this.getCode());
    }

}

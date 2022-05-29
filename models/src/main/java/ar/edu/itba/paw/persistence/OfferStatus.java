package ar.edu.itba.paw.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name="status")
public final class OfferStatus {

    OfferStatus(){}
    @Id
    @Column(name="code",length = 3,nullable = false)
    private String code;
    @Column(name="status_description",length = 20,nullable = false)
    private String description;

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private static Map<String, OfferStatus> cache = new HashMap<>();

    private OfferStatus(String code, String description) {
        this.code = code;
        this.description = description;
        cache.put(code, this);
    }

    public static OfferStatus getInstance(String code, String description) {
        return cache.getOrDefault(code, new OfferStatus(code, description));
    }

    public String getCode() {
        return code;
    }
    public String getDescription() {
        return description;
    }


}

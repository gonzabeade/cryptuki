package ar.edu.itba.paw.persistence;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name="cryptocurrency")
public final class Cryptocurrency {

    Cryptocurrency(){}

    @Id
    @Column(name="code",nullable = false)
    private String code;

    @Column(name="market_price")
    private double marketPrice;

    @Column(name="commercial_name",nullable = false,length = 20)
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

    public double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(double marketPrice) {
        this.marketPrice = marketPrice;
    }

}

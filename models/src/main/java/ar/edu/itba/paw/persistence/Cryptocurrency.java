package ar.edu.itba.paw.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Cryptocurrency {

    private CryptoTag cryptoTag;
    private Double marketPrice;

    // Flyweight Pattern - Why return whole new Cryptocurrency instances if I can cache the intrinsic state?
    private static Map<String, CryptoTag> cache = new HashMap<>();

    private static class CryptoTag {
        private String code;
        private String name;

        public CryptoTag(String code, String name) {
            this.code = code;
            this.name = name;
        }
    }

    private Cryptocurrency(CryptoTag cryptoTag,  Double marketPrice) {
        this.cryptoTag = cryptoTag;
        this.marketPrice = marketPrice;
    }

    // Flyweight Factory Method with on-the-go updates
    protected static Cryptocurrency getInstance(String code, String name, Double marketPrice) {
        CryptoTag ct = cache.getOrDefault(code, new CryptoTag(code, name));
        return new Cryptocurrency(ct, marketPrice);
    }

    public String getCode() {
        return cryptoTag.code;
    }
    public String getName() {
        return cryptoTag.name;
    }
    public double getMarketPrice() {
        return marketPrice;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cryptocurrency that = (Cryptocurrency) o;
        return Objects.equals(cryptoTag.code, that.cryptoTag.code) && Objects.equals(marketPrice, that.marketPrice) && Objects.equals(cryptoTag.name, cryptoTag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cryptoTag.code, marketPrice, cryptoTag.name);
    }


    @Override
    public String toString() {
        return "Cryptocurrency{" +
                "code='" + cryptoTag.code + '\'' +
                ", marketPrice=" + marketPrice +
                ", name='" + cryptoTag.name + '\'' +
                '}';
    }
}

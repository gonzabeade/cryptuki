package ar.edu.itba.paw.persistence;

import java.util.Objects;

public final class Cryptocurrency {

    private final String code;
    private final Double marketPrice;
    private final String name;

    protected Cryptocurrency(String code, String name, Double marketPrice) {
        this.code = code;
        this.marketPrice = marketPrice;
        this.name = name;
    }

    public String getCode() {
        return code;
    }
    public String getName() {
        return name;
    }
    public double getMarketPrice() {
        return marketPrice;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cryptocurrency that = (Cryptocurrency) o;
        return Objects.equals(code, that.code) && Objects.equals(marketPrice, that.marketPrice) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, marketPrice, name);
    }
}

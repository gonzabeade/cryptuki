package ar.edu.itba.paw.persistence;

public final class Cryptocurrency {

    private final String code;
    private final Double marketPrice;

    protected Cryptocurrency(String code, Double marketPrice, String name) {
        this.code = code;
        this.marketPrice = marketPrice;
        this.name = name;
    }

    private final String name;

    public String getCode() {
        return code;
    }
    public String getName() {
        return name;
    }
    public double getMarketPrice() {
        return marketPrice;
    }
}

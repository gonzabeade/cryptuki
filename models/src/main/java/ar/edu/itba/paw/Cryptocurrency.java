package ar.edu.itba.paw;

public class Cryptocurrency {

    private String code;
    private double marketPrice;
    private String name;



    public Cryptocurrency(String code, String name, float marketPrice) {
        this.code = code;
        this.name = name;
        this.marketPrice = marketPrice;
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
}

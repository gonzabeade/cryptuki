package ar.edu.itba.paw;

public class Coins {

    private String coin_id;
    private float market_price;

    public Coins(String coin_id, float market_price) {
        this.coin_id = coin_id;
        this.market_price = market_price;
    }

    public String getCoin_id() {
        return coin_id;
    }

    public float getMarket_price() {
        return market_price;
    }
}

package ar.edu.itba.paw;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Offer {
    private int id;
    private int sellerId;
    private Date date;
    private String coinId;
    private String status;
    private double askingPrice;
    private double coinAmount;
    private Set<PaymentMethod> paymentMethodsAccepted;

    public static class Builder {
        private int id;
        private int sellerId;
        private String coinId;
        private double askingPrice;
        private Date date;
        private double coinAmount;
        private String status;
        private Set<PaymentMethod> paymentMethodsAccepted = new HashSet<>();


        public Builder id(int id) { this.id = id; return this; }
        public Builder seller(int id) { sellerId = id; return this; }
        public Builder coin(String id) { coinId = id; return this; }
        public Builder price(double price) { askingPrice = price; return this; }
        public Builder date(Date date) { this.date = date; return this; }
        public Builder amount(double amount) { coinAmount = amount; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder addPaymentMethod(PaymentMethod method) {
            paymentMethodsAccepted.add(method);
            return this;
        }

        public Offer build() {
            return new Offer(this);
        }
    }

    private Offer(Builder builder) {
        id = builder.id;
        sellerId = builder.sellerId;
        date = builder.date;
        coinId = builder.coinId;
        askingPrice = builder.askingPrice;
        coinAmount = builder.coinAmount;
        status = builder.status;
        paymentMethodsAccepted = builder.paymentMethodsAccepted;
    }

    public Number getId() {
        return id;
    }
    public int getSellerId() {
        return sellerId;
    }
    public Date getDate() {
        return date;
    }
    public String getCoin_id() {
        return coinId;
    }
    public double getAskingPrice() {
        return askingPrice;
    }
    public double getCoinAmount() {
        return coinAmount;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "offer_id=" + id +
                ", seller_id=" + sellerId +
                ", offer_date=" + date +
                ", coin_id='" + coinId + '\'' +
                ", asking_price=" + askingPrice +
                ", coin_amount=" + coinAmount +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj)
            return true;
        if(!(obj instanceof Offer))
            return false;
        Offer other = (Offer) obj;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

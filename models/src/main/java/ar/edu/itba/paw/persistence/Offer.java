package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.Cryptocurrency;
import ar.edu.itba.paw.PaymentMethod;
import ar.edu.itba.paw.User;

import java.util.*;

public class Offer {

    private Integer id;
    private Integer sellerId;
    private Date date;
    private String coinId;
    private Integer statusId;
    private Double askingPrice;
    private Double coinAmount;
    private Set<PaymentMethod> paymentMethodsAccepted;

    public static class Builder {

        // Compulsory
        private Integer sellerId;
        private String coinId;
        private Double askingPrice;

        // Optional
        private Integer id; // Can be autogenerated or ignored
        private Set<PaymentMethod> paymentMethodsAccepted = new HashSet<>();
        private Date date = new Date();
        private Double coinAmount;
        private Integer statusId;

        // Main Constructor
        public Builder(int sellerId, String coinId, double askingPrice) {
            this.sellerId = sellerId;
            this.coinId = coinId;
            this.askingPrice = askingPrice;
        }

        // Optional parameters
        public Builder id(int id) { this.id = id; return this; }
        public Builder amount(double coinAmount) { this.coinAmount = coinAmount; return this; }
        public Builder date(Date date) {
            /* Defensive copy of date - Item 50 Effective Java */
            this.date = new Date(date.getTime());
            return this;
        }
        public Builder status(int statusId) { this.statusId = statusId; return this; }
        public Builder paymentMethod(PaymentMethod method) {
            paymentMethodsAccepted.add(method);
            return this;
        }

        // Getters. Finals. Do not change state of Builder but from within the Builder.
        public int getId() {
            return id;
        }
        public int getSellerId() {
            return sellerId;
        }
        public String getCoinId() {
            return coinId;
        }
        public double getAskingPrice() {
            return askingPrice;
        }
        public Set<PaymentMethod> getPaymentMethodsAccepted() {
            return paymentMethodsAccepted;
        }
        public Date getDate() {
            return date;
        }
        public Double getCoinAmount() {
            return coinAmount;
        }
        public int getStatusId() {
            return statusId;
        }

        // Restricted access Offer build method
        protected Offer build() {
            return new Offer(this);
        }
    }

    public static Builder builder(int sellerId, String coinId, double askingPrice) {
        return new Builder(sellerId, coinId, askingPrice);
    }

    private Offer(Builder builder) {
        id = builder.id;
        sellerId = builder.sellerId;
        date = builder.date;
        coinId = builder.coinId;
        askingPrice = builder.askingPrice;
        coinAmount = builder.coinAmount;
        statusId = builder.statusId;
        paymentMethodsAccepted = builder.paymentMethodsAccepted;
    }

    public int getId() {
        return id;
    }
    public int getSellerId() {
        return sellerId;
    }
    public Date getDate() {
        return new Date(date.getTime());
    } // All attributes are final
    public String getCoin_id() {
        return coinId;
    }
    public double getAskingPrice() {
        return askingPrice;
    }
    public double getCoinAmount() {
        return coinAmount;
    }
    public Iterable<PaymentMethod> getPaymentMethods() {  // Prevents manipulation of collection from outside the class
        return paymentMethodsAccepted;
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

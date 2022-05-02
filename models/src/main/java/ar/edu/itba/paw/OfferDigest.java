package ar.edu.itba.paw;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class OfferDigest {

    private Integer id;
    private final int sellerId;
    private final String cryptoCode;
    private final double askingPrice;

    private final double minQuantity;
    private final double maxQuantity;

    private final Collection<String> paymentMethods;
    private final LocalDateTime date;

    public static class Builder {

        private final String cryptoCode;
        private final double askingPrice;
        private final int sellerId;


        private Collection<String> paymentMethods = new LinkedList<>();
        private double minQuantity = 1.0f;
        private double maxQuantity = 1.0f;

        private LocalDateTime date = LocalDateTime.now();
        private Integer id;


        public Builder(int sellerId, String cryptoCode, double askingPrice) {
            this.sellerId = sellerId;
            this.cryptoCode = cryptoCode;
            this.askingPrice = askingPrice;
        }

        public int getSellerId() {
            return sellerId;
        }
        public String getCryptoCode() {
            return cryptoCode;
        }
        public double getAskingPrice() {
            return askingPrice;
        }
        public Collection<String> getPaymentMethods() {
            return paymentMethods;
        }
        public double getMinQuantity() {
            return minQuantity;
        }
        public double getMaxQuantity() {
            return maxQuantity;
        }

        public LocalDateTime getDate() {
            return date;
        }
        public Integer getId() {
            return id;
        }

        public Builder withPaymentMethod(String pm) { this.paymentMethods.add(pm); return this; }
        public Builder withMinQuantity(double quantity) { this.minQuantity = quantity; return this; }
        public Builder withMaxQuantity(double quantity) { this.maxQuantity = quantity; return this; }

        public Builder withId(int id) { this.id = id; return this; }

        public OfferDigest build() { return new OfferDigest(this); }
    }

    private OfferDigest(Builder builder) {
        id = builder.id;
        sellerId = builder.sellerId;
        cryptoCode = builder.cryptoCode;
        askingPrice = builder.askingPrice;
        minQuantity = builder.minQuantity;
        maxQuantity = builder.maxQuantity;
        paymentMethods = Collections.unmodifiableList(new LinkedList<>(builder.paymentMethods));
        date = builder.date;
    }

    public int getId() {
        return id;
    }
    public int getSellerId() {
        return sellerId;
    }
    public String getCryptoCode() {
        return cryptoCode;
    }
    public double getAskingPrice() {
        return askingPrice;
    }
    public double getMinQuantity() {
        return minQuantity;
    }
    public double getMaxQuantity() {
        return maxQuantity;
    }

    public Collection<String> getPaymentMethods() {
        return paymentMethods;
    }
    public LocalDateTime getDate() {
        return date;
    }
}

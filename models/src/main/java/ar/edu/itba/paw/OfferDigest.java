package ar.edu.itba.paw;

import ar.edu.itba.paw.persistence.Offer;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class OfferDigest {

    private Integer id;
    private final int sellerId;
    private final String cryptoCode;
    private final float askingPrice;

    private final float minQuantity;
    private final float maxQuantity;

    private final Collection<String> paymentMethods;
    private final LocalDateTime date;

    private final String comments;

    public static class Builder {

        private final String cryptoCode;
        private final float askingPrice;
        private final int sellerId;


        private String comments;

        private Collection<String> paymentMethods = new LinkedList<>();
        private float minQuantity = 1.0f;
        private float maxQuantity = 1.0f;

        private LocalDateTime date = LocalDateTime.now();
        private Integer id;


        public Builder(int sellerId, String cryptoCode, float askingPrice) {
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
        public float getAskingPrice() {
            return askingPrice;
        }
        public Collection<String> getPaymentMethods() {
            return paymentMethods;
        }
        public float getMinQuantity() {
            return minQuantity;
        }
        public float getMaxQuantity() {
            return maxQuantity;
        }

        public LocalDateTime getDate() {
            return date;
        }
        public Integer getId() {
            return id;
        }

        public String getComments() {
            return comments;
        }

        public Builder withPaymentMethod(String pm) { this.paymentMethods.add(pm); return this; }
        public Builder withMinQuantity(float quantity) { this.minQuantity = quantity; return this; }
        public Builder withMaxQuantity(float quantity) { this.maxQuantity = quantity; return this; }
        public Builder withComments(String comments) { this.comments = comments; return this; }

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
        comments = builder.comments;
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
    public float getAskingPrice() {
        return askingPrice;
    }
    public float getMinQuantity() {
        return minQuantity;
    }
    public float getMaxQuantity() {
        return maxQuantity;
    }

    public Collection<String> getPaymentMethods() {
        return paymentMethods;
    }
    public LocalDateTime getDate() {
        return date;
    }

    public String getComments() {
        return comments;
    }
}

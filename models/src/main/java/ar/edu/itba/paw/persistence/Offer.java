package ar.edu.itba.paw.persistence;

import java.time.LocalDateTime;
import java.util.*;

public final class Offer {

    private final int id;
    private final User seller;
    private final LocalDateTime date;
    private final Cryptocurrency crypto;
    private final OfferStatus status;
    private final float askingPrice;
    private final float minQuantity;
    private final float maxQuantity;
    private final String comments;

    private final Collection<PaymentMethod> paymentMethods;

    public static class Builder {

        private User seller;
        private Cryptocurrency crypto;
        private float askingPrice;
        private int id;

        private Collection<PaymentMethod> paymentMethods = new HashSet<>();
        private LocalDateTime date;
        private float minQuantity;
        private float maxQuantity;

        private String comments;

        private OfferStatus status;

        public Builder(int id, User seller, Cryptocurrency crypto, float askingPrice) {
            this.id = id;
            this.seller = seller;
            this.crypto = crypto;
            this.askingPrice = askingPrice;
        }

        public Builder withMinQuantity(float minQuantity) { this.minQuantity = minQuantity; return this; }
        public Builder withMaxQuantity(float maxQuantity) { this.maxQuantity = maxQuantity; return this; }

        public Builder withComments(String comments) { this.comments = comments; return this; }
        public Builder withDate(LocalDateTime date) {
            this.date = date; // Immutable
            return this;
        }
        protected Builder withStatus(OfferStatus statusCode) { this.status = status; return this; }
        public Builder withPaymentMethod(PaymentMethod method) {
            if (method != null) paymentMethods.add(method);
            return this;
        }

        public int getId() {
            return id;
        }
        public User getSeller() {
            return seller;
        }
        public Cryptocurrency getCrypto() {
            return crypto;
        }
        public float getAskingPrice() {
            return askingPrice;
        }
        public Collection<PaymentMethod> getPaymentMethods() {
            return paymentMethods;
        }
        public LocalDateTime getDate() {
            return date;
        }
        public float getMinQuantity() {
            return minQuantity;
        }
        public float getMaxQuantity() {
            return maxQuantity;
        }

        public OfferStatus getStatus() {
            return status;
        }

        public String getComments() {
            return comments;
        }

        protected Offer build() {
            return new Offer(this);
        }
    }

    private Offer(Builder builder) {
        id = builder.id;
        seller = builder.seller;
        date = builder.date;
        crypto = builder.crypto;
        askingPrice = builder.askingPrice;
        minQuantity = builder.minQuantity;
        maxQuantity = builder.maxQuantity;
        status = builder.status;
        paymentMethods = builder.paymentMethods;
        comments = builder.comments;
    }

    public int getId() {
        return id;
    }
    public User getSeller() {
        return seller;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public Cryptocurrency getCrypto() {
        return crypto;
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
    public Collection<PaymentMethod> getPaymentMethods() {
        return Collections.unmodifiableCollection(paymentMethods);
    }

    public OfferStatus getStatus() {
        return status;
    }

    public String getComments() {
        return comments;
    }
}

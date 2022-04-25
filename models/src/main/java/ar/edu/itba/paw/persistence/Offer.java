package ar.edu.itba.paw.persistence;

import java.time.LocalDateTime;
import java.util.*;

public final class Offer {

    private final Integer id;
    private final User seller;
    private final LocalDateTime date;
    private final Cryptocurrency crypto;
    private final OfferStatus status;
    private final Double askingPrice;
    private final Double coinAmount;
    private final Iterable<PaymentMethod> paymentMethods;

    public static class Builder {

        // Compulsory
        private User seller; // TODO: Deberia ser un User.Builder?
        private Cryptocurrency crypto;
        private Double askingPrice;

        // Optional
        private Integer id; // Can be autogenerated or ignored
        private Collection<PaymentMethod> paymentMethods = new HashSet<>();
        private LocalDateTime date;
        private Double quantity;

        private OfferStatus status;

        // Main Constructor
        private Builder(User seller, Cryptocurrency crypto, double askingPrice) {
            this.seller = seller;
            this.crypto = crypto;
            this.askingPrice = askingPrice;
            this.date = LocalDateTime.now();
        }

        // Optional parameters
        public Builder id(int id) { this.id = id; return this; }
        public Builder quantity(double quantity) { this.quantity = quantity; return this; }
        public Builder date(LocalDateTime date) {
            this.date = date; // Immutable
            return this;
        }
        protected Builder status(OfferStatus statusCode) { this.status = status; return this; }
        public Builder paymentMethod(PaymentMethod method) {
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
        public double getAskingPrice() {
            return askingPrice;
        }
        public Collection<PaymentMethod> getPaymentMethods() {
            return paymentMethods;
        }
        public LocalDateTime getDate() {
            return date;
        }
        public Double getQuantity() {
            return quantity;
        }
        public OfferStatus getStatus() {
            return status;
        }

        // Restricted access Offer build method
        protected Offer build() {
            return new Offer(this);
        }
    }

    public static Builder builder(User seller, Cryptocurrency crypto, double askingPrice) {
        return new Builder(seller, crypto, askingPrice);
    }

    private Offer(Builder builder) {
        id = builder.id;
        seller = builder.seller;
        date = builder.date;
        crypto = builder.crypto;
        askingPrice = builder.askingPrice;
        coinAmount = builder.quantity;
        status = builder.status;
        paymentMethods = builder.paymentMethods;
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
    public double getAskingPrice() {
        return askingPrice;
    }
    public double getCoinAmount() {
        return coinAmount;
    }

    public Iterable<PaymentMethod> getPaymentMethods() {  // Prevents manipulation of collection from outside the class
        return paymentMethods;
    }

    // TODO: This is a patch, erase when done
    public String getCoin_id () {
        return crypto.getCode();
    }
    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder("tu oferta \n" +
                "Identificador de la Oferta: " + id +
                "\n Dueño: " + seller.getEmail() +
                "\n Publicada el: " + date +
                "\n Criptomoneda: '" + crypto.getName() + '\'' +
                "\n Precio : " + askingPrice +
                "\n Cantidad ofertada : " + coinAmount +
                "\n Métodos de Pago: ");

        for (PaymentMethod payment_method : paymentMethods) {
            ans.append(payment_method.getDescription()).append(", ");
        }
        return ans.toString();
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

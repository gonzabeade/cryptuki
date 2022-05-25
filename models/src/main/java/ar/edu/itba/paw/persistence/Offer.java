package ar.edu.itba.paw.persistence;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;





@Entity
@Table(name="offer")
public final class Offer {
    Offer(){}
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offer_id_seq")
    @SequenceGenerator(sequenceName = "offer_id_seq", name = "offer_id_seq", allocationSize = 1)
    private  int id;
    @OneToOne
    @JoinColumn(name="seller_id")
    private  User seller;
    @Column(name="offer_date",nullable = false)
    private  LocalDateTime date;
    @OneToOne
    @JoinColumn(name="crypto_code")
    private  Cryptocurrency crypto;
    @OneToOne
    @JoinColumn(name="status_code")
    private  OfferStatus status;
    @Column(name="asking_price",nullable = false)
    private  float askingPrice;
    @Column(name="min_quantity",nullable = false)
    private  float minQuantity;
    @Column(name="max_quantity",nullable = false)
    private  float maxQuantity;
    @Column(name="comments",length = 280)
    private  String comments;

    @OneToMany(mappedBy = "offer")
    private  Collection<PaymentMethodAtOffer> paymentMethodAtOffers ;

   @Transient
   private  Collection<PaymentMethod> paymentMethods = new ArrayList<>();

    public Collection<PaymentMethodAtOffer> getPaymentMethodAtOffers() {
        return paymentMethodAtOffers;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setCrypto(Cryptocurrency crypto) {
        this.crypto = crypto;
    }

    public void setStatus(OfferStatus status) {
        this.status = status;
    }

    public void setAskingPrice(float askingPrice) {
        this.askingPrice = askingPrice;
    }

    public void setMinQuantity(float minQuantity) {
        this.minQuantity = minQuantity;
    }

    public void setMaxQuantity(float maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean acceptPaymentMethodCode(Collection<String> paymentMethodCode){
        return this.paymentMethodAtOffers.stream()
                        .map(paymentMethodAtOffer -> PaymentMethod.getInstance(paymentMethodAtOffer.getPaymentMethod().getName(),paymentMethodAtOffer.getPaymentMethod().getDescription()))
                .collect(Collectors.toCollection(ArrayList::new)).containsAll(paymentMethodCode)
        ;

    }


    public void setPaymentMethodAtOffers(Collection<PaymentMethodAtOffer> paymentMethodAtOffers) {
        this.paymentMethodAtOffers = paymentMethodAtOffers;
        for(PaymentMethodAtOffer pam : paymentMethodAtOffers)
            this.paymentMethods.add(PaymentMethod.getInstance(pam.getPaymentMethod().getName(),pam.getPaymentMethod().getDescription()));
    }

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
        protected Builder withStatus(OfferStatus status) { this.status = status; return this; }
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

        for(PaymentMethodAtOffer pam : this.paymentMethodAtOffers){
            PaymentMethod pm = pam.getPaymentMethod();
            this.paymentMethods.add(PaymentMethod.getInstance(pm.getName(),pm.getDescription()));
        }
        return Collections.unmodifiableCollection(paymentMethods);
    }

    public OfferStatus getStatus() {
        return status;
    }

    public String getComments() {
        return comments;
    }

    public boolean equals(Object object){
        if(object == this)
            return true;
        if(!(object instanceof Offer))
            return false;
        Offer testedOffer= (Offer) object;
        return testedOffer.getId() == this.getId();
    }
}

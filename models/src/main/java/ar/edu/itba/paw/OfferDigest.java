package ar.edu.itba.paw;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

@Entity
@Table(name="offer")
public class OfferDigest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offer_id_seq")
    @SequenceGenerator(sequenceName = "offer_id_seq", name = "offer_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "seller_id",nullable = false)
    private  int sellerId;

    @Column(name="offer_date",nullable = false)
    private  LocalDateTime date;
    @Column(name="crypto_code",length = 5,nullable = false)
    private  String cryptoCode;

    @Column(name="status_code",length = 3,nullable = false)
    private  String statusCode = "APR";
    @Column(name="asking_price",nullable = false)
    private  double askingPrice;
    @Column(name="max_quantity",nullable = false)
    private  double maxQuantity;

    @Column(name="min_quantity",nullable = false)
    private  double minQuantity;


    @Column(name="comments",length = 280)
    private  String comments;


    @Transient
    private  Collection<String> paymentMethods;

    OfferDigest(){}

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setCryptoCode(String cryptoCode) {
        this.cryptoCode = cryptoCode;
    }

    public void setAskingPrice(double askingPrice) {
        this.askingPrice = askingPrice;
    }

    public void setMaxQuantity(double maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public void setMinQuantity(double minQuantity) {
        this.minQuantity = minQuantity;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setPaymentMethods(Collection<String> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public static class Builder {

        private final String cryptoCode;
        private final double askingPrice;
        private final int sellerId;


        private String comments;

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

    public String getComments() {
        return comments;
    }
}

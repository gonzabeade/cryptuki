package ar.edu.itba.paw.model;

import ar.edu.itba.paw.persistence.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name="offer")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offer_id_seq")
    @SequenceGenerator(sequenceName = "offer_id_seq", name = "offer_id_seq", allocationSize = 1)
    @Column(name="id", nullable = false)
    private Integer offerId;

    @Column(name="asking_price", nullable = false)
    private double unitPrice;

    @Column(name="min_quantity", nullable = false)
    private double minInCrypto;

    @Column(name="max_quantity", nullable = false)
    private double maxInCrypto;

    @OneToOne
    @JoinColumn(name="seller_id")
    private User seller;

    @Column(name="offer_date", nullable = false, insertable = false)
    private LocalDateTime date;

    @OneToOne
    @JoinColumn(name="crypto_code")
    private Cryptocurrency crypto;

    @Enumerated(EnumType.STRING)
    @Column(name="status_code", nullable = false, insertable = false)
    private OfferStatus offerStatus;

    @Column(name="location", length = 100)
    @Enumerated(EnumType.STRING)
    private Location location;

    @OneToMany(mappedBy = "offer", fetch = FetchType.LAZY)
    private Collection<Trade> trades;

    @Column(name="comments", length = 280)
    private String comments;

    public Offer(){
        // Just for Hibernate
    }

    public static class Builder {

        private final double unitPrice;
        private final double minInCrypto;
        private final double maxInCrypto;
        private User seller;
        private Cryptocurrency crypto;
        private OfferStatus offerStatus;
        private Location location;
        private Collection<Trade> trades;
        private String comments;

        public Builder(double unitPrice, double minInCrypto, double maxInCrypto) {
            this.unitPrice = unitPrice;
            this.minInCrypto = minInCrypto;
            this.maxInCrypto = maxInCrypto;
        }

        public Builder withSeller(User seller) {
            this.seller = seller;
            return this;
        }

        public Builder withCrypto(Cryptocurrency crypto) {
            this.crypto = crypto;
            return this;
        }

        public Builder withOfferStatus(OfferStatus offerStatus) {
            this.offerStatus = offerStatus;
            return this;
        }

        public Builder withLocation(Location location) {
            this.location = location;
            return this;
        }

        public Builder withComments(String comments) {
            this.comments = comments;
            return this;
        }

        public Offer build() {
            return new Offer(this);
        }
    }

    private Offer(Builder builder) {
        this.unitPrice = builder.unitPrice;
        this.minInCrypto = builder.minInCrypto;
        this.maxInCrypto = builder.maxInCrypto;
        this.seller = builder.seller;
        this.crypto = builder.crypto;
        this.offerStatus = builder.offerStatus;
        this.location = builder.location;
        this.comments = builder.comments;
    }

    public int getOfferId() {
        return offerId;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getMinInCrypto() {
        return minInCrypto;
    }

    public double getMaxInCrypto() {
        return maxInCrypto;
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

    public OfferStatus getOfferStatus() {
        return offerStatus;
    }

    public Location getLocation() {
        return location;
    }

    public Collection<Trade> getTrades() {
        return Collections.unmodifiableCollection(trades);
    }

    public String getComments() {
        return comments;
    }

    public void setOfferStatus(OfferStatus offerStatus) {
        this.offerStatus = offerStatus;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "offerId=" + offerId +
                ", unitPrice=" + unitPrice +
                ", minInCrypto=" + minInCrypto +
                ", maxInCrypto=" + maxInCrypto +
                ", seller=" + seller +
                ", date=" + date +
                ", crypto=" + crypto +
                ", offerStatus=" + offerStatus +
                ", location=" + location +
                ", trades=" + trades +
                ", comments='" + comments + '\'' +
                '}';
    }
}

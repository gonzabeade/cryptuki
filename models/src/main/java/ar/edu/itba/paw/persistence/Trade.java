package ar.edu.itba.paw.persistence;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name="trade")
public final class Trade {

    Trade(){}
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trade_trade_id_seq")
    @SequenceGenerator(sequenceName = "trade_trade_id_seq", name = "trade_trade_id_seq", allocationSize = 1)
    @Column(name="trade_id")
    private int tradeId;

    @ManyToOne
    @JoinColumn(name="offer_id")
    private Offer offer;

    @OneToOne
    @JoinColumn(name="buyer_id")
    private User user;

    @Column(name="start_date")
    private LocalDateTime startDate;

    @Column(name="status",length = 10)
    @Enumerated(EnumType.STRING)
    private  TradeStatus status;

    @Column(name="quantity")
    private  float quantity;


    @Column(name="rated_buyer")
    private boolean ratedBuyer;
    @Column(name="rated_seller")
    private boolean ratedSeller;

    public Offer getOffer() {
        return offer;
    }

    public User getUser() {
        return user;
    }


    @Entity
    @Table(name="trade")
    public static class Builder {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trade_trade_id_seq")
        @SequenceGenerator(sequenceName = "trade_trade_id_seq", name = "trade_trade_id_seq", allocationSize = 1)
        @Column(name="trade_id")
        private Integer tradeId;

        @Column(name="offer_id")
        private  int offerId;

        @Column(name="buyer_id",nullable = false)
        private int buyerId;

        @Column(name="start_date")
        private LocalDateTime startDate = null;

        @Column(name="status",length = 10)
        @Enumerated(EnumType.STRING)
        private TradeStatus status = TradeStatus.PENDING;
        @Column(name="quantity")
        private float quantity = 0f;

        @Column(name="rated_buyer")
        private boolean ratedBuyer;

        @Column(name="rated_seller")
        private boolean ratedSeller;

        @Transient
        private String sellerUsername;
        @Transient
        private final String buyerUsername;

        @Transient
        private  Cryptocurrency cryptoCurrency;
        @Transient
        private String wallet;
        @Transient
        private float askedPrice;



        public Builder(int offerId, String buyerUsername) {
            this.offerId = offerId;
            this.buyerUsername = buyerUsername;
            this.ratedBuyer = false;
            this.ratedSeller = false;
        }

        public Builder withTradeId(int tradeId) {
            this.tradeId = tradeId;
            return this;
        }
        public Builder withSellerUsername(String sellerUsername) {
            this.sellerUsername = sellerUsername;
            return this;
        }
        public Builder withRatedBuyer(boolean rated){
            this.ratedBuyer = rated;
            return this;
        }
        public Builder withBuyerId(Integer buyerId){
            this.buyerId = buyerId;
            return this;
        }
        public Builder withRatedSeller(boolean rated){
            this.ratedSeller = rated;
            return this;
        }

        public Builder withStartDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }
        public Builder withTradeStatus(TradeStatus status) {
            this.status = status;
            return this;
        }
        public Builder withQuantity(float quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder withWallet(String wallet) {
            this.wallet = wallet;
            return this;
        }

        public Builder withCryptoCurrency(Cryptocurrency cryptoCurrency) {
            this.cryptoCurrency =cryptoCurrency;
            return this;
        }
        public Builder withAskedPrice(float askedPrice) {
            this.askedPrice=askedPrice;
            return this;
        }

        public int getTradeId() {
            return tradeId;
        }
        public int getOfferId() {
            return offerId;
        }
        public String getSellerUsername() {
            return sellerUsername;
        }
        public String getBuyerUsername() {
            return buyerUsername;
        }
//        public LocalDateTime getStartDate() {
//            return startDate;
//        }
        public TradeStatus getStatus() {
            return status;
        }
        public float getQuantity() {
            return quantity;
        }

        public Cryptocurrency getCryptoCurrency() {
            return cryptoCurrency;
        }

        public float getAskedPrice() {
            return askedPrice;
        }
        public boolean getRatedBuyer(){
            return ratedBuyer;
        }
        public boolean getRatedSeller(){
            return ratedSeller;
        }
        public String getWallet(){
            return wallet;
        }

        public int getBuyerId() {
            return buyerId;
        }

        protected Trade build() {
            return new Trade(this);
        }

    }

    protected Trade(Builder builder) {

        this.quantity = builder.quantity;
        this.tradeId = builder.tradeId;
        this.startDate = builder.startDate;
        this.status = builder.status;
        this.ratedBuyer = builder.getRatedBuyer();
        this.ratedSeller = builder.getRatedSeller();

    }

    public int getTradeId() {
        return tradeId;
    }
    public int getOfferId() {
        return offer.getId();
    }
    public String getSellerUsername() {
        return offer.getSeller().getUserAuth().getUsername();
    }
    public String getBuyerUsername() {
        return user.getUserAuth().getUsername();
    }

    public TradeStatus getStatus() {
        return status;
    }
    public float getQuantity() {
        return quantity;
    }

    public Cryptocurrency getCryptoCurrency() {
        return offer.getCrypto();
    }

    public float getAskedPrice() {
        return offer.getAskingPrice();
    }

    public boolean equals(Object object) {
        if (object == this)
            return true;
        if (!(object instanceof User))
            return false;
        Trade testedTrade = (Trade) object;
        return testedTrade.getTradeId() == this.getTradeId();
    }

    public boolean getRatedBuyer(){
        return ratedBuyer;
    }
    public boolean getRatedSeller(){
        return ratedSeller;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStatus(TradeStatus status) {
        this.status = status;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public void setRatedBuyer(boolean ratedBuyer) {
        this.ratedBuyer = ratedBuyer;
    }

    public void setRatedSeller(boolean ratedSeller) {
        this.ratedSeller = ratedSeller;
    }
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Optional<LocalDateTime> getStartDate() {
        return Optional.of(startDate);
    }

}

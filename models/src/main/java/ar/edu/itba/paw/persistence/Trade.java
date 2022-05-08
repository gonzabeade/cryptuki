package ar.edu.itba.paw.persistence;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

public final class Trade {

    private final int tradeId;
    private final int offerId;
    private final String sellerUsername;
    private final String buyerUsername;
    private final Optional<LocalDateTime> startDate;
    private final TradeStatus status;
    private final float quantity;

    private final Cryptocurrency cryptoCurrency;

    private final float askedPrice;


    public static class Builder {

        private final int offerId;
        private String sellerUsername;
        private final String buyerUsername;

        private LocalDateTime startDate = null;
        private TradeStatus status = TradeStatus.OPEN;
        private float quantity = 0f;
        private Integer tradeId;

        private  Cryptocurrency cryptoCurrency;

        private float askedPrice;


        public Builder(int offerId, String buyerUsername) {
            this.offerId = offerId;
            this.buyerUsername = buyerUsername;
        }

        public Builder withTradeId(int tradeId) {
            this.tradeId = tradeId;
            return this;
        }
        public Builder withSellerUsername(String sellerUsername) {
            this.sellerUsername = sellerUsername;
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
        public LocalDateTime getStartDate() {
            return startDate;
        }
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

        protected Trade build() {
            return new Trade(this);
        }

    }

    protected Trade(Builder builder) {
        this.buyerUsername = builder.buyerUsername;
        this.offerId = builder.offerId;
        this.quantity = builder.quantity;
        this.tradeId = builder.tradeId;
        this.startDate = Optional.ofNullable(builder.startDate);
        this.sellerUsername = builder.sellerUsername;
        this.status = builder.status;
        this.askedPrice= builder.getAskedPrice();
        this.cryptoCurrency =builder.getCryptoCurrency();

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
    public Optional<String> getStartDate() {
        return startDate.isPresent()?Optional.ofNullable(startDate.get().format(ISO_LOCAL_DATE)):Optional.empty();
    }
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
}

package ar.edu.itba.paw.cryptuki.dto;

import ar.edu.itba.paw.model.Trade;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class TradeRatingDTO {
    private Integer seller_rating;
    private Integer buyer_rating;
    private boolean buyer_rated;
    private boolean seller_rated;
    private URI trade;

    public static TradeRatingDTO fromTrade(Trade trade, final UriInfo uriInfo){
        TradeRatingDTO tradeRatingDTO = new TradeRatingDTO();
        tradeRatingDTO.seller_rated = trade.isSellerRated();
        tradeRatingDTO.buyer_rated = trade.isBuyerRated();
        tradeRatingDTO.seller_rating = trade.getSeller_rating();
        tradeRatingDTO.buyer_rating = trade.getBuyer_rating();
        tradeRatingDTO.trade = uriInfo.getBaseUriBuilder()
                .path("/api/trades")
                .path(String.valueOf(trade.getTradeId()))
                .build();
        return tradeRatingDTO;
    }

    public Integer getSeller_rating() {
        return seller_rating;
    }

    public void setSeller_rating(Integer seller_rating) {
        this.seller_rating = seller_rating;
    }

    public Integer getBuyer_rating() {
        return buyer_rating;
    }

    public void setBuyer_rating(Integer buyer_rating) {
        this.buyer_rating = buyer_rating;
    }

    public boolean isBuyer_rated() {
        return buyer_rated;
    }

    public void setBuyer_rated(boolean buyer_rated) {
        this.buyer_rated = buyer_rated;
    }

    public boolean isSeller_rated() {
        return seller_rated;
    }

    public void setSeller_rated(boolean seller_rated) {
        this.seller_rated = seller_rated;
    }

    public URI getTrade() {
        return trade;
    }

    public void setTrade(URI trade) {
        this.trade = trade;
    }
}

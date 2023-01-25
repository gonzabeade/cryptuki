package ar.edu.itba.paw.cryptuki.dto;

import ar.edu.itba.paw.model.Offer;
import ar.edu.itba.paw.model.Trade;
import ar.edu.itba.paw.model.TradeStatus;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;

public class TradeDto {

    private int tradeId;
    private TradeStatus status;
    private double buyingQuantity;
    private LocalDateTime lastModified;
    private int qUnseenMessagesBuyer;
    private int qUnseenMessagesSeller;
    private URI self;
    private URI offer;
    private URI seller;
    private URI buyer;
    private URI messages;
    private URI rating;


    public static TradeDto fromTrade(final Trade trade, final UriInfo uriInfo) {

        final TradeDto dto = new TradeDto();

        dto.buyingQuantity = trade.getQuantity();
        dto.lastModified = trade.getLastModified();
        dto.status = trade.getStatus();
        dto.tradeId = trade.getTradeId();
        dto.qUnseenMessagesBuyer = trade.getqUnseenMessagesBuyer();
        dto.qUnseenMessagesSeller =  trade.getqUnseenMessagesSeller();

        dto.self = uriInfo.getBaseUriBuilder()
                        .path("/api/trades")
                        .path(String.valueOf(dto.tradeId))
                        .build();

        dto.offer = uriInfo.getBaseUriBuilder()
                        .path("/api/offers")
                        .path(String.valueOf(trade.getOffer().getOfferId()))
                        .build();

        dto.seller = uriInfo.getBaseUriBuilder()
                .path("/api/users")
                .path(trade.getOffer().getSeller().getUsername().get())
                .build();

        dto.buyer = uriInfo.getBaseUriBuilder()
                .path("/api/users")
                .path(trade.getBuyer().getUsername().get())
                .build();

        dto.messages = uriInfo.getBaseUriBuilder()
                .path("/api/trades")
                .path(String.valueOf(dto.tradeId))
                .path("/messages")
                .build();
        dto.rating = uriInfo.getBaseUriBuilder()
                .path("/api/trades")
                .path(String.valueOf(dto.tradeId))
                .path("/rating")
                .build();

        return dto;
    }

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    public TradeStatus getStatus() {
        return status;
    }

    public void setStatus(TradeStatus status) {
        this.status = status;
    }

    public double getBuyingQuantity() {
        return buyingQuantity;
    }

    public void setBuyingQuantity(double buyingQuantity) {
        this.buyingQuantity = buyingQuantity;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

    public URI getOffer() {
        return offer;
    }

    public void setOffer(URI offer) {
        this.offer = offer;
    }

    public URI getSeller() {
        return seller;
    }

    public void setSeller(URI seller) {
        this.seller = seller;
    }

    public URI getBuyer() {
        return buyer;
    }

    public void setBuyer(URI buyer) {
        this.buyer = buyer;
    }

    public URI getMessages() {
        return messages;
    }

    public void setMessages(URI messages) {
        this.messages = messages;
    }

    public URI getRating() {
        return rating;
    }

    public void setRating(URI rating) {
        this.rating = rating;
    }

    public int getqUnseenMessagesBuyer() {
        return qUnseenMessagesBuyer;
    }

    public void setqUnseenMessagesBuyer(int qUnseenMessagesBuyer) {
        this.qUnseenMessagesBuyer = qUnseenMessagesBuyer;
    }

    public int getqUnseenMessagesSeller() {
        return qUnseenMessagesSeller;
    }

    public void setqUnseenMessagesSeller(int qUnseenMessagesSeller) {
        this.qUnseenMessagesSeller = qUnseenMessagesSeller;
    }
}

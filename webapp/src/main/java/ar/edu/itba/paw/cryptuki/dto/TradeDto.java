package ar.edu.itba.paw.cryptuki.dto;

import ar.edu.itba.paw.model.Offer;
import ar.edu.itba.paw.model.Trade;
import ar.edu.itba.paw.model.TradeStatus;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;

public class TradeDto {

    private int tradeId;
    private int offerId;
    private String Seller;
    private String Buyer;
    private TradeStatus tradeStatus;
    private double quantity;
    private LocalDateTime lastModified;

    private URI selfUri;
    private URI offerUri;
    private URI sellerUri;
    private URI buyerUri;
    private URI messagesUri;


    public static TradeDto fromTrade(final Trade trade, final UriInfo uriInfo) {

        final TradeDto dto = new TradeDto();

        dto.setTradeId(trade.getTradeId());
        dto.setOfferId(trade.getTradeId());
        dto.setSeller(trade.getOffer().getSeller().getUsername().get());
        dto.setBuyer(trade.getBuyer().getUsername().get());
        dto.setTradeStatus(trade.getStatus());
        dto.setQuantity(trade.getQuantity());
        dto.setLastModified(trade.getLastModified());

        dto.setSelfUri(
                uriInfo.getAbsolutePathBuilder()
                        .replacePath("trades")
                        .path(String.valueOf(dto.getTradeId()))
                        .build()
        );

        dto.setOfferUri(
                uriInfo.getAbsolutePathBuilder()
                        .replacePath("offers")
                        .path(String.valueOf(dto.getOfferId()))
                        .build()
        );

        dto.setSellerUri(
                uriInfo.getAbsolutePathBuilder()
                        .replacePath("users")
                        .path(String.valueOf(dto.getSeller()))
                        .build()
        );

        dto.setBuyerUri(
                uriInfo.getAbsolutePathBuilder()
                        .replacePath("users")
                        .path(String.valueOf(dto.getBuyer()))
                        .build()
        );

        dto.setMessagesUri(
                uriInfo.getAbsolutePathBuilder()
                        .replacePath("trades")
                        .path(String.valueOf(dto.getTradeId()))
                        .path("messages")
                        .build()
        );

        return dto;
    }

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public String getSeller() {
        return Seller;
    }

    public void setSeller(String seller) {
        Seller = seller;
    }

    public String getBuyer() {
        return Buyer;
    }

    public void setBuyer(String buyer) {
        Buyer = buyer;
    }

    public TradeStatus getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(TradeStatus tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public URI getOfferUri() {
        return offerUri;
    }

    public void setOfferUri(URI offerUri) {
        this.offerUri = offerUri;
    }

    public URI getSellerUri() {
        return sellerUri;
    }

    public void setSellerUri(URI sellerUri) {
        this.sellerUri = sellerUri;
    }

    public URI getBuyerUri() {
        return buyerUri;
    }

    public void setBuyerUri(URI buyerUri) {
        this.buyerUri = buyerUri;
    }

    public URI getMessagesUri() {
        return messagesUri;
    }

    public void setMessagesUri(URI messagesUri) {
        this.messagesUri = messagesUri;
    }

    public URI getSelfUri() {
        return selfUri;
    }

    public void setSelfUri(URI selfUri) {
        this.selfUri = selfUri;
    }
}

package ar.edu.itba.paw.cryptuki.dto;

import ar.edu.itba.paw.model.*;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;
import java.net.URL;

public class OfferDto {


    private int offerId;
    private double unitPrice;
    private double minInCrypto;
    private double maxInCrypto;
    private LocalDateTime date;
    private String cryptoCode;
    private String comments;
    private OfferStatus offerStatus;
    private Location location;
    private URI trades;
    private URI seller;
    private URI self;

    public static OfferDto fromOffer(final Offer offer, final UriInfo uriInfo) {

        final OfferDto dto = new OfferDto();

        dto.offerId = offer.getOfferId();
        dto.unitPrice = offer.getUnitPrice();
        dto.minInCrypto = offer.getMinInCrypto();
        dto.maxInCrypto = offer.getMaxInCrypto();
        dto.date = offer.getDate();
        dto.offerStatus = offer.getOfferStatus();
        dto.cryptoCode = offer.getCrypto().getCode();
        dto.comments = offer.getComments();
        dto.location = offer.getLocation();

        UriBuilder builder = uriInfo.getBaseUriBuilder()
                .path("/api/offers")
                .path(String.valueOf(offer.getOfferId()));

        dto.self = builder
                .build();

        dto.seller = uriInfo.getBaseUriBuilder()
                .replacePath("/api/users")
                .path(offer.getSeller().getUsername().get())
                .build();

        dto.trades = builder
                .path("trades")
                .build();

        return dto;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getMinInCrypto() {
        return minInCrypto;
    }

    public void setMinInCrypto(double minInCrypto) {
        this.minInCrypto = minInCrypto;
    }

    public double getMaxInCrypto() {
        return maxInCrypto;
    }

    public void setMaxInCrypto(double maxInCrypto) {
        this.maxInCrypto = maxInCrypto;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getCryptoCode() {
        return cryptoCode;
    }

    public void setCryptoCode(String cryptoCode) {
        this.cryptoCode = cryptoCode;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public OfferStatus getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(OfferStatus offerStatus) {
        this.offerStatus = offerStatus;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public URI getTrades() {
        return trades;
    }

    public void setTrades(URI trades) {
        this.trades = trades;
    }

    public URI getSeller() {
        return seller;
    }

    public void setSeller(URI seller) {
        this.seller = seller;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }
}

package ar.edu.itba.paw.parameterObject;

import ar.edu.itba.paw.model.Location;
import ar.edu.itba.paw.model.Offer;
import ar.edu.itba.paw.model.OfferStatus;
import ar.edu.itba.paw.model.Cryptocurrency;
import ar.edu.itba.paw.persistence.User;

import java.util.*;

public class OfferPO {

    private Integer offerId;
    private Integer sellerId;
    private OfferStatus offerStatus = OfferStatus.APR;
    private Double unitPrice;
    private Double maxInCrypto;
    private Double minInCrypto;
    private String cryptoCode;
    private Location location;
    private String comments;
    private Collection<String> paymentMethods;

    public OfferPO withOfferId(int offerId) {
        this.offerId = offerId;
        return this;
    }

    public OfferPO withSellerId(int sellerId) {
        this.sellerId = sellerId;
        return this;
    }

    public OfferPO withStatus(OfferStatus offerStatus) {
        this.offerStatus = offerStatus;
        return this;
    }

    public OfferPO withUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public OfferPO withMinInCrypto(double minInCrypto) {
        this.minInCrypto = minInCrypto;
        return this;
    }

    public OfferPO withMaxInCrypto(double maxInCrypto) {
        this.maxInCrypto = maxInCrypto;
        return this;
    }

    public OfferPO withCryptoCode(String cryptoCode) {
        this.cryptoCode = cryptoCode;
        return this;
    }

    public OfferPO withLocation(Location location) {
        this.location = location;
        return this;
    }

    public OfferPO withComments(String comments) {
        this.comments = comments;
        return this;
    }

    public OfferPO withPaymentMethod(String pmCode) {
        this.paymentMethods.add(pmCode);
        return this;
    }

    public OptionalInt getOfferId() {
        return offerId != null ? OptionalInt.of(offerId) : OptionalInt.empty();
    }

    public OptionalInt getSellerId() {
        return sellerId != null ? OptionalInt.of(sellerId) : OptionalInt.empty();
    }

    public OfferStatus getOfferStatus() {
        return offerStatus;
    }

    public OptionalDouble getUnitPrice() {
        return OptionalDouble.of(unitPrice);
    }

    public OptionalDouble getMaxInCrypto() {
        return maxInCrypto != null ? OptionalDouble.of(maxInCrypto) : OptionalDouble.empty();
    }

    public OptionalDouble getMinInCrypto() {
        return minInCrypto != null ? OptionalDouble.of(minInCrypto) : OptionalDouble.empty();
    }

    public Optional<String> getCryptoCode() {
        return Optional.ofNullable(cryptoCode);
    }

    public Optional<Location> getLocation() {
        return Optional.ofNullable(location);
    }

    public Optional<String> getComments() {
        return Optional.ofNullable(comments);
    }

    public Collection<String> getPaymentMethods() {
        return Collections.unmodifiableCollection(paymentMethods);
    }

    public Offer.Builder toBuilder(Cryptocurrency crypto, User seller) {

        if (crypto == null || seller == null || !cryptoCode.equals(crypto.getCode()) || seller.getId() != sellerId)
            new IllegalArgumentException("Crypocurrency or seller different than the one on the parameter object");

        return new Offer.Builder(
                getUnitPrice().orElseThrow(()->new IllegalArgumentException()),
                getMinInCrypto().orElseThrow(()->new IllegalArgumentException()),
                getMaxInCrypto().orElseThrow(()->new IllegalArgumentException())
        )
                    .withOfferStatus(getOfferStatus())
                    .withComments(getComments().orElse(""))
                    .withCrypto(crypto)
                    .withLocation(getLocation().orElseThrow(()->new IllegalArgumentException()))
                    .withSeller(seller);
    }
}

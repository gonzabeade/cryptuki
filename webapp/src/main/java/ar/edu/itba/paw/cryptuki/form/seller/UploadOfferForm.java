package ar.edu.itba.paw.cryptuki.form.seller;
import ar.edu.itba.paw.cryptuki.form.annotation.MinLessThanMax;
import ar.edu.itba.paw.model.Location;
import ar.edu.itba.paw.model.parameterObject.OfferPO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

@MinLessThanMax(
        min="minInCrypto",
        max="maxInCrypto"
)
public class UploadOfferForm {

    @NotNull
    private Integer sellerId;

    @NotNull
    @DecimalMin("0.0000001")
    private Double minInCrypto;

    @NotNull
    @DecimalMin("0.00000001")
    private Double maxInCrypto;

    @NotNull
    @DecimalMin("10.0")
    private Double unitPrice;

    @Size(min=2, max=30)
    @NotNull
    private String cryptoCode;

    @Size(min = 1)
    private String[] paymentMethods = new String[] {"cas"};

    @Size(max = 140)
    private String firstChat;

    @Size(min = 1, max = 30)
    @NotNull
    private String location;

    public Double getMinInCrypto() {
        return minInCrypto;
    }
    public Double getMaxInCrypto() {
        return maxInCrypto;
    }
    public Double getUnitPrice() {
        return unitPrice;
    }
    public String getCryptoCode() {
        return cryptoCode;
    }
    public String[] getPaymentMethods() {
        return paymentMethods;
    }
    public String getFirstChat() {
        return firstChat;
    }
    public String getLocation() {
        return location;
    }
    public Integer getSellerId() { return sellerId; }


    public void setMinInCrypto(Double minInCrypto) {
        this.minInCrypto = minInCrypto;
    }
    public void setMaxInCrypto(Double maxInCrypto) {
        this.maxInCrypto = maxInCrypto;
    }
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
    public void setCryptoCode(String cryptoCode) {
        this.cryptoCode = cryptoCode;
    }
    public void setPaymentMethods(String[] paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
    public void setFirstChat(String firstChat) {
        this.firstChat = firstChat;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public OfferPO toOfferParameterObject() {
       return new OfferPO()
               .withCryptoCode(cryptoCode)
               .withLocation(Location.valueOf(location))
               .withSellerId(sellerId)
               .withMaxInCrypto(maxInCrypto)
               .withMinInCrypto(minInCrypto)
               .withFirstChat(firstChat)
               .withUnitPrice(unitPrice);
    }
}


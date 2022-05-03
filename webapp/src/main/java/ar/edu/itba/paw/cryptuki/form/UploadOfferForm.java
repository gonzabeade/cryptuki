package ar.edu.itba.paw.cryptuki.form;

import ar.edu.itba.paw.OfferDigest;
import ar.edu.itba.paw.persistence.Cryptocurrency;
import ar.edu.itba.paw.persistence.Offer;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

public class UploadOfferForm {

    @NotNull
    @DecimalMin("0.0000001")
    private Double minAmount;

    @NotNull
    @DecimalMin("0.00000001")
    private Double maxAmount;

    @NotNull
    @DecimalMin("10.0")
    private Double price;

    @Size(min=2, max=30)
    @NotNull
    private String cryptocurrency;

    @Size(min = 1)
    private String[] paymentMethods;

    public Double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Double minAmount) {
        this.minAmount = minAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCryptocurrency() {
        return cryptocurrency;
    }

    public void setCryptocurrency(String cryptocurrency) {
        this.cryptocurrency = cryptocurrency;
    }

    public String[] getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(String[] paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public OfferDigest toOfferDigest(int sellerId) {
        OfferDigest.Builder builder = new OfferDigest.Builder(sellerId, cryptocurrency, price).withMinQuantity(minAmount).withMaxQuantity(maxAmount);
        for (String pm: paymentMethods)
                builder.withPaymentMethod(pm);
        return builder.build();
    }


}


package ar.edu.itba.paw.cryptuki.form;

import ar.edu.itba.paw.OfferDigest;
import ar.edu.itba.paw.cryptuki.form.annotation.MinLessThanMax;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
@MinLessThanMax(
        min="minAmount",
        max="maxAmount"
)
public class UploadOfferForm {

    @NotNull
    @DecimalMin("0.0000001")
    private Float minAmount;

    @NotNull
    @DecimalMin("0.00000001")
    private Float maxAmount;

    @NotNull
    @DecimalMin("10.0")
    private Float price;

    @Size(min=2, max=30)
    @NotNull
    private String cryptocurrency;

    //TODO SALVA: mirar que hacer con los payment methods, tanto como sacarlos como que valor pasarle
    @Size(min = 1)
    private String[] paymentMethods = new String[] {"cas"};

    @Size(min = 1, max = 140)
    private String message;

    @Size(min = 1, max = 30)
    private String location;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Float getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Float minAmount) {
        this.minAmount = minAmount;
    }

    public void setMaxAmount(Float maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Float getMaxAmount() {
        return maxAmount;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getCryptocurrency() {
        return cryptocurrency;
    }

    public void setCryptocurrency(String cryptocurrency) {
        this.cryptocurrency = cryptocurrency;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String[] getPaymentMethods() {
        return paymentMethods;
    }


    public void setPaymentMethods(String[] paymentMethods) {
        return;
        //this.paymentMethods = paymentMethods;
    }


    public OfferDigest toOfferDigest(int sellerId) {
        OfferDigest.Builder builder = new OfferDigest.Builder(sellerId, cryptocurrency, price)
                .withMinQuantity(minAmount)
                .withMaxQuantity(maxAmount)
                .withComments(message)
                .withLocation(location);

        for (String pm: paymentMethods)
                builder.withPaymentMethod(pm);
        return builder.build();
    }


}


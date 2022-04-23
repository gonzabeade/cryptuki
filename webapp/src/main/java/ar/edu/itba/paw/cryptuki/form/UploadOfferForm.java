package ar.edu.itba.paw.cryptuki.form;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UploadOfferForm {
    @NotNull
    @DecimalMin("0.0")
    private float minAmount;

    @NotNull
    @DecimalMin("0.0")
    private float maxAmount;

    @NotNull
    @DecimalMin("0.0")
    private float price;

    @NotNull
    private String cryptocurrency;

    @NotNull
    private String[] paymentMethods;
    public float getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(float minAmount) {
        this.minAmount = minAmount;
    }

    public void setMaxAmount(float maxAmount) {
        this.maxAmount = maxAmount;
    }

    public float getMaxAmount() {
        return maxAmount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
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
}

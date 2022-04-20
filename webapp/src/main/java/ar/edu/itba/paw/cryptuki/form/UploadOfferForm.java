package ar.edu.itba.paw.cryptuki.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UploadOfferForm {
    @Min(1)
    @NotNull
    private int maxAmount;

    @NotNull
    private float price;

    @NotNull
    private String cryptocurrency;

    @NotNull
    private String[] paymentMethods;

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
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

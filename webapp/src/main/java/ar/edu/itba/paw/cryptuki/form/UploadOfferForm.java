package ar.edu.itba.paw.cryptuki.form;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UploadOfferForm {
    @NotNull
    @Min(0)
    private float minAmount;

    @NotNull
    @Min(0)
    private float maxAmount;

    @NotNull
    @Min(0)
    private float price;

    @NotNull
    @Size(min=2, max=30)
    private String cryptocurrency;

    @NotEmpty
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

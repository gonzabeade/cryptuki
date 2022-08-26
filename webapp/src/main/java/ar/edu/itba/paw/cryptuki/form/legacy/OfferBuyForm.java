package ar.edu.itba.paw.cryptuki.form.legacy;

import ar.edu.itba.paw.cryptuki.annotation.AmountCheck;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AmountCheck(
        offerId="offerId",
        amount="amount"
)
public class OfferBuyForm {
    @Size(min=6, max= 100)
    @Pattern(regexp=".+@.+\\..+")
    private String email;

    @Min(1)
    @NotNull
    private Double amount;

    @NotNull
    private Integer offerId;

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }


    public String getEmail() {
        return email;
    }
    public Integer getOfferId() {
        return offerId;
    }
    public Double getAmount() {
        return amount;
    }
}

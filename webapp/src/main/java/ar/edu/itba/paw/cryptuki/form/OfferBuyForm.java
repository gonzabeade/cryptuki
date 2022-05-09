package ar.edu.itba.paw.cryptuki.form;

import ar.edu.itba.paw.cryptuki.form.annotation.AmountCheck;
import ar.edu.itba.paw.service.digests.BuyDigest;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AmountCheck
public class OfferBuyForm {
    @Size(min=6, max= 100)
    @Pattern(regexp=".+@.+\\..+")
    private String email;

    @Min(1)
    @NotNull
    private Float amount;

    @NotNull
    private Integer offerId;

    @Size(min=1, max= 140)
    @NotEmpty
    private String message;

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }
    public Integer getOfferId() {
        return offerId;
    }
    public String getMessage() {
        return message;
    }
    public Float getAmount() {
        return amount;
    }


    public BuyDigest toDigest() {

        BuyDigest digest = new BuyDigest(
                getOfferId(),
                getEmail(),
                getMessage(),
                getAmount()
        );

        return digest;
    }

}

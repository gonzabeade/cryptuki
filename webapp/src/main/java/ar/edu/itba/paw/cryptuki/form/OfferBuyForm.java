package ar.edu.itba.paw.cryptuki.form;

import ar.edu.itba.paw.service.TradeService;
import ar.edu.itba.paw.service.digests.BuyDigest;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.*;

public class OfferBuyForm {
    @Size(min=6, max= 100)
    @Email()
    @Pattern(regexp=".+@.+\\..+")
    private String email;

    @Min(1)
    @NotNull
    private Double amount;

    @NotNull
    private int offerId;

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
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }
    public int getOfferId() {
        return offerId;
    }
    public String getMessage() {
        return message;
    }
    public Double getAmount() {
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

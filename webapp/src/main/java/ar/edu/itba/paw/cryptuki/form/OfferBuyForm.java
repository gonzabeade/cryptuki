package ar.edu.itba.paw.cryptuki.form;

import ar.edu.itba.paw.service.TradeService;
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
    private Integer amount;

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
    public void setAmount(Integer amount) {
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
    public Integer getAmount() {
        return amount;
    }


    public TradeService.BuyDigest toOfferBuyDigest() {

        TradeService.BuyDigest helper = TradeService.BuyDigest.newInstance(
                getOfferId(),
                getEmail(),
                getMessage(),
                getAmount()
        );

        return helper;
    }

}

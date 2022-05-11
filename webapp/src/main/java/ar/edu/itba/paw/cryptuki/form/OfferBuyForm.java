package ar.edu.itba.paw.cryptuki.form;

import ar.edu.itba.paw.cryptuki.form.annotation.AmountCheck;
import ar.edu.itba.paw.persistence.Trade;
import org.hibernate.validator.constraints.NotEmpty;

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
    private Float amount;

    @NotNull
    private Integer offerId;

    @Size(min=1, max= 140)
    @NotEmpty
    private String message;

    @NotNull
    @Size(min=1, max = 140)
    private String wallet;


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

    public void setWallet(String wallet) {
        this.wallet = wallet;
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

    public String getWallet() {
        return wallet;
    }

    public Trade.Builder toTradeBuilder(String username) {
        return new Trade.Builder(offerId, username)
                .withWallet(wallet)
                .withQuantity(amount);
    }
}

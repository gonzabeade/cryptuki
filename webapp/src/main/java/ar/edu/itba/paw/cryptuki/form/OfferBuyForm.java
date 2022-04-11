package ar.edu.itba.paw.cryptuki.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.*;

public class OfferBuyForm {
    @Size(min=6, max= 100)
    @Email()
    @Pattern(regexp=".+@.+\\..+")
    private String email;

    @NotEmpty
    private int amount;

    @NotNull
    private Integer offerId;

    @NotEmpty
    private String message;

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setAmount(int amount) {
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
    public int getAmount() {
        return amount;
    }

}

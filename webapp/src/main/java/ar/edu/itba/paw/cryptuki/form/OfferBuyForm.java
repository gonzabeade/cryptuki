package ar.edu.itba.paw.cryptuki.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class OfferBuyForm {
    @Size(min=6, max= 100)
    @Email()
    @Pattern(regexp=".+@.+\\..+")
    private String email;

    @NumberFormat
    private int amount;

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

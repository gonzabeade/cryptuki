package ar.edu.itba.paw.cryptuki.form;

public class OfferBuyForm {

    private String email;
    private int amount;
    private Integer offerId;
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

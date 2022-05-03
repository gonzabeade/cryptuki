package ar.edu.itba.paw.cryptuki.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TradeForm {
    @NotNull
    private Double amount;
    @NotNull
    @Size(min = 1)
    private String wallet;
    @NotNull
    private int offerId;

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }
}

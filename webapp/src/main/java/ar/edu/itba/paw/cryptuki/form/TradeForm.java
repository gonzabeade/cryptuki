package ar.edu.itba.paw.cryptuki.form;

import javax.validation.constraints.NotNull;

public class TradeForm {
    private Double amount;
    @NotNull
    private String wallet;

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

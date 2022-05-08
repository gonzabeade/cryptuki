package ar.edu.itba.paw.cryptuki.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class TradeForm {
//    @NotNull
    private Float amount;
    @NotNull
    @Pattern(regexp = "0x[\\dabcdef]{8,10}")
    private String wallet;
//    @NotNull
    private Integer offerId;

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }
}

package ar.edu.itba.paw.cryptuki.form;

import javax.validation.constraints.NotNull;

public class SoldTradeForm {

    @NotNull
    private Integer offerId;
    @NotNull
    private Integer trade;

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public Integer getTrade() {
        return trade;
    }

    public void setTrade(Integer trade) {
        this.trade = trade;
    }
}

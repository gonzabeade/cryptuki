package ar.edu.itba.paw.cryptuki.form;

import javax.validation.constraints.NotNull;

public class StatusTradeForm {
    @NotNull
    private Integer tradeId;

    @NotNull
    private String newStatus;

    public Integer getTradeId() {
        return tradeId;
    }

    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }
}

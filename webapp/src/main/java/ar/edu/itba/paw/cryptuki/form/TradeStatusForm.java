package ar.edu.itba.paw.cryptuki.form;

import ar.edu.itba.paw.cryptuki.annotation.validation.ValueOfEnum;
import ar.edu.itba.paw.model.TradeStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TradeStatusForm {

    @NotNull
    @Size(min = 1, max = 30)
    @ValueOfEnum(enumClass = TradeStatus.class, message = "Incorrect value from newStatus")
    private String newStatus;

    public TradeStatusForm() {

    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }
}
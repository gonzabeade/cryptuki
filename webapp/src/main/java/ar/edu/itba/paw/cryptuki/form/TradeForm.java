package ar.edu.itba.paw.cryptuki.form;

import javax.validation.constraints.Positive;

public class TradeForm {
    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Positive
    private double quantity;
}

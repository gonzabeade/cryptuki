package ar.edu.itba.paw.cryptuki.form;

import javax.validation.constraints.NotNull;

public class RatingForm {
    @NotNull
    private int tradeId;
    @NotNull
    private int rating;

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

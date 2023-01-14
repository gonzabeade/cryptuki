package ar.edu.itba.paw.exception;

import ar.edu.itba.paw.model.TradeStatus;

public class InvalidTradeStatusChange extends RuntimeException {

    private int tradeId;

    public InvalidTradeStatusChange(int tradeId, TradeStatus tradeStatus ) {
        super("The status of the trade can not be set to  "+ tradeStatus);
        this.tradeId = tradeId;
    }

    public int getTradeId() {
        return tradeId;
    }
}

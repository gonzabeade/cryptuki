package ar.edu.itba.paw.exception;

public class NoSuchTradeException extends RuntimeException {

    private int tradeId;

    public NoSuchTradeException(int tradeId) {
        super("Trade with id does not exist. Id: "+tradeId);
        this.tradeId = tradeId;
    }

    public int getTradeId() {
        return tradeId;
    }
}

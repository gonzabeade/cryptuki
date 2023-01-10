package ar.edu.itba.paw.exception;

public class UnmodifiableTradeException extends RuntimeException {

    private int tradeId;

    public UnmodifiableTradeException(int tradeId) {
        super("Trade with id cannot be modified. Id: "+tradeId);
        this.tradeId = tradeId;
    }

    public int getTradeId() {
        return tradeId;
    }
}

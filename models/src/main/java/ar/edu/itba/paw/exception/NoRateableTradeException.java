package ar.edu.itba.paw.exception;

public class NoRateableTradeException extends RuntimeException {

    private int tradeId;
    private static String MESSAGE = "Trade with id can not be rated since it is not SOLD. Id: ";

    public NoRateableTradeException(int tradeId) {
        super(MESSAGE+tradeId);
        this.tradeId = tradeId;
    }

    public NoRateableTradeException(int tradeId, Throwable cause) {
        super(MESSAGE+tradeId, cause);
        this.tradeId = tradeId;
    }

    public int getTradeId() {
        return tradeId;
    }
}

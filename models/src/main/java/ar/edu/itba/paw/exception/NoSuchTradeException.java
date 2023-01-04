package ar.edu.itba.paw.exception;

public class NoSuchTradeException extends RuntimeException {

    private int tradeId;
    private static String MESSAGE = "Trade with id does not exist. Id: ";

    public NoSuchTradeException(int tradeId) {
        super(MESSAGE+tradeId);
        this.tradeId = tradeId;
    }

    public NoSuchTradeException(int tradeId, Throwable cause) {
        super(MESSAGE+tradeId, cause);
        this.tradeId = tradeId;
    }

    public int getTradeId() {
        return tradeId;
    }
}

package ar.edu.itba.paw.exception;

public class NoSuchOfferException extends RuntimeException {

    private int offerId;

    public NoSuchOfferException(int offerId) {
        super("Offer with id does not exist. Id: "+offerId);
        this.offerId = offerId;
    }

    public NoSuchOfferException(int offerId, Throwable cause) {
        super("Offer with id does not exist. Id: "+offerId, cause);
        this.offerId = offerId;
    }

    public int getOfferId() {
        return offerId;
    }
}

package ar.edu.itba.paw.exception;

public class UnmodifiableOfferException extends RuntimeException {

    private int offerId;

    public UnmodifiableOfferException(int offerId) {
        super("Offer with id cannot be modified. Id: "+offerId);
        this.offerId = offerId;
    }

    public int getOfferId() {
        return offerId;
    }
}

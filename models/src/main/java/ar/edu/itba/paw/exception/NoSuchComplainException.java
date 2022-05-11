package ar.edu.itba.paw.exception;

public class NoSuchComplainException extends RuntimeException{

    private int complainId;

    public NoSuchComplainException(int complainId) {
        super("Offer with id does not exist. Id: "+ complainId);
        this.complainId= complainId;
    }

    public int getComplainId() {
        return complainId;
    }
}

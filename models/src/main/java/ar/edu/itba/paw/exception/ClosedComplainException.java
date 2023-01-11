package ar.edu.itba.paw.exception;

public class ClosedComplainException extends RuntimeException{

    private int complainId;

    public ClosedComplainException(int complainId) {
        super("Complain with id : "+ complainId+" is closed");
        this.complainId= complainId;
    }

    public int getComplainId() {
        return complainId;
    }
}

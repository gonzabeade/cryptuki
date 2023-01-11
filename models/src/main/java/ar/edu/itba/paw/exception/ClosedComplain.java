package ar.edu.itba.paw.exception;

public class ClosedComplain extends RuntimeException{

    private int complainId;

    public ClosedComplain(int complainId) {
        super("Complain with id : "+ complainId+" is closed");
        this.complainId= complainId;
    }

    public int getComplainId() {
        return complainId;
    }
}

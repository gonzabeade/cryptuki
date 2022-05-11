package ar.edu.itba.paw.exception;

public class ForbiddenServiceException extends ServiceException {

    public ForbiddenServiceException(String msg) {
        super("Forbidden access to method at persistence: "+msg);
    }
}

package ar.edu.itba.paw.exception;

public abstract class ServiceException extends RuntimeException {

    public ServiceException(String msg) {
        super(msg);
    }
    public ServiceException(String msg, Throwable cause) {
        super(msg, cause);
    }
    public ServiceException(Throwable cause) { super(cause); }
}

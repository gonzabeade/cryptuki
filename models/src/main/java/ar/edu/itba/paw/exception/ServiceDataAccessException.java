package ar.edu.itba.paw.exception;

public class ServiceDataAccessException extends ServiceException {

    public ServiceDataAccessException(Throwable cause) {
        super("Unable to access persistence at service layer.", cause);
    }
}

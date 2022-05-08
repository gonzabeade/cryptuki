package ar.edu.itba.paw.exception;

public class NotAllowedServiceException extends ServiceException {

    private String conflictingOperation ;

    public NotAllowedServiceException(String conflictingOperation, Throwable cause) {
        super("Operation not allowed: "+conflictingOperation, cause);
        this.conflictingOperation = conflictingOperation;
    }

    public String getConflictingOperation() {
        return conflictingOperation;
    }
}

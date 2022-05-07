package ar.edu.itba.paw.exception;

public class UncategorizedPersistenceException extends PersistenceException {

    public UncategorizedPersistenceException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UncategorizedPersistenceException(Throwable cause) {
        super("Unable to process persistence request.", cause);
    }
}

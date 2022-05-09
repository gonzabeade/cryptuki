package ar.edu.itba.paw.exception;

public class UncategorizedPersistenceException extends PersistenceException {

    public UncategorizedPersistenceException(Throwable cause) {
        super("Unable to process persistence request.", cause);
    }
}

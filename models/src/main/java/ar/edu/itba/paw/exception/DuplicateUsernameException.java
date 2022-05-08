package ar.edu.itba.paw.exception;

public class DuplicateUsernameException extends PersistenceException {

    private String conflictingUsername;

    public DuplicateUsernameException(String conflictingUsername, Throwable cause) {
        super("Duplicate username supplied to persistence layer. Conflicting username: "+conflictingUsername, cause);
        this.conflictingUsername = conflictingUsername;
    }

    public String getConflictingUsername() {
        return this.conflictingUsername;
    }
}

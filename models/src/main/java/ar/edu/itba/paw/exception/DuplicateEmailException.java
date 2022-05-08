package ar.edu.itba.paw.exception;

public class DuplicateEmailException extends PersistenceException {

    private String conflictingEmail;

    public DuplicateEmailException(String conflictingEmail, Throwable cause) {
        super("Duplicate email supplied to persistence layer. Conflicting email: "+conflictingEmail, cause);
        this.conflictingEmail = conflictingEmail;
    }

    public String getConflictingEmail() {
        return this.conflictingEmail;
    }
}

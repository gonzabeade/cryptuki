package ar.edu.itba.paw.exception;

import com.sun.istack.internal.Nullable;

public abstract class PersistenceException extends RuntimeException {

    public PersistenceException(String msg) {
        super(msg);
    }
    public PersistenceException(String msg, Throwable cause) {
        super(msg, cause);
    }
    public PersistenceException(Throwable cause) { super(cause); }
}

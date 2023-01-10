package ar.edu.itba.paw.cryptuki.mapper.Conflict;

import ar.edu.itba.paw.exception.UserAlreadyVerifiedException;

import javax.ws.rs.ext.Provider;

@Provider
public class UserAlreadyVerifiedExceptionMapper extends ConflictMapper<UserAlreadyVerifiedException> {

    private static final String MESSAGE = "User with that username has already been verified. Its current status is %s";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}

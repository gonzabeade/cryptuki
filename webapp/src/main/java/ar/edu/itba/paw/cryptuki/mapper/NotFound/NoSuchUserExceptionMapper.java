package ar.edu.itba.paw.cryptuki.mapper.NotFound;

import ar.edu.itba.paw.exception.NoSuchUserException;

import javax.ws.rs.ext.Provider;

@Provider
public class NoSuchUserExceptionMapper extends NotFoundMapper<NoSuchUserException> {

    private static final String MESSAGE = "User with username: '%s' does not exist";

    @Override
    public String getMessage() {
        return String.format(MESSAGE,this.exception.getUsername());
    }
}

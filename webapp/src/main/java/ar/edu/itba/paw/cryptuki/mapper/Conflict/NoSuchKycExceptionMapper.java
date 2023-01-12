package ar.edu.itba.paw.cryptuki.mapper.Conflict;

import ar.edu.itba.paw.exception.NoSuchKycException;

import javax.ws.rs.ext.Provider;

@Provider
public class NoSuchKycExceptionMapper extends ConflictMapper<NoSuchKycException> {

    private static final String MESSAGE = "User with that username does not have a pending KYC request %d";
    @Override
    public String getMessage() {
        return MESSAGE;
    }
}

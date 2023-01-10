package ar.edu.itba.paw.cryptuki.mapper.Conflict;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;
import ar.edu.itba.paw.exception.AlreadyHasKycRequestException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AlreadyHasKycRequestExceptionMapper extends ConflictMapper<AlreadyHasKycRequestException> {

    private static final String MESSAGE = "User with that username already has an active KYC filing.";
    @Override
    public String getMessage() {
        return MESSAGE;
    }
}

package ar.edu.itba.paw.cryptuki.mapper;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;
import ar.edu.itba.paw.exception.AlreadyHasKycRequestException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AlreadyHasKycRequestExceptionMapper implements ExceptionMapper<AlreadyHasKycRequestException> {

    private static final String MESSAGE = "User %s already has an active KYC filing.";

    @Override
    public Response toResponse(AlreadyHasKycRequestException e) {
        GenericErrorDto dto = GenericErrorDto.fromMessage(String.format(MESSAGE, e.getUsername()));
        return Response.status(Response.Status.CONFLICT).entity(dto).build();
    }
}

package ar.edu.itba.paw.cryptuki.mapper;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;
import ar.edu.itba.paw.exception.NoSuchKycException;
import ar.edu.itba.paw.exception.NoSuchOfferException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoSuchKycExceptionMapper implements ExceptionMapper<NoSuchKycException> {

    private static final String MESSAGE = "User %s does not have a pending KYC request %d";

    @Override
    public Response toResponse(NoSuchKycException e) {
        GenericErrorDto dto = GenericErrorDto.fromMessage(String.format(MESSAGE, e.getUsername(), e.getKycId()));
        return Response.status(Response.Status.CONFLICT).entity(dto).build();
    }
}

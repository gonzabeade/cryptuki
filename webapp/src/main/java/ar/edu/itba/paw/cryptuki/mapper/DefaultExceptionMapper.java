package ar.edu.itba.paw.cryptuki.mapper;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;
import ar.edu.itba.paw.exception.NoSuchCryptocurrencyException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

//TODO DESCOMENTAR @Provider
public class DefaultExceptionMapper implements ExceptionMapper<Exception> {

    private static final String MESSAGE = "Internal server error - Cause unknown";

    @Override
    public Response toResponse(Exception e) {
        GenericErrorDto dto = GenericErrorDto.fromMessage(MESSAGE);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(dto).build();
    }
}

package ar.edu.itba.paw.cryptuki.mapper;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;
import ar.edu.itba.paw.exception.NoSuchCryptocurrencyException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoSuchCryptocurrencyExceptionMapper implements ExceptionMapper<NoSuchCryptocurrencyException> {

    private static final String MESSAGE = "Cryptocurrency with code '%s' does not exist";

    @Override
    public Response toResponse(NoSuchCryptocurrencyException e) {
        GenericErrorDto dto = GenericErrorDto.fromMessage(String.format(MESSAGE, e.getCode()));
        return Response.status(Response.Status.NOT_FOUND).entity(dto).build();
    }
}

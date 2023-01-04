package ar.edu.itba.paw.cryptuki.mapper;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;
import ar.edu.itba.paw.exception.NoSuchTradeException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoSuchTradeExceptionMapper implements ExceptionMapper<NoSuchTradeException> {

    private static final String MESSAGE = "Trade with id %s does not exist";

    @Override
    public Response toResponse(NoSuchTradeException e) {
        GenericErrorDto dto = GenericErrorDto.fromMessage(String.format(MESSAGE, e.getTradeId()));
        return Response.status(Response.Status.NOT_FOUND).entity(dto).build();
    }
}

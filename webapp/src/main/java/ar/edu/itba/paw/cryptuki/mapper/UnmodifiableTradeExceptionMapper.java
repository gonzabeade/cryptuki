package ar.edu.itba.paw.cryptuki.mapper;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;
import ar.edu.itba.paw.exception.UnmodifiableOfferException;
import ar.edu.itba.paw.exception.UnmodifiableTradeException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnmodifiableTradeExceptionMapper implements ExceptionMapper<UnmodifiableTradeException> {

    @Override
    public Response toResponse(UnmodifiableTradeException e) {
        GenericErrorDto dto = GenericErrorDto.fromMessage(e.getMessage());
        return Response.status(Response.Status.CONFLICT).entity(dto).build();
    }
}

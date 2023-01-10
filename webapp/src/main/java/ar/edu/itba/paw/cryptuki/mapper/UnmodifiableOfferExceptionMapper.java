package ar.edu.itba.paw.cryptuki.mapper;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;
import ar.edu.itba.paw.exception.UnmodifiableOfferException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnmodifiableOfferExceptionMapper implements ExceptionMapper<UnmodifiableOfferException> {
    private static String MESSAGE = "Offer cannot be modified as it has associated trades.";
    @Override
    public Response toResponse(UnmodifiableOfferException e) {
        GenericErrorDto dto = GenericErrorDto.fromMessage(MESSAGE);
        return Response.status(Response.Status.CONFLICT).entity(dto).build();
    }
}

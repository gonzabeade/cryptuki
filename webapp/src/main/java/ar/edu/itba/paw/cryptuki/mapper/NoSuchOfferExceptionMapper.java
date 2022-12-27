package ar.edu.itba.paw.cryptuki.mapper;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;
import ar.edu.itba.paw.exception.NoSuchOfferException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoSuchOfferExceptionMapper implements ExceptionMapper<NoSuchOfferException> {

    private static final String MESSAGE = "Offer with such id does not exist";

    @Override
    public Response toResponse(NoSuchOfferException e) {
        GenericErrorDto dto = GenericErrorDto.fromMessage(MESSAGE);
        return Response.status(Response.Status.NOT_FOUND).entity(dto).build();
    }
}

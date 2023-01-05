package ar.edu.itba.paw.cryptuki.mapper;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;
import ar.edu.itba.paw.exception.NoSuchComplainException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoSuchComplainExceptionMapper implements ExceptionMapper<NoSuchComplainException> {

    private static final String MESSAGE = "Complain with id '%d' does not exist";


    @Override
    public Response toResponse(NoSuchComplainException e) {
        GenericErrorDto dto = GenericErrorDto.fromMessage(String.format(MESSAGE, e.getComplainId()));
        return Response.status(Response.Status.NOT_FOUND).entity(dto).build();
    }
}

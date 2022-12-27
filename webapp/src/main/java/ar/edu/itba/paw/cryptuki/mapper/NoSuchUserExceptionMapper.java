package ar.edu.itba.paw.cryptuki.mapper;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;
import ar.edu.itba.paw.exception.NoSuchUserException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoSuchUserExceptionMapper implements ExceptionMapper<NoSuchUserException> {

    private static final String MESSAGE = "User with username '%s' does not exist";

    @Override
    public Response toResponse(NoSuchUserException e) {
        GenericErrorDto dto = GenericErrorDto.fromMessage(String.format(MESSAGE, e.getUsername()));
        return Response.status(Response.Status.NOT_FOUND).entity(dto).build();
    }
}

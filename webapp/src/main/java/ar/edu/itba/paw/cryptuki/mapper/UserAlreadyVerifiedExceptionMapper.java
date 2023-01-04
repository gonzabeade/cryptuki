package ar.edu.itba.paw.cryptuki.mapper;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.exception.UserAlreadyVerifiedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserAlreadyVerifiedExceptionMapper implements ExceptionMapper<UserAlreadyVerifiedException> {

    private static final String MESSAGE = "User with username '%s' has already been verified. Its current status is %s";

    @Override
    public Response toResponse(UserAlreadyVerifiedException e) {
        GenericErrorDto dto = GenericErrorDto.fromMessage(String.format(MESSAGE, e.getUsername(), e.getUserStatus()));
        return Response.status(Response.Status.CONFLICT).entity(dto).build();
    }
}

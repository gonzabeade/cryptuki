package ar.edu.itba.paw.cryptuki.mapper;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;
import org.springframework.security.core.AuthenticationException;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnauthorizedExceptionMapper implements ExceptionMapper<AuthenticationException> {

    private static String MESSAGE ="Unauthorized action";
    @Override
    public Response toResponse(AuthenticationException e) {
        GenericErrorDto dto = GenericErrorDto.fromMessage(MESSAGE);
        return Response.status(Response.Status.UNAUTHORIZED).entity(dto).build();
    }

}

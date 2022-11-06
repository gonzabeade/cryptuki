package ar.edu.itba.paw.cryptuki.mapper;

import ar.edu.itba.paw.cryptuki.dto.AuthorizationErrorDto;
import org.springframework.security.access.AccessDeniedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class AccessExceptionMapper implements ExceptionMapper<AccessDeniedException> {

    @Override
    public Response toResponse(AccessDeniedException e) {
        return Response.status(Response.Status.FORBIDDEN).entity(AuthorizationErrorDto.fromAccessDeniedException(e)).build();
    }
}

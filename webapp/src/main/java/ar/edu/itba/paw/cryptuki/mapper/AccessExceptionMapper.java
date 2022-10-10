package ar.edu.itba.paw.cryptuki.mapper;

import ar.edu.itba.paw.cryptuki.dto.AuthorizationErrorDto;
import ar.edu.itba.paw.cryptuki.dto.ValidationErrorDto;
import org.springframework.expression.AccessException;
import org.springframework.security.access.AccessDeniedException;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.stream.Collectors;

@Provider
public class AccessExceptionMapper implements ExceptionMapper<AccessDeniedException> {

    @Override
    public Response toResponse(AccessDeniedException e) {
        return Response.status(Response.Status.UNAUTHORIZED).entity(AuthorizationErrorDto.fromAccessException(e)).build();
    }
}

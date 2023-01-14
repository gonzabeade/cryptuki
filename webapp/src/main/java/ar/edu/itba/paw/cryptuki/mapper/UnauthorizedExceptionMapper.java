package ar.edu.itba.paw.cryptuki.mapper;

import org.springframework.security.core.AuthenticationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class UnauthorizedExceptionMapper extends GenericMapper<AuthenticationException> {

    private static final String MESSAGE ="Unauthorized action";

    @Override
    public String getMessage() {
        return MESSAGE;
    }

    @Override
    public Response.Status getStatus() {
        return Response.Status.UNAUTHORIZED;
    }
}

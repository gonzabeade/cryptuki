package ar.edu.itba.paw.cryptuki.mapper;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;

import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotAcceptableExceptionMapper extends GenericMapper<NotAcceptableException> {

    private static final String MESSAGE = "Client media type requested not supported";

    @Override
    public String getMessage() {
        return MESSAGE;
    }

    @Override
    public Response.Status getStatus() {
        return Response.Status.NOT_ACCEPTABLE;
    }
}

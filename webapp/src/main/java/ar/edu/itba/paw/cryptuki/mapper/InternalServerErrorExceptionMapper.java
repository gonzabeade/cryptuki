package ar.edu.itba.paw.cryptuki.mapper;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class InternalServerErrorExceptionMapper extends GenericMapper<InternalServerErrorException> {

    private static final String MESSAGE = "Internal server error - Cause unknown";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
    @Override
    public Response.Status getStatus() {
        return Response.Status.INTERNAL_SERVER_ERROR;
    }

}

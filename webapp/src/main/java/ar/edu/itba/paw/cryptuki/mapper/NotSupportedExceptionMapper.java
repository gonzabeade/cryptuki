package ar.edu.itba.paw.cryptuki.mapper;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;

import javax.ws.rs.NotSupportedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotSupportedExceptionMapper implements ExceptionMapper<NotSupportedException> {

    private static final String MESSAGE = "Unsupported media type";

    @Override
    public Response toResponse(NotSupportedException e) {
        GenericErrorDto dto = GenericErrorDto.fromMessage(MESSAGE);
        return Response.status(Response.Status.UNSUPPORTED_MEDIA_TYPE).entity(dto).build();
    }
}

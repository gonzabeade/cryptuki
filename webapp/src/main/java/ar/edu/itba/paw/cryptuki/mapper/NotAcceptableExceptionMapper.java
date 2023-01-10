package ar.edu.itba.paw.cryptuki.mapper;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;

import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotAcceptableExceptionMapper implements ExceptionMapper<NotAcceptableException> {

    private static final String MESSAGE = "Client media type requested not supported";

    @Override
    public Response toResponse(NotAcceptableException e) {
        GenericErrorDto dto = GenericErrorDto.fromMessage(MESSAGE);
        return Response
                .status(Response.Status.NOT_ACCEPTABLE)
                .entity(dto)
                .build();
    }
}

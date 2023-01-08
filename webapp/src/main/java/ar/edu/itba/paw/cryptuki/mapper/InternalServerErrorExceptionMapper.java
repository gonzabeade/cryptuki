package ar.edu.itba.paw.cryptuki.mapper;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InternalServerErrorExceptionMapper implements ExceptionMapper<InternalServerErrorException> {

    private static final String MESSAGE = "Internal server error - Cause unknown";

    @Override
    public Response toResponse(InternalServerErrorException e) {
        GenericErrorDto dto = GenericErrorDto.fromMessage(MESSAGE);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(dto).build();
    }
}

package ar.edu.itba.paw.cryptuki.mapper;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;
import ar.edu.itba.paw.exception.AlreadyHasKycRequestException;
import ar.edu.itba.paw.exception.BadMultipartFormatException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BadMultipartFormatExceptionMapper implements ExceptionMapper<BadMultipartFormatException> {

    private static final String MESSAGE = "Expected a multipart/form-data with the following sections: %s";

    @Override
    public Response toResponse(BadMultipartFormatException e) {
        GenericErrorDto dto = GenericErrorDto.fromMessage(String.format(MESSAGE, e.getDescriptors()));
        return Response.status(Response.Status.UNSUPPORTED_MEDIA_TYPE).entity(dto).build();
    }
}

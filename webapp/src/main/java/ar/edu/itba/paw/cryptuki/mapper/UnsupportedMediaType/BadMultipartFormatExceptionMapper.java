package ar.edu.itba.paw.cryptuki.mapper.UnsupportedMediaType;

import ar.edu.itba.paw.cryptuki.dto.MultipartErrorDto;
import ar.edu.itba.paw.cryptuki.dto.MultipartPartErrorDto;
import ar.edu.itba.paw.cryptuki.dto.ValidationErrorDto;
import ar.edu.itba.paw.cryptuki.exception.BadMultipartFormatException;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Provider
public class BadMultipartFormatExceptionMapper implements ExceptionMapper<BadMultipartFormatException> {

    private static String MESSAGE = "Expected a multipart/form-data with particular structure";
    @Override
    public Response toResponse(BadMultipartFormatException exception) {
        MultipartErrorDto dto = MultipartErrorDto.fromBadMultipartFormatException(exception);
        dto.setMessage(MESSAGE);
        return Response.status(Response.Status.UNSUPPORTED_MEDIA_TYPE).entity(dto).build();
    }




}

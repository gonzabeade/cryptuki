package ar.edu.itba.paw.cryptuki.mapper.BadRequest;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;
import ar.edu.itba.paw.cryptuki.dto.OfferDto;
import ar.edu.itba.paw.cryptuki.dto.ValidationErrorDto;
import ar.edu.itba.paw.cryptuki.mapper.NotFound.NotFoundMapper;

import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<ValidationErrorDto> validationErrorDtoCollection = exception.getConstraintViolations().stream().map(v -> ValidationErrorDto.fromValidationException(v)).collect(Collectors.toList());
        return Response.status(Response.Status.BAD_REQUEST).entity(new GenericEntity<Collection<ValidationErrorDto>>(validationErrorDtoCollection) {}).build();
    }




}

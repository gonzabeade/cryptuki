package ar.edu.itba.paw.cryptuki.mapper.BadRequest;

import ar.edu.itba.paw.cryptuki.dto.ValidationErrorDto;
import ar.edu.itba.paw.cryptuki.mapper.NotFound.NotFoundMapper;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.stream.Collectors;

@Provider
public class ValidationExceptionMapper extends NotFoundMapper<ConstraintViolationException> {
    @Override
    public String getMessage() {
        return this.exceptionMessage;
    }

}

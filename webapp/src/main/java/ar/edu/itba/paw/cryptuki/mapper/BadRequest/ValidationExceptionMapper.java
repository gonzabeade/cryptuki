package ar.edu.itba.paw.cryptuki.mapper.BadRequest;

import ar.edu.itba.paw.cryptuki.mapper.NotFound.NotFoundMapper;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper extends NotFoundMapper<ConstraintViolationException> {
    @Override
    public String getMessage() {
        return this.exceptionMessage;
    }

}

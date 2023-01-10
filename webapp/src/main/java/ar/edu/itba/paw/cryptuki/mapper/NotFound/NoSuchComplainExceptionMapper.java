package ar.edu.itba.paw.cryptuki.mapper.NotFound;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;
import ar.edu.itba.paw.exception.NoSuchComplainException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoSuchComplainExceptionMapper extends NotFoundMapper<NoSuchComplainException> {

    private static final String MESSAGE = "Complain with such id does not exist";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}

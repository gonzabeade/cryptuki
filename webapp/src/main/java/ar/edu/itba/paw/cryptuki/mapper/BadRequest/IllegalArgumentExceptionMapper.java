package ar.edu.itba.paw.cryptuki.mapper.BadRequest;

import ar.edu.itba.paw.cryptuki.dto.IllegalArgumentErrorDto;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionMapper extends BadRequestMapper<IllegalArgumentException> {

    @Override
    public String getMessage() {
        return this.exceptionMessage;
    }
}

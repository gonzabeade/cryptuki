package ar.edu.itba.paw.cryptuki.mapper.BadRequest;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper extends BadRequestMapper<BadRequestException> {

    @Override
    public String getMessage() {
        return this.exceptionMessage;
    }
}

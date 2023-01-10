package ar.edu.itba.paw.cryptuki.mapper.Forbidden;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ForbiddenExceptionMapper extends ForbiddenMapper<ForbiddenException> {

    @Override
    public String getMessage() {
        return this.exceptionMessage;
    }
}

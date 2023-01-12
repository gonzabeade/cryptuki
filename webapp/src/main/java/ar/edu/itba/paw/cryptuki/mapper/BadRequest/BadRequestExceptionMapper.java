package ar.edu.itba.paw.cryptuki.mapper.BadRequest;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper extends BadRequestMapper<BadRequestException> {

    @Override
    public String getMessage() {
        return this.exceptionMessage;
    }
}

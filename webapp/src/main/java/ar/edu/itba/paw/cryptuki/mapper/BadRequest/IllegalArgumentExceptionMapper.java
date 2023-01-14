package ar.edu.itba.paw.cryptuki.mapper.BadRequest;

import javax.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionMapper extends BadRequestMapper<IllegalArgumentException> {

    @Override
    public String getMessage() {
        return this.exceptionMessage;
    }
}

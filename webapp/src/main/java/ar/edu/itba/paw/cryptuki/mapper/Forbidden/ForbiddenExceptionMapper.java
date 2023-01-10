package ar.edu.itba.paw.cryptuki.mapper.Forbidden;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.ext.Provider;

@Provider
public class ForbiddenExceptionMapper extends ForbiddenMapper<ForbiddenException> {

    @Override
    public String getMessage() {
        return this.exceptionMessage;
    }
}

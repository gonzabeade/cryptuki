package ar.edu.itba.paw.cryptuki.mapper.NotFound;


import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ResourceNotFoundExceptionMapper extends NotFoundMapper<NotFoundException> {

    private static final String MESSAGE = "Resource Not Found";

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}

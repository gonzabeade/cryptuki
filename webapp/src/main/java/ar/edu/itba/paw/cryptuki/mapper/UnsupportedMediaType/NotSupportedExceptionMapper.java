package ar.edu.itba.paw.cryptuki.mapper.UnsupportedMediaType;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;

import javax.ws.rs.NotSupportedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotSupportedExceptionMapper extends UnsupportedMediaType<NotSupportedException> {

    private static final String MESSAGE = "Unsupported media type";

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}

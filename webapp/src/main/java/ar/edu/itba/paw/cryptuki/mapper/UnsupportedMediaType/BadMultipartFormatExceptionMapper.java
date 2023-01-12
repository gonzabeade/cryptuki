package ar.edu.itba.paw.cryptuki.mapper.UnsupportedMediaType;

import ar.edu.itba.paw.exception.BadMultipartFormatException;

import javax.ws.rs.ext.Provider;

@Provider
public class BadMultipartFormatExceptionMapper extends UnsupportedMediaType<BadMultipartFormatException> {

    private static final String MESSAGE = "Expected a multipart/form-data with the following sections: %s";

    @Override
    public String getMessage() {
        return String.format(MESSAGE, this.exception.getDescriptors());
    }

}

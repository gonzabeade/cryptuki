package ar.edu.itba.paw.cryptuki.mapper;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public abstract class GenericMapper<T extends Throwable> implements ExceptionMapper<T> {

    protected T exception;
    protected String exceptionMessage;

    @Override
    public Response toResponse(T t) {
        this.exception = t;
        this.exceptionMessage = t.getMessage();
        GenericErrorDto dto = GenericErrorDto.fromMessage(getMessage());
        return Response.status(getStatus()).entity(dto).build();
    }

    public abstract String getMessage();
    public abstract Response.Status getStatus();

}

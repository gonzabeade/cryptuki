package ar.edu.itba.paw.cryptuki.mapper.BadRequest;

import ar.edu.itba.paw.cryptuki.mapper.GenericMapper;

import javax.ws.rs.core.Response;

public abstract class BadRequestMapper<T extends Throwable> extends GenericMapper<T> {
    @Override
    public Response.Status getStatus() {
        return Response.Status.BAD_REQUEST;
    }
}

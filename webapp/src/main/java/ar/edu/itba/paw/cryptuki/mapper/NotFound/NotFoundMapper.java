package ar.edu.itba.paw.cryptuki.mapper.NotFound;

import ar.edu.itba.paw.cryptuki.mapper.GenericMapper;

import javax.ws.rs.core.Response;

public abstract class NotFoundMapper<T extends Throwable> extends GenericMapper<T> {
    @Override
    public Response.Status getStatus() {
        return Response.Status.NOT_FOUND;
    }
}

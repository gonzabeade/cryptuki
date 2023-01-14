package ar.edu.itba.paw.cryptuki.mapper.Conflict;

import ar.edu.itba.paw.cryptuki.mapper.GenericMapper;

import javax.ws.rs.core.Response;

public abstract class ConflictMapper<T extends Throwable> extends GenericMapper<T> {
    @Override
    public Response.Status getStatus() {
        return Response.Status.CONFLICT;
    }
}

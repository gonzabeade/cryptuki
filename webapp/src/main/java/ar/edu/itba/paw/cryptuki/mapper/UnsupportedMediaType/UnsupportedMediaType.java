package ar.edu.itba.paw.cryptuki.mapper.UnsupportedMediaType;

import ar.edu.itba.paw.cryptuki.mapper.GenericMapper;

import javax.ws.rs.core.Response;

public abstract class UnsupportedMediaType<T extends Throwable> extends GenericMapper<T> {
    @Override
    public Response.Status getStatus() {
        return Response.Status.UNSUPPORTED_MEDIA_TYPE;
    }
}

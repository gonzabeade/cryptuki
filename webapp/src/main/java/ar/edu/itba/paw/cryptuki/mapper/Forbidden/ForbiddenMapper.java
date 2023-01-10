package ar.edu.itba.paw.cryptuki.mapper.Forbidden;

import ar.edu.itba.paw.cryptuki.mapper.GenericMapper;
import org.springframework.security.access.AccessDeniedException;

import javax.ws.rs.core.Response;

public abstract class ForbiddenMapper<T extends Throwable> extends GenericMapper<T> {

    public Response.Status getStatus(){
        return Response.Status.FORBIDDEN;
    }

}

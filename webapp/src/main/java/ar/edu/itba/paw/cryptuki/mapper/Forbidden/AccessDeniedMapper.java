package ar.edu.itba.paw.cryptuki.mapper.Forbidden;

import ar.edu.itba.paw.cryptuki.mapper.GenericMapper;
import org.springframework.security.access.AccessDeniedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class AccessDeniedMapper extends ForbiddenMapper<AccessDeniedException> {
    @Override
    public String getMessage() {
        return this.exceptionMessage;
    }
}

package ar.edu.itba.paw.cryptuki.mapper.Conflict;

import ar.edu.itba.paw.exception.ClosedComplainException;

import javax.ws.rs.ext.Provider;

@Provider
public class ClosedComplainMapper extends ConflictMapper<ClosedComplainException>{
    @Override
    public String getMessage() {
        return this.exceptionMessage;
    }
}

package ar.edu.itba.paw.cryptuki.mapper.Conflict;

import ar.edu.itba.paw.exception.InvalidTradeStatusChange;

import javax.ws.rs.ext.Provider;

@Provider
public class InvalidTradeStatusChangeMapper extends ConflictMapper<InvalidTradeStatusChange> {
    @Override
    public String getMessage() {
        return this.exceptionMessage;
    }
}

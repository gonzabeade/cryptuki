package ar.edu.itba.paw.cryptuki.mapper.Conflict;

import ar.edu.itba.paw.exception.UnmodifiableTradeException;

import javax.ws.rs.ext.Provider;

@Provider
public class UnmodifiableTradeExceptionMapper extends ConflictMapper<UnmodifiableTradeException> {
    @Override
    public String getMessage() {
        return this.exceptionMessage;
    }
}

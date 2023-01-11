package ar.edu.itba.paw.cryptuki.mapper.Conflict;

import ar.edu.itba.paw.exception.NoRateableTradeException;

import javax.ws.rs.ext.Provider;

@Provider
public class NoRateableTradeExceptionMapper extends ConflictMapper<NoRateableTradeException> {

    @Override
    public String getMessage() {
        return this.exceptionMessage;
    }
}

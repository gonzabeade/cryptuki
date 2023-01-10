package ar.edu.itba.paw.cryptuki.mapper.NotFound;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;
import ar.edu.itba.paw.exception.NoSuchTradeException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoSuchTradeExceptionMapper extends NotFoundMapper<NoSuchTradeException> {

    private static final String MESSAGE = "Trade with id: '%d' does not exist";

    @Override
    public String getMessage() {
        return String.format(MESSAGE,this.exception.getTradeId());
    }
}

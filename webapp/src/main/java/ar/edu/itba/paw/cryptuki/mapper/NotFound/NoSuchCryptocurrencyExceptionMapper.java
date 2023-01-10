package ar.edu.itba.paw.cryptuki.mapper.NotFound;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;
import ar.edu.itba.paw.exception.NoSuchCryptocurrencyException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoSuchCryptocurrencyExceptionMapper extends NotFoundMapper<NoSuchCryptocurrencyException> {

    private static final String MESSAGE = "Cryptocurrency with code '%s' does not exist";

    @Override
    public String getMessage() {
        return String.format(MESSAGE, this.exception.getCode());
    }

}

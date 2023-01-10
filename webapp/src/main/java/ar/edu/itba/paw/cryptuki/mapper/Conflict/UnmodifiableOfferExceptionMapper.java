package ar.edu.itba.paw.cryptuki.mapper.Conflict;

import ar.edu.itba.paw.cryptuki.dto.GenericErrorDto;
import ar.edu.itba.paw.exception.UnmodifiableOfferException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnmodifiableOfferExceptionMapper extends ConflictMapper<UnmodifiableOfferException> {
    private static String MESSAGE = "Offer cannot be modified as it has associated trades.";
    @Override
    public String getMessage() {
        return MESSAGE;
    }
}

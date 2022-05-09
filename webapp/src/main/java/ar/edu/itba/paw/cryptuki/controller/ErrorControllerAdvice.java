package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.exception.ServiceDataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
class ErrorControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorControllerAdvice.class);

    @ExceptionHandler(ServiceDataAccessException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView serviceDataAccess(ServiceDataAccessException sdae) {
        LOGGER.warn("Captured ServiceDataAccessException", sdae);
        return new ModelAndView("redirect://500");
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ModelAndView nullPointer(NullPointerException npe) {
        LOGGER.warn("Captured NullPointerException", npe);
        return new ModelAndView("redirect://400");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ModelAndView illegalArgument(IllegalArgumentException iae) {
        LOGGER.warn("Captured IllegalArgumentException", iae);
        return new ModelAndView("redirect://400");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView notFound(NoHandlerFoundException nfhe) {
        LOGGER.warn("Captured NoHandlerFoundException", nfhe);
        return new ModelAndView("redirect://500");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ModelAndView notFound(AccessDeniedException ade) {
        LOGGER.warn("Captured AccessDeniedException", ade);
        return new ModelAndView("redirect://403");
    }
}
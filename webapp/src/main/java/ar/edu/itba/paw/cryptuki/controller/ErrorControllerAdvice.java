package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.exception.NoSuchOfferException;
import ar.edu.itba.paw.exception.NoSuchTradeException;
import ar.edu.itba.paw.exception.NoSuchUserException;
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
import org.springframework.web.servlet.view.RedirectView;

import java.util.Locale;

@ControllerAdvice
class ErrorControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorControllerAdvice.class);

    @ExceptionHandler(ServiceDataAccessException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView serviceDataAccess(ServiceDataAccessException sdae) {
        LOGGER.warn("Captured ServiceDataAccessException", sdae);
        return new ModelAndView("forward:/500");
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ModelAndView nullPointer(NullPointerException npe) {
        LOGGER.warn("Captured NullPointerException", npe);
        return new ModelAndView("forward:/400");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ModelAndView illegalArgument(IllegalArgumentException iae) {
        LOGGER.warn("Captured IllegalArgumentException", iae);
        return new ModelAndView("forward:/400");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView notFound(NoHandlerFoundException nfhe) {
        LOGGER.warn("Captured NoHandlerFoundException", nfhe);
        return new ModelAndView("forward:/500");
    }

    @ExceptionHandler(NoSuchOfferException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView notFound(NoSuchOfferException nsoe) {
        LOGGER.debug("Captured NoSuchOfferException", nsoe);
        return new ModelAndView("forward:/404");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public ModelAndView accessDenied(AccessDeniedException ade) {
        LOGGER.warn("Captured AccessDeniedException", ade);
        return new ModelAndView("forward:/403");
    }

    @ExceptionHandler(NoSuchTradeException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView noSuchTrade(NoSuchTradeException nste) {
        LOGGER.warn("Captured NoSuchTradeException", nste);
        return new ModelAndView("forward:/404");
    }

    @ExceptionHandler(NoSuchUserException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ModelAndView noSuchUser(NoSuchUserException nsue) {
        LOGGER.warn("Captured NoSuchUserException", nsue);
        return new ModelAndView("forward:/404");
    }
}
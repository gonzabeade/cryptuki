package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.exception.ServiceDataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
class ErrorControllerAdvice {


    // TODO!! Add logger!
//    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorControllerAdvice.class);

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ServiceDataAccessException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView serviceDataAccess() {
        return new ModelAndView("forward://500");
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ModelAndView nullPointer() {
        return new ModelAndView("forward://400");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ModelAndView illegalArgument() {
        return new ModelAndView("forward://400");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView notFound() {
        return new ModelAndView("forward://500");
    }

}
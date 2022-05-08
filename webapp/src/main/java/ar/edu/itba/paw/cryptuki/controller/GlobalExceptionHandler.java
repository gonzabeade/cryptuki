package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.exception.ServiceDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
class GlobalExceptionHandler {

    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR, reason="Server could not fulfill request.")
    @ExceptionHandler(value = ServiceDataAccessException.class)
    public ModelAndView serviceDataAccessException(HttpServletRequest req, Exception e) {

        ModelAndView mav = new ModelAndView();
        mav.addObject("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        mav.setViewName("error_page");
        return mav;
    }

    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Not found")
    public ModelAndView notFound(HttpServletRequest req) {

        ModelAndView mav = new ModelAndView("views/error_page");
        mav.addObject("code", HttpStatus.NOT_FOUND.value());
        return mav;
    }
}
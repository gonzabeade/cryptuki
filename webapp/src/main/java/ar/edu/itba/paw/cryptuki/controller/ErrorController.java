package ar.edu.itba.paw.cryptuki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@Controller
public class ErrorController {


    @Autowired
    private MessageSource messageSource;


    @RequestMapping(value = "/404", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView notFound(Locale locale) {
        ModelAndView mav = new ModelAndView("views/error_page");
        mav.addObject("errorMsg", messageSource.getMessage("error.404", null, locale));
        mav.addObject("code", HttpStatus.NOT_FOUND.value());
        return mav;
    }

    @RequestMapping(value = "/400", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView badRequest(Locale locale) {
        ModelAndView mav = new ModelAndView("views/error_page");
        mav.addObject("errorMsg", messageSource.getMessage("error.400", null, locale));
        mav.addObject("code", HttpStatus.BAD_REQUEST.value());
        return mav;
    }

    @RequestMapping(value = "/403", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView accessDenied(Locale locale) {
        ModelAndView mav = new ModelAndView("views/error_page");
        mav.addObject("errorMsg", messageSource.getMessage("error.403", null, locale));
        mav.addObject("code", HttpStatus.FORBIDDEN.value());
        return mav;
    }

    @RequestMapping(value = "/500", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView internalServerError(Locale locale) {
        ModelAndView mav = new ModelAndView("views/error_page");
        mav.addObject("errorMsg", messageSource.getMessage("error.500", null, locale));
        mav.addObject("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return mav;
    }

}
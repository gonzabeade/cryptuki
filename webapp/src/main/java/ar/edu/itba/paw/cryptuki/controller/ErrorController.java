package ar.edu.itba.paw.cryptuki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {
    @RequestMapping(value = "errors", method = RequestMethod.GET)
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

        ModelAndView errorPage = new ModelAndView("views/error_page");
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) {
            case 400: {
                errorMsg = "Parece que estás enviando algo incorrecto";
                break;
            }
            case 403: {
                errorMsg = "No tiene los permisos necesarios para ingresar a esta página";
                break;
            }
            case 404: {
                errorMsg = "Lo sentimos! No encontramos lo que pediste";
                break;
            }
            case 500: {
                errorMsg = "Hubo un error de nuestro lado! Por favor intenta de nuevo. Si el error persiste, contacte a soporte";
            }
            default: {
                errorMsg = "Error desconocido";
            }
        }
        errorPage.addObject("errorMsg", errorMsg);
        errorPage.addObject("code", httpErrorCode);
        return errorPage;
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute("javax.servlet.error.status_code");
    }
}
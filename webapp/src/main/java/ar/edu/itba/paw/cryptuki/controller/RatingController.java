package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.form.RatingForm;
import ar.edu.itba.paw.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RatingController {
    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }
    @RequestMapping(value = "/rate", method = RequestMethod.POST)
    public ModelAndView rate(@Valid @ModelAttribute("ratingForm") RatingForm ratingForm, final  BindingResult errors, final Authentication authentication){
        if(errors.hasErrors()){
            return null;
        }
        ratingService.rate(ratingForm.getTradeId(), authentication.getName(),  ratingForm.getRating());
        return new ModelAndView("redirect:/");
    }
}

package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.form.support.GeneralSupportForm;
import ar.edu.itba.paw.cryptuki.form.support.TradeComplainSupportForm;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class HomeController {

    private final UserService us;
    private final ComplainService complainService;

    @Autowired
    public HomeController(UserService us, ComplainService complainService) {
        this.us = us;
        this.complainService = complainService;
    }

    /** Redirect: depending on the role, the landing page is different*/
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView landing(Authentication authentication) {
        if (authentication != null && authentication.getAuthorities().stream().findFirst().get().getAuthority().equals("ROLE_ADMIN"))
            return new ModelAndView("redirect:/admin");
        return new ModelAndView("redirect:/buyer/market");
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public ModelAndView contact(@ModelAttribute("supportForm") GeneralSupportForm form, Authentication authentication){
        ModelAndView mav =  new ModelAndView("contact");

        // Autocomplete email if user is logged in. If it is not logged in, ask for it.
        if ( null != authentication ) {
            String username= authentication.getName();
            User user = us.getUserByUsername(username).orElseThrow(()->new NoSuchUserException(username));
            form.setEmail(user.getEmail());
        }

        mav.addObject("supportForm", form);
        return mav;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.POST)
    public ModelAndView contactPost(@Valid  @ModelAttribute("supportForm") GeneralSupportForm form, BindingResult errors, Authentication authentication){
        if(errors.hasErrors())
            return contact(form, authentication);
        complainService.getSupportFor(form.getEmail(), form.getMessage());
        return new ModelAndView("redirect:/contact?success");
    }
}


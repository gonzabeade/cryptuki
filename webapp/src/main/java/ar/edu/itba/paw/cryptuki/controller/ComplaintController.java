package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.ComplainFilter;
import ar.edu.itba.paw.OfferFilter;
import ar.edu.itba.paw.cryptuki.form.SupportForm;
import ar.edu.itba.paw.persistence.Complain;
import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;


@Controller  /* Requests can be dispatched to this class */
public class ComplaintController {

    private final UserService us;
    private final ComplainService complainService;
    private static final int PAGE_SIZE = 7;

    @Autowired
    public ComplaintController(UserService us, ComplainService complainService) {
        this.us = us;
        this.complainService = complainService;
    }



    @RequestMapping(value="/complaints", method = {RequestMethod.GET})
    public ModelAndView complaints(@RequestParam(value = "page") final Optional<Integer> page,Authentication authentication){

        ModelAndView mav = new ModelAndView("views/complaints_page");

        int pageNumber= page.orElse(0);
        int complaintsCount = complainService.countComplainsBy(new ComplainFilter.Builder().withComplainerUsername(authentication.getName()).build());
        int pages=(complaintsCount+PAGE_SIZE-1)/PAGE_SIZE;
        ComplainFilter complainFilter = new ComplainFilter.Builder().withComplainerUsername(authentication.getName())
                .withPage(pageNumber)
                .withPageSize(PAGE_SIZE)
                .build();
        Collection<Complain> complaintsList = complainService.getComplainsBy(complainFilter);
        mav.addObject("complaintsList",complaintsList);
        mav.addObject("pages",pages);
        mav.addObject("activePage",pageNumber);

        mav.addObject("username",authentication.getName());
        return mav;
    }

    @RequestMapping(value = "/complain", method = RequestMethod.GET)
    public ModelAndView complain(@ModelAttribute("supportForm") final SupportForm form, final Authentication authentication, @RequestParam( value = "tradeId", required = false) final Integer tradeId){
        ModelAndView mav =  new ModelAndView("views/complain");

        String username= authentication.getName();
        User user = us.getUserInformation(username).get();
        form.setEmail(user.getEmail());
        mav.addObject("complainerId",user.getId());
        mav.addObject("username", authentication.getName());
        mav.addObject("supportForm", form);
        mav.addObject("tradeId", tradeId);
        return mav;
    }

    @RequestMapping(value = "/complain/success", method = RequestMethod.GET)
    public ModelAndView complainSuccess(@ModelAttribute("supportForm") final SupportForm form, final Authentication authentication, @RequestParam( value = "tradeId", required = false) final Integer tradeId) {
        ModelAndView mav = complain(new SupportForm(), authentication, tradeId);
        mav.addObject("completed", true);
        return mav;
    }

    @RequestMapping(value = "/complain", method = RequestMethod.POST)
    public ModelAndView createComplain(@Valid  @ModelAttribute("supportForm") final SupportForm form, final BindingResult errors, final Authentication authentication){

        if(errors.hasErrors())
            return complain(form, authentication, form.getTradeId());

        complainService.makeComplain( form.toComplainBuilder() );

        return new ModelAndView("redirect:/complain/success");
    }


}


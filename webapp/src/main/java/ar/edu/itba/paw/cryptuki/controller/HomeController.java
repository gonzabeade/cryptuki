package ar.edu.itba.paw.cryptuki.controller;


import ar.edu.itba.paw.Offer;
import ar.edu.itba.paw.User;
import ar.edu.itba.paw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller  /* Requests can be dispatched to this class */
public class HomeController {

    private final UserService us;
    private final OfferService offerService;

    @Autowired
    public HomeController(UserService us, OfferService offerService) {
        this.us = us;
        this.offerService = offerService;
    }

    @RequestMapping("/") /* When requests come to this path, requests are forwarded to this method*/
    public ModelAndView home() {
        /*Alter the model (M) alters de view (V) via this Controller (C)*/
        final ModelAndView mav = new ModelAndView("views/index"); /* Load a jsp file */

        List<Offer> offers = offerService.getAllOffers();
        System.out.println("OFFERS"+offers);
        mav.addObject("offerList",offers);
        String[] payments = {"bru", "mp"}; //this is WRONG, it should get the info from the offer. Demostrative purposes only
        mav.addObject("payments", payments);
        return mav;
    }
    @RequestMapping("/contact")
    public ModelAndView contact(){
        final ModelAndView mav = new ModelAndView("views/contact"); /* Load a jsp file */
        return mav;
    }
    @RequestMapping(value= "/support", method = {RequestMethod.POST})
    public ModelAndView support(@RequestParam(name= "email",required= true) final String email, @RequestParam(name= "message",required= true) final String message){
        //send ticket to our email with this info
        return new ModelAndView("redirect:/");
    }


}

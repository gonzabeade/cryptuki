package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.form.OfferBuyForm;
import ar.edu.itba.paw.cryptuki.form.SupportForm;
import ar.edu.itba.paw.persistence.Offer;
import ar.edu.itba.paw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.Optional;


@Controller  /* Requests can be dispatched to this class */
public class HomeController {

    private final UserService us;
    private final OfferService offerService;
    private final CryptocurrencyService cryptocurrencyService;
    private final SupportService supportService;
    private final TradeService tradeService;
    private static final int PAGE_SIZE = 3;

    @Autowired
    public HomeController(UserService us, OfferService offerService, CryptocurrencyService cryptocurrencyService, SupportService supportService, TradeService tradeService) {
        this.us = us;
        this.offerService = offerService;
        this.supportService = supportService;
        this.cryptocurrencyService = cryptocurrencyService;
        this.tradeService = tradeService;
    }

    @RequestMapping(value = {"/","/{page}"}, method = RequestMethod.GET)
    public ModelAndView helloWorld(@PathVariable(value = "page") final Optional<Integer> page) {

        final ModelAndView mav = new ModelAndView("views/index");/* Load a jsp file */

        int offersSize = offerService.getOfferCount();
        Iterable<Offer> offers = offerService.getPagedOffers(page.orElse(0), PAGE_SIZE); // offerService.getPagedOffers(page.get(),PAGE_SIZE);
        mav.addObject("offerList", offers);

        mav.addObject("pages", 1 +  (offersSize-1) / PAGE_SIZE);
        return mav;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public ModelAndView support( @ModelAttribute("supportForm") final SupportForm form ){
        return new ModelAndView("views/contact");
    }

    @RequestMapping(value = "/contact", method = RequestMethod.POST)
    public ModelAndView createTicket(@Valid  @ModelAttribute("supportForm") final SupportForm form, final BindingResult errors){
        if(errors.hasErrors()){
            return support(form);
        }
        supportService.getSupportFor(form.toSupportDigest());
        return new ModelAndView("redirect:/");
    }


    @RequestMapping(value = "/buy/{offerId}", method = RequestMethod.GET)
    public ModelAndView buyOffer(@PathVariable("offerId") final int offerId, @ModelAttribute("offerBuyForm") final OfferBuyForm form){
        ModelAndView mav = new ModelAndView("views/buy_offer");
        mav.addObject("offer", offerService.getOffer(offerId));
        return mav;
    }
    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public ModelAndView buyOffer(@Valid @ModelAttribute("offerBuyForm") final OfferBuyForm form, final BindingResult errors){
        if(errors.hasErrors()){
            return buyOffer(form.getOfferId(), form);
        }
        tradeService.executeTrade(form.toOfferBuyDigest());
        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/coins") /* When requests come to this path, requests are forwarded to this method*/
    public ModelAndView coins() {
        /*Alter the model (M) alters de view (V) via this Controller (C)*/
        final ModelAndView mav = new ModelAndView("views/coins_page"); /* Load a jsp file */
        mav.addObject("coinList", cryptocurrencyService.getAllCryptocurrencies());
        return mav;
    }

}
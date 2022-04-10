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


@Controller  /* Requests can be dispatched to this class */
public class HomeController {

    private final UserService us;
    private final OfferService offerService;
    private final ContactService<MailMessage> mailContactService;
    private final CryptocurrencyService cryptocurrencyService;

    @Autowired
    public HomeController(UserService us, OfferService offerService, ContactService<MailMessage> mailContactService, CryptocurrencyService cryptocurrencyService) {
        this.us = us;
        this.offerService = offerService;
        this.mailContactService = mailContactService;
        this.cryptocurrencyService = cryptocurrencyService;
    }

    @RequestMapping("/") /* When requests come to this path, requests are forwarded to this method*/
    public ModelAndView helloWorld() {
        /*Alter the model (M) alters de view (V) via this Controller (C)*/
        final ModelAndView mav = new ModelAndView("views/index"); /* Load a jsp file */

        Iterable<Offer> offers = offerService.getAllOffers();
        mav.addObject("offerList",offers);
        String[] payments = {"bru", "mp"}; //this is WRONG, it should get the info from the offer. Demostrative purposes only
        mav.addObject("payments", payments);
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
        //send mail
        String user = form.getEmail();
        String message = form.getMessage();
        MailMessage mailMessage = mailContactService.createMessage(user);
        mailMessage.setBody("Tu consulta: " + message);
        mailMessage.setSubject("Hemos recibido tu consulta");
        mailContactService.sendMessage(mailMessage);

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
        String user = form.getEmail();

        String message =  user + " ha demostrado interés en  " + offerService.getOffer(form.getOfferId()).toString();
        message+="\nQuiere comprarte " + form.getAmount() + "ARS";

        message+="\nTambién te dejó un mensaje: " + form.getMessage();
        message+="\nContactalo ya por mail!";
        MailMessage mailMessage = mailContactService.createMessage(offerService.getOffer(form.getOfferId()).getSeller().getEmail());
        mailMessage.setBody(message);

        mailMessage.setSubject("Recibiste una oferta por tu publicación en Cryptuki!");
        mailContactService.sendMessage(mailMessage);

        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/coins") /* When requests come to this path, requests are forwarded to this method*/
    public ModelAndView coins() {
        /*Alter the model (M) alters de view (V) via this Controller (C)*/
        final ModelAndView mav = new ModelAndView("views/CoinsPage"); /* Load a jsp file */
        mav.addObject("coinList", cryptocurrencyService.getAllCryptocurrencies());
        return mav;
    }

}


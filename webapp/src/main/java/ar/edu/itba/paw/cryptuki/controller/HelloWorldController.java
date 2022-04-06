package ar.edu.itba.paw.cryptuki.controller;


import ar.edu.itba.paw.Offer;
import ar.edu.itba.paw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;


@Controller  /* Requests can be dispatched to this class */
public class HelloWorldController {

    private final UserService us;
    private final OfferService offerService;
    private final GmailSender gmailSender;

    @Autowired
    public HelloWorldController(UserService us, OfferService offerService, GmailSender gmailSender) {
        this.us = us;
        this.offerService = offerService;
        this.gmailSender = gmailSender;
    }

    @RequestMapping("/") /* When requests come to this path, requests are forwarded to this method*/
    public ModelAndView helloWorld() {
        /*Alter the model (M) alters de view (V) via this Controller (C)*/
        final ModelAndView mav = new ModelAndView("hello/index"); /* Load a jsp file */
        offerService.makeOffer(2,new Date(),"arg",15,16);


        List<Offer> offers = offerService.getAllOffers();
        System.out.println("OFFERS"+offers);
        mav.addObject("empList",offers);
        mav.addObject("empListSize",offers.size());

        mav.addObject("greeting", us.getUsername(1)); /* Set variables to be used within the jsp*/
        return mav;
    }

    @RequestMapping("/chau") /* When requests come to this path, requests are forwarded to this method*/
    public ModelAndView byeByeWorld() {
        /*Alter the model (M) alters de view (V) via this Controller (C)*/
        final ModelAndView mav = new ModelAndView("hello/byebye"); /* Load a jsp file */
        mav.addObject("greeting", "PAW"); /* Set variables to be used within the jsp*/
        return mav;
    }

    @RequestMapping("/mail")
    public ModelAndView sendMail(String receiverMail) throws MessagingException {

        final MimeMessage mimeMessage = this.gmailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
        message.setSubject("Test de servicio de mail paw");
        message.setFrom("TestPaw25@gmail.com");
        message.setTo(receiverMail);
        message.setText("Buenas, este es un intento de mail, adioss", false);

        this.gmailSender.send(mimeMessage);


        final ModelAndView mav = new ModelAndView("hello/index"); /* Load a jsp file */
        offerService.makeOffer(2,new Date(),"arg",15,16);


        List<Offer> offers = offerService.getAllOffers();
        System.out.println("OFFERS"+offers);
        mav.addObject("empList",offers);
        mav.addObject("empListSize",offers.size());

        mav.addObject("greeting", us.getUsername(1)); /* Set variables to be used within the jsp*/
        return mav;
    }
}

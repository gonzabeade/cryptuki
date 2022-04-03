package ar.edu.itba.paw.cryptuki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.service.UserService;

@Controller  /* Requests can be dispatched to this class */
public class HelloWorldController {

    private final UserService us;

    @Autowired /*This is the constructor I want Spring to use*/
    public HelloWorldController(final UserService us) {
        this.us = us;
    }

    @RequestMapping("/") /* When requests come to this path, requests are forwarded to this method*/
    public ModelAndView helloWorld() {
        /*Alter the model (M) alters de view (V) via this Controller (C)*/
        final ModelAndView mav = new ModelAndView("hello/index"); /* Load a jsp file */
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
}

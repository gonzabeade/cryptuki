package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.OfferFilter;
import ar.edu.itba.paw.cryptuki.form.*;
import ar.edu.itba.paw.cryptuki.utils.LastConnectionUtils;
import ar.edu.itba.paw.persistence.Image;
import ar.edu.itba.paw.persistence.Offer;
import ar.edu.itba.paw.persistence.Trade;
import ar.edu.itba.paw.persistence.UserAuth;
import ar.edu.itba.paw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;


@Controller  /* Requests can be dispatched to this class */
public class HomeController {

    private final UserService us;
    private final OfferService offerService;
    private final CryptocurrencyService cryptocurrencyService;
    private final SupportService supportService;
    private final PaymentMethodService paymentMethodService;
    private static final int PAGE_SIZE = 7;



    @Autowired
    public HomeController(UserService us,
                          OfferService offerService,
                          CryptocurrencyService cryptocurrencyService,
                          SupportService supportService,
                          PaymentMethodService paymentMethodService) {
        this.us = us;
        this.offerService = offerService;
        this.supportService = supportService;
        this.cryptocurrencyService = cryptocurrencyService;
        this.paymentMethodService = paymentMethodService;
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView helloWorld(@RequestParam(value = "page") final Optional<Integer> page, @RequestParam(value = "coin", required = false) final String coin, @RequestParam(value = "pm", required = false) final String paymentMethod, @RequestParam(value = "price", required = false) final Float price, final Authentication authentication) {

        final ModelAndView mav = new ModelAndView("views/index");/* Load a jsp file */
        int pageNumber = page.orElse(0);

        OfferFilter filter = new OfferFilter();
        if(coin != null){
            filter = filter.byCryptoCode(coin);
        }
        if(paymentMethod != null){
            filter = filter.byPaymentMethod(paymentMethod);
        }
        if(price != null){
            filter = filter.byMinPrice(price);
        }
        filter = filter.withPageSize(PAGE_SIZE).fromPage(pageNumber);

        mav.addObject("offerList", offerService.getOfferBy(filter));
        int offerCount = offerService.countOffersBy(filter);
        int pages =  (offerCount + PAGE_SIZE - 1) / PAGE_SIZE;;
        mav.addObject("pages", pages);
        mav.addObject("activePage", pageNumber);
        mav.addObject("cryptocurrencies", cryptocurrencyService.getAllCryptocurrencies());
        mav.addObject("paymentMethods", paymentMethodService.getAllPaymentMethods());
        mav.addObject("offerCount", offerCount);

        if(authentication != null){
            mav.addObject("username",  authentication.getName());
            mav.addObject("userEmail", us.getUserInformation(authentication.getName()).get().getEmail());
            mav.addObject("isAdmin", authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN")));
        }

        return mav;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public ModelAndView support(@ModelAttribute("supportForm") final SupportForm form, final Authentication authentication,@RequestParam( value = "completed", required = false) final boolean completed){
        ModelAndView mav =  new ModelAndView("views/contact");
        mav.addObject("username", authentication == null ? null : authentication.getName());
        mav.addObject("completed", completed);

        return mav;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.POST)
    public ModelAndView createTicket(@Valid  @ModelAttribute("supportForm") final SupportForm form, final BindingResult errors, final Authentication authentication){
        if(errors.hasErrors()){
            return support(form,authentication, false);
        }

        supportService.getSupportFor(form.toDigest());

        return support(new SupportForm(),authentication, true);

    }

    @RequestMapping(value = "/coins", method = RequestMethod.GET) /* When requests come to this path, requests are forwarded to this method*/
    public ModelAndView coins(final Authentication authentication) {
        /*Alter the model (M) alters de view (V) via this Controller (C)*/
        final ModelAndView mav = new ModelAndView("views/coins_page"); /* Load a jsp file */
        mav.addObject("coinList", cryptocurrencyService.getAllCryptocurrencies());

        mav.addObject("username", authentication == null ? null : authentication.getName());

        return mav;

    }
    @RequestMapping(value = "/admin/complaint/{complaintId}", method = RequestMethod.GET)
    public ModelAndView complaintDetail(@PathVariable(value = "complaintId") final int complaintId, final Authentication authentication){
        ModelAndView mav = new ModelAndView("views/complaint");
        mav.addObject("username", authentication == null ? null : authentication.getName());
        mav.addObject("isAdmin", true);
        mav.addObject("trade", "messi");
        return mav;

    }
    @RequestMapping(value = "/admin/solve/{complaintId}", method = RequestMethod.GET)
    public ModelAndView solveComplaint(@ModelAttribute("solveComplaintForm") SolveComplaintForm form, @PathVariable(value = "complaintId") final int complaintId, final Authentication authentication){

        ModelAndView mav = new ModelAndView("views/solve_complaint");
        mav.addObject("username", authentication == null ? null : authentication.getName());
        mav.addObject("isAdmin", true);
        mav.addObject("trade", "messi");
        return mav;
    }
    @RequestMapping(value = "/admin/solve/{complaintId}", method = RequestMethod.POST)
    public ModelAndView solveComplaint(@Valid @ModelAttribute("solveComplaintForm") SolveComplaintForm form, BindingResult result, @PathVariable(value = "complaintId") final int complaintId, final Authentication authentication){
        if(result.hasErrors()){
            return solveComplaint(form,complaintId,authentication);
        }
        ModelAndView mav = new ModelAndView("views/solved_complaint");
        mav.addObject("username", authentication == null ? null : authentication.getName());
        mav.addObject("isAdmin", true);
        return mav;
    }

}
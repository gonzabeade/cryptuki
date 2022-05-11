package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.OfferFilter;
import ar.edu.itba.paw.cryptuki.form.SupportForm;
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
import java.util.Optional;


@Controller  /* Requests can be dispatched to this class */
public class HomeController {

    private final UserService us;
    private final OfferService offerService;
    private final CryptocurrencyService cryptocurrencyService;
    private final PaymentMethodService paymentMethodService;
    private final ComplainService complainService;
    private static final int PAGE_SIZE = 7;

    @Autowired
    public HomeController(UserService us,
                          OfferService offerService,
                          CryptocurrencyService cryptocurrencyService,
                          PaymentMethodService paymentMethodService,
                          ComplainService complainService) {
        this.us = us;
        this.offerService = offerService;
        this.cryptocurrencyService = cryptocurrencyService;
        this.paymentMethodService = paymentMethodService;
        this.complainService = complainService;
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView landing(@RequestParam(value = "page") final Optional<Integer> page, @RequestParam(value = "coin", required = false) final String coin, @RequestParam(value = "pm", required = false) final String paymentMethod, @RequestParam(value = "price", required = false) final Double price, final Authentication authentication) {

        final ModelAndView mav = new ModelAndView("index");

        int pageNumber = page.orElse(0);
        OfferFilter filter = new OfferFilter()
            .byCryptoCode(coin)
                .byPaymentMethod(paymentMethod)
                .byMinPrice(price)
                .byMaxPrice(price)
                .withPageSize(PAGE_SIZE)
                .fromPage(pageNumber);

        int offerCount = offerService.countOffersBy(filter);
        int pages =  (offerCount + PAGE_SIZE - 1) / PAGE_SIZE;

        mav.addObject("offerList", offerService.getOfferBy(filter));
        mav.addObject("pages", pages);
        mav.addObject("activePage", pageNumber);
        mav.addObject("cryptocurrencies", cryptocurrencyService.getAllCryptocurrencies());
        mav.addObject("paymentMethods", paymentMethodService.getAllPaymentMethods());
        mav.addObject("offerCount", offerCount);

        if( null != authentication){
            mav.addObject("username",  authentication.getName());
            mav.addObject("userEmail", us.getUserInformation(authentication.getName()).get().getEmail());
            mav.addObject("isAdmin", authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN")));
        }
        return mav;
    }

    @RequestMapping(value = "/coins", method = RequestMethod.GET)
    public ModelAndView coins(final Authentication authentication) {
        final ModelAndView mav = new ModelAndView("coinsPage"); /* Load a jsp file */
        mav.addObject("coinList", cryptocurrencyService.getAllCryptocurrencies());
        mav.addObject("username", authentication == null ? null : authentication.getName());
        return mav;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public ModelAndView contact(@ModelAttribute("supportForm") final SupportForm form, Authentication authentication){
        ModelAndView mav =  new ModelAndView("contact");

        if ( null != authentication ) {
            String username= authentication.getName();
            User user = us.getUserInformation(username).get();
            form.setEmail(user.getEmail());
            mav.addObject("username",  authentication.getName());
        }

        mav.addObject("supportForm", form);
        return mav;
    }

    @RequestMapping(value = "/contact/success", method = RequestMethod.GET)
    public ModelAndView contactSuccess(Authentication authentication){
        ModelAndView mav = contact(new SupportForm(), authentication);
        mav.addObject("completed", true);
        return mav;
    }


    @RequestMapping(value = "/contact", method = RequestMethod.POST)
    public ModelAndView contactPost(@Valid  @ModelAttribute("supportForm") final SupportForm form, final BindingResult errors, final Authentication authentication){

        if(errors.hasErrors())
            return contact(form, authentication);

        complainService.getSupportFor(form.toDigest());
        return new ModelAndView("redirect:/contact/success");
    }

}


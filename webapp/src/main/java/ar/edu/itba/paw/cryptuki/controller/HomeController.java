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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;


@Controller  /* Requests can be dispatched to this class */
public class HomeController {

    private final UserService us;
    private final OfferService offerService;
    private final CryptocurrencyService cryptocurrencyService;
    private final SupportService supportService;
    private final PaymentMethodService paymentMethodService;
    private final ComplainService complainService;
    private static final int PAGE_SIZE =2;



    @Autowired
    public HomeController(UserService us,
                          OfferService offerService,
                          CryptocurrencyService cryptocurrencyService,
                          SupportService supportService,
                          PaymentMethodService paymentMethodService,
                          ComplainService complainService) {
        this.us = us;
        this.offerService = offerService;
        this.supportService = supportService;
        this.cryptocurrencyService = cryptocurrencyService;
        this.paymentMethodService = paymentMethodService;
        this.complainService = complainService;
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
        int pages =  (offerCount + PAGE_SIZE - 1) / PAGE_SIZE;
        mav.addObject("pages", pages);
        mav.addObject("activePage", pageNumber);
        mav.addObject("cryptocurrencies", cryptocurrencyService.getAllCryptocurrencies());
        mav.addObject("paymentMethods", paymentMethodService.getAllPaymentMethods());
        mav.addObject("offerCount", offerCount);

        if(null != authentication){
            mav.addObject("username",  authentication.getName());
            mav.addObject("userEmail", us.getUserInformation(authentication.getName()).get().getEmail());
            mav.addObject("isAdmin", authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN")));
        }

        return mav;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public ModelAndView support(@ModelAttribute("supportForm") final SupportForm form, final Authentication authentication,@RequestParam( value = "tradeId", required = false) final Integer tradeId,@RequestParam( value = "completed", required = false) final boolean completed){
        ModelAndView mav =  new ModelAndView("views/contact");

        if(null!=authentication){
            String username= authentication.getName();
            User user = us.getUserInformation(username).get();
            mav.addObject("complainerId",user.getId());
            mav.addObject("username", authentication.getName());
        }

        mav.addObject("tradeId",tradeId);
        mav.addObject("completed", completed);
        return mav;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.POST)
    public ModelAndView createTicket(@Valid  @ModelAttribute("supportForm") final SupportForm form, final BindingResult errors, final Authentication authentication){
        if(errors.hasErrors()){
            return support(form,authentication, form.getTradeId(),false);
        }
        if(null!=authentication){
            supportService.getSupportFor(form.toComplainBuilder());
        }
        return support(new SupportForm(), authentication,null, true);

    }
    @RequestMapping(value = "/coins", method = RequestMethod.GET) /* When requests come to this path, requests are forwarded to this method*/
    public ModelAndView coins(final Authentication authentication) {
        /*Alter the model (M) alters de view (V) via this Controller (C)*/
        final ModelAndView mav = new ModelAndView("views/coins_page"); /* Load a jsp file */
        mav.addObject("coinList", cryptocurrencyService.getAllCryptocurrencies());

        mav.addObject("username", authentication == null ? null : authentication.getName());

        return mav;

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
}


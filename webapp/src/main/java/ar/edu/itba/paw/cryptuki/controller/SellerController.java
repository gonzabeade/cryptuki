package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.form.*;
import ar.edu.itba.paw.cryptuki.form.seller.UploadOfferForm;
import ar.edu.itba.paw.exception.NoSuchTradeException;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/seller")
public class SellerController {
    private final OfferService offerService;
    private final CryptocurrencyService cryptocurrencyService;
    private final UserService userService;
    private static final int PAGE_SIZE = 6;

    @Autowired
    public SellerController(OfferService offerService, UserService userService, CryptocurrencyService cryptocurrencyService) {
        this.offerService = offerService;
        this.userService = userService;
        this.cryptocurrencyService = cryptocurrencyService;
    }

    public ModelAndView baseSellerDashboard(Authentication authentication, @RequestParam(value = "page") Optional<Integer> page, @ModelAttribute("profilePicForm") ProfilePicForm form, Set<OfferStatus> status) {
        ModelAndView mav = new ModelAndView("seller/sellerIndexVerbose");

        String username = authentication.getName();
        int pageNumber= page.orElse(0);

        User user = userService.getUserByUsername(username).orElseThrow(()->new NoSuchUserException(username));
        Collection<Offer> offers = offerService.getOffersByUsername(authentication.getName(), pageNumber, PAGE_SIZE, status);
        long offerCount = offerService.countOffersByUsername(authentication.getName(), status);
        long pages = (offerCount+PAGE_SIZE-1)/PAGE_SIZE;

        mav.addObject("offerList", offers);
        mav.addObject("user", user);
        mav.addObject("pages", pages);
        mav.addObject("activePage", pageNumber);
        return mav;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView seller(Authentication authentication, @RequestParam(value = "page") Optional<Integer> page, @ModelAttribute("profilePicForm") ProfilePicForm form){
        return baseSellerDashboard(authentication, page, form, EnumSet.allOf(OfferStatus.class));
    }

    @RequestMapping(value = "/active", method = RequestMethod.GET)
    public ModelAndView active(Authentication authentication, @RequestParam(value = "page") Optional<Integer> page, @ModelAttribute("profilePicForm") ProfilePicForm form){
        return baseSellerDashboard(authentication, page, form, EnumSet.of(OfferStatus.APR));
    }

    @RequestMapping(value = "/paused", method = RequestMethod.GET)
    public ModelAndView paused(Authentication authentication, @RequestParam(value = "page") Optional<Integer> page, @ModelAttribute("profilePicForm") ProfilePicForm form){
        return baseSellerDashboard(authentication, page, form, EnumSet.of(OfferStatus.PSE));
    }

    @RequestMapping(value = "/deleted", method = RequestMethod.GET)
    public ModelAndView deleted(Authentication authentication, @RequestParam(value = "page") Optional<Integer> page, @ModelAttribute("profilePicForm") ProfilePicForm form){
        return baseSellerDashboard(authentication, page, form, EnumSet.of(OfferStatus.DEL));
    }

    @RequestMapping(value = "/sold", method = RequestMethod.GET)
    public ModelAndView sold(Authentication authentication, @RequestParam(value = "page") Optional<Integer> page, @ModelAttribute("profilePicForm") ProfilePicForm form){
        return baseSellerDashboard(authentication, page, form, EnumSet.of(OfferStatus.SOL));
    }

}

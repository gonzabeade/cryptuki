package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.form.*;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/seller")
public class SellerController {
    private final OfferService offerService;
    private final UserService userService;
    private final TradeService tradeService;
    private static final int PAGE_SIZE = 6;
    private static final int Q_MOST_RECENT_TRADES = 5;

    @Autowired
    public SellerController(OfferService offerService, UserService userService, CryptocurrencyService cryptocurrencyService, TradeService tradeService) {
        this.offerService = offerService;
        this.userService = userService;
        this.tradeService = tradeService;
    }

    public ModelAndView baseSellerDashboard(Authentication authentication, @RequestParam(value = "page") Optional<Integer> page, @ModelAttribute("profilePicForm") ProfilePicForm form, Set<OfferStatus> status) {
        ModelAndView mav = new ModelAndView("seller/sellerIndexVerbose");

        String username = authentication.getName();
        int pageNumber= page.orElse(0);

        User user = userService.getUserByUsername(username).orElseThrow(()->new NoSuchUserException(username));
        Collection<Offer> offers = offerService.getOffersByUsername(authentication.getName(), pageNumber, PAGE_SIZE, status);

        Collection<Trade> trades = tradeService.getMostRecentTradesAsSeller(authentication.getName(), Q_MOST_RECENT_TRADES);

        long offerCount = offerService.countOffersByUsername(authentication.getName(), status);
        long pages = (offerCount+PAGE_SIZE-1)/PAGE_SIZE;


        mav.addObject("offerList", offers);
        mav.addObject("tradeList", trades);
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
        ModelAndView mav =  baseSellerDashboard(authentication, page, form, EnumSet.of(OfferStatus.APR));
        mav.addObject("status", OfferStatus.APR);
        return mav;
    }

    @RequestMapping(value = "/paused", method = RequestMethod.GET)
    public ModelAndView paused(Authentication authentication, @RequestParam(value = "page") Optional<Integer> page, @ModelAttribute("profilePicForm") ProfilePicForm form){
        ModelAndView mav =  baseSellerDashboard(authentication, page, form, EnumSet.of(OfferStatus.PSE));
        mav.addObject("status", OfferStatus.PSE);
        return mav;
    }

    @RequestMapping(value = "/deleted", method = RequestMethod.GET)
    public ModelAndView deleted(Authentication authentication, @RequestParam(value = "page") Optional<Integer> page, @ModelAttribute("profilePicForm") ProfilePicForm form){
        ModelAndView mav =  baseSellerDashboard(authentication, page, form, EnumSet.of(OfferStatus.DEL));
        mav.addObject("status", OfferStatus.DEL);
        return mav;
    }

    @RequestMapping(value = "/sold", method = RequestMethod.GET)
    public ModelAndView sold(Authentication authentication, @RequestParam(value = "page") Optional<Integer> page, @ModelAttribute("profilePicForm") ProfilePicForm form){
        ModelAndView mav =  baseSellerDashboard(authentication, page, form, EnumSet.of(OfferStatus.SOL));
        mav.addObject("status", OfferStatus.SOL);
        return mav;
    }

}

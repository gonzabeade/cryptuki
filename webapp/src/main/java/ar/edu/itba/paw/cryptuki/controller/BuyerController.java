package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.form.LandingForm;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.cryptuki.form.ProfilePicForm;
import ar.edu.itba.paw.exception.NoSuchUserException;
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
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/buyer")
public class BuyerController {

    private final TradeService tradeService;
    private final OfferService offerService;

    private final CryptocurrencyService cryptocurrencyService;

    private final UserService userService;
    private static final int PAGE_SIZE = 5;

    @Autowired
    public BuyerController(TradeService tradeService, OfferService offerService, UserService userService,CryptocurrencyService cryptocurrencyService)
    {
        this.tradeService = tradeService;
        this.offerService = offerService;
        this.userService = userService;
        this.cryptocurrencyService = cryptocurrencyService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView buyer(Authentication authentication,
                              @RequestParam(value = "page") final Optional<Integer> page,
                              @RequestParam(value = "status", required = false) final Optional<String> status,
                              @ModelAttribute("profilePicForm") ProfilePicForm form){
        ModelAndView mav = new ModelAndView("buyer/buyerIndex");

        String username = authentication.getName();
        int pageNumber = page.orElse(0);


        // TODO: check!!
        long tradeCount;
        Collection<Trade> tradeList;
        if (status.isPresent()) {
            TradeStatus askedStatus = TradeStatus.valueOf(status.get());
            tradeCount = tradeService.getTradesAsBuyerCount(username, askedStatus);
            tradeList = tradeService.getTradesAsBuyer(authentication.getName(), pageNumber, PAGE_SIZE, askedStatus);
        } else {
            tradeCount = tradeService.getTradesAsBuyerCount(username, TradeStatus.PENDING);
            tradeList = tradeService.getTradesAsBuyer(authentication.getName(), pageNumber, PAGE_SIZE, TradeStatus.PENDING);
        }


        User user = userService.getUserByUsername(username).orElseThrow(() -> new NoSuchUserException(username));


        int pages = (int)(tradeCount + PAGE_SIZE - 1) / PAGE_SIZE;

        mav.addObject("username", username);
        mav.addObject("user", user);
        mav.addObject("noBuyingTrades", tradeList.isEmpty());
        mav.addObject("tradeStatusList", TradeStatus.values());
        mav.addObject("tradeList", tradeList);
        mav.addObject("pages", pages);
        mav.addObject("activePage", pageNumber);
        return mav;
    }

    @RequestMapping(value = {"/market"}, method = RequestMethod.GET)
    public ModelAndView landing(@Valid @ModelAttribute("landingForm") LandingForm form, final BindingResult errors,
                                final Authentication authentication) {

        if(errors.hasErrors()){
            return new ModelAndView("forward:/400");
        }
        final ModelAndView mav = new ModelAndView("index");

        int pageNumber = form.getPage()!= null ? form.getPage() :  0;
        OfferFilter filter = new OfferFilter()
                .withPageSize(PAGE_SIZE)
                .withPage(form.getPage()!= null ? form.getPage() :  0)
                .withOfferStatus("APR")
                .orderingBy(OfferOrderCriteria.values()[form.getOrderCriteria()]);

        if (form.getCoins() != null){
            mav.addObject("selectedCoins", Arrays.asList(form.getCoins()));
            for (String coinCode: form.getCoins() ) {
                filter.withCryptoCode(coinCode);
            }
        }

        if(form.getLocation() != null){
            for (String location: form.getLocation() ) {
                filter.withLocation(location);
            }
        }

        long offerCount = offerService.countBuyableOffers(filter);
        long pages =  (offerCount + PAGE_SIZE - 1) / PAGE_SIZE;

        Collection<Offer> offer = offerService.getBuyableOffers(filter);
        Collection<Cryptocurrency> cryptocurrencies = cryptocurrencyService.getAllCryptocurrencies();
        Collection<LocationCountWrapper> locationCountWrappers = offerService.getOfferCountByLocation();


        mav.addObject("offerList", offer);
        mav.addObject("pages", pages);
        mav.addObject("activePage", pageNumber);
        mav.addObject("cryptocurrencies", cryptocurrencies);
        mav.addObject("paymentMethods", Arrays.asList(PaymentMethod.values()));
        mav.addObject("offerCount", offerCount);
        mav.addObject("locations", Arrays.asList(Location.values()));
        mav.addObject("locationsWithOffers", locationCountWrappers);


        mav.addObject("location" , form.getLocation());
        mav.addObject("coins" , form.getCoins());
        mav.addObject("page" , form.getPage());
        mav.addObject("orderCriteria", form.getOrderCriteria());
        mav.addObject("arsAmount", form.getArsAmount());


        if( null != authentication){
            mav.addObject("userEmail", userService.getUserByUsername(authentication.getName()).orElseThrow(()->new NoSuchUserException(authentication.getName())).getEmail());
        }
        return mav;
    }



}

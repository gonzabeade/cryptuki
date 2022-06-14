package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.form.*;
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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/seller")
public class SellerController {
    private final TradeService tradeService;
    private final OfferService offerService;

    private final CryptocurrencyService cryptocurrencyService;
    private final UserService userService;
    private static final int PAGE_SIZE = 6;

    @Autowired
    public SellerController(TradeService tradeService, OfferService offerService, UserService userService, CryptocurrencyService cryptocurrencyService) {
        this.tradeService = tradeService;
        this.offerService = offerService;
        this.userService = userService;
        this.cryptocurrencyService = cryptocurrencyService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView seller(Authentication authentication, @RequestParam(value = "page") final Optional<Integer> page, final @ModelAttribute("soldTradeForm") SoldTradeForm soldTradeForm, @ModelAttribute("statusTradeForm") final StatusTradeForm statusTradeForm,@ModelAttribute("profilePicForm") ProfilePicForm form){
        ModelAndView mav = new ModelAndView("seller/sellerIndexVerbose");

        String username = authentication.getName();
        int pageNumber= page.orElse(0);
        User user = userService.getUserByUsername(username).orElseThrow(()->new NoSuchUserException(username));
        Collection<Offer> offers = offerService.getOffersByUsername(authentication.getName() , pageNumber, PAGE_SIZE);
        long offerCount = offerService.countOffersByUsername(authentication.getName());
        long pages = (offerCount+PAGE_SIZE-1)/PAGE_SIZE;

        mav.addObject("offerList", offers);
        mav.addObject("noOffers", offers.isEmpty());
        mav.addObject("username", username);
        mav.addObject("user", user);
        mav.addObject("tradeStatusList", TradeStatus.values());
        mav.addObject("pages", pages);
        mav.addObject("activePage", pageNumber);
        return mav;
    }


    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public ModelAndView uploadOffer(@ModelAttribute("uploadOfferForm") final UploadOfferForm form, final Authentication authentication){
        ModelAndView mav = new ModelAndView("uploadPage");
        mav.addObject("cryptocurrencies", cryptocurrencyService.getAllCryptocurrencies());
        mav.addObject("paymentMethods", PaymentMethod.values());
        mav.addObject("location", form.getLocation());

        if (form.getPaymentMethods() != null){
            List<String> paymentCodesAlreadySelected = Arrays.asList(form.getPaymentMethods());
            mav.addObject("selectedPayments", paymentCodesAlreadySelected);
        }

        return mav;
    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView uploadOffer(@Valid @ModelAttribute("uploadOfferForm") final UploadOfferForm form, final BindingResult errors, final Authentication authentication){
        if (errors.hasErrors())
            return uploadOffer(form, authentication);
        int id = userService.getUserByUsername(authentication.getName()).orElseThrow(()->new NoSuchUserException(authentication.getName())).getId();
        Offer offer = offerService.makeOffer(form.toOfferParameterObject(id));
        return new ModelAndView("redirect:/offer/"+offer.getOfferId()+"/creationsuccess");
    }


    // TODO(gonza): Change offer status correctly !!!!
    @RequestMapping(value="/changeStatus",method = RequestMethod.POST)
    public ModelAndView updateStatus(final @ModelAttribute("soldTradeForm") SoldTradeForm soldTradeForm,@Valid @ModelAttribute("statusTradeForm") final StatusTradeForm statusTradeForm, final BindingResult errors ,final Authentication authentication){

        Trade trade = tradeService.getTradeById(statusTradeForm.getTradeId()).orElseThrow(()->new NoSuchTradeException(statusTradeForm.getTradeId()));
        if (statusTradeForm.getNewStatus().equals(TradeStatus.ACCEPTED.toString()))
            tradeService.acceptTrade(statusTradeForm.getTradeId());
        else if (statusTradeForm.getNewStatus().equals(TradeStatus.REJECTED.toString()))
            tradeService.rejectTrade(statusTradeForm.getTradeId());
        else if (statusTradeForm.getNewStatus().equals(TradeStatus.SOLD.toString()))
            tradeService.sellTrade(trade.getTradeId());

        return new ModelAndView("redirect:/chat?tradeId="+trade.getTradeId());
    }

    @RequestMapping(value="/acceptOffer", method= RequestMethod.POST)
    public ModelAndView acceptOffer(@RequestParam(value = "tradeId") int tradeId, final Authentication authentication) {
        tradeService.acceptTrade(tradeId);
        return new ModelAndView("redirect:/chat?tradeId="+tradeId);
    }

}

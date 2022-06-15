package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.form.seller.TradeFilterForm;
import ar.edu.itba.paw.exception.NoSuchOfferException;
import ar.edu.itba.paw.model.Trade;
import ar.edu.itba.paw.model.TradeStatus;
import ar.edu.itba.paw.service.OfferService;
import ar.edu.itba.paw.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Optional;

@Controller
@RequestMapping("/seller/trade")
public class TradeController {

    private static final int PAGE_SIZE = 6;
    private final TradeService tradeService;
    private final OfferService offerService;

    @Autowired
    public TradeController(TradeService tradeService, OfferService offerService) {
        this.tradeService = tradeService;
        this.offerService = offerService;
    }

    public ModelAndView baseTradesDashboard(Authentication authentication, Optional<Integer> page, Optional<Integer> focusOnOfferId, TradeStatus status) {
        ModelAndView mav = new ModelAndView("seller/sellerTrade");
        Collection<Trade> trades = tradeService.getTradesAsSeller(authentication.getName(), page.orElse(0), PAGE_SIZE, status);

        if (focusOnOfferId.isPresent())
            mav.addObject("focusOffer", offerService.getOfferById(focusOnOfferId.get()).orElseThrow(()-> new NoSuchOfferException(focusOnOfferId.get())));
        mav.addObject("trades", trades);
        mav.addObject("page", page.orElse(0));
        mav.addObject("status", status);
        return mav;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView tradeRedirect(){
        return new ModelAndView("redirect:/seller/trade/pending");
    }

    @RequestMapping(value = "/accepted", method = RequestMethod.GET)
    public ModelAndView trades(Authentication authentication, @ModelAttribute("tradeFilterForm")TradeFilterForm tradeFilterForm){
        return baseTradesDashboard(authentication, tradeFilterForm.getPageOptional(), tradeFilterForm.getFocusOnOfferIdOptional(), TradeStatus.ACCEPTED);
    }

    @RequestMapping(value = "/pending", method = RequestMethod.GET)
    public ModelAndView pendingTrades(Authentication authentication, @ModelAttribute("tradeFilterForm")TradeFilterForm tradeFilterForm){
        return baseTradesDashboard(authentication, tradeFilterForm.getPageOptional(), tradeFilterForm.getFocusOnOfferIdOptional(), TradeStatus.PENDING);
    }

    @RequestMapping(value = "/deletedByUser", method = RequestMethod.GET)
    public ModelAndView deletedByUserTrades(Authentication authentication, @ModelAttribute("tradeFilterForm")TradeFilterForm tradeFilterForm){
        return baseTradesDashboard(authentication, tradeFilterForm.getPageOptional(), tradeFilterForm.getFocusOnOfferIdOptional(), TradeStatus.DELETED);
    }

    @RequestMapping(value = "/rejected", method = RequestMethod.GET)
    public ModelAndView rejectedTrades(Authentication authentication, @ModelAttribute("tradeFilterForm")TradeFilterForm tradeFilterForm){
        return baseTradesDashboard(authentication, tradeFilterForm.getPageOptional(), tradeFilterForm.getFocusOnOfferIdOptional(), TradeStatus.REJECTED);
    }


    @RequestMapping(value = "/completed", method = RequestMethod.GET)
    public ModelAndView completedTrades(Authentication authentication, @ModelAttribute("tradeFilterForm")TradeFilterForm tradeFilterForm){
        return baseTradesDashboard(authentication, tradeFilterForm.getPageOptional(), tradeFilterForm.getFocusOnOfferIdOptional(), TradeStatus.SOLD);
    }


}

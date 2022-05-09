package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.form.OfferBuyForm;
import ar.edu.itba.paw.cryptuki.form.RatingForm;
import ar.edu.itba.paw.cryptuki.form.TradeForm;
import ar.edu.itba.paw.cryptuki.utils.LastConnectionUtils;
import ar.edu.itba.paw.persistence.*;
import ar.edu.itba.paw.service.OfferService;
import ar.edu.itba.paw.service.RatingService;
import ar.edu.itba.paw.service.TradeService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class TradeFluxController {
    private final OfferService offerService;
    private final TradeService tradeService;
    private final UserService us;

    @Autowired
    public TradeFluxController(OfferService offerService, TradeService tradeService, UserService us) {
        this.offerService = offerService;
        this.tradeService = tradeService;
        this.us = us;
    }

    @RequestMapping(value = "/buy/{offerId}", method = RequestMethod.GET)
    public ModelAndView buyOffer(@PathVariable("offerId") final int offerId,
                                 @ModelAttribute("offerBuyForm") final OfferBuyForm form,
                                 final Authentication authentication){

        ModelAndView mav = new ModelAndView("views/buy_offer");
        Offer offer = offerService.getOfferById(offerId).orElseThrow(RuntimeException::new);
        mav.addObject("offer", offer);
        if( authentication != null ){
            mav.addObject("username", authentication.getName());
            mav.addObject("userEmail", us.getUserInformation(authentication.getName()).get().getEmail());
        }
        mav.addObject("sellerLastLogin", LastConnectionUtils.toRelativeTime(offer.getSeller().getLastLogin()));

        return mav;
    }

    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public ModelAndView buyOffer(@Valid @ModelAttribute("offerBuyForm") final OfferBuyForm form, final BindingResult errors, final Authentication authentication){
        if(errors.hasErrors()){
            return buyOffer(form.getOfferId(), form,authentication);
        }

//        TradeForm tradeForm =  new TradeForm();
//        tradeForm.setAmount(form.getAmount());
//        tradeForm.setOfferId(form.getOfferId());

        return new ModelAndView("redirect:/trade?offerId="+form.getOfferId()+"&amount="+form.getAmount());
//        return executeTrade(tradeForm, authentication);

    }
    @RequestMapping(value="/trade", method = RequestMethod.GET)
    public ModelAndView executeTrade(@RequestParam(value="offerId") Integer offerId,
                                     @RequestParam (value="amount") float amount,
                                     @ModelAttribute("tradeForm") final TradeForm form,
                                     final Authentication authentication){
        ModelAndView mav = new ModelAndView("views/trade");
//        mav.addObject("tradeForm", form);
        Offer offer = offerService.getOfferById(offerId).get();
        mav.addObject("offer", offer);
        mav.addObject("amount", amount);
        mav.addObject("username", authentication == null ? null : authentication.getName());
        mav.addObject("sellerLastLogin", LastConnectionUtils.toRelativeTime(offer.getSeller().getLastLogin()));
        return mav;
    }
    @RequestMapping(value = "/trade", method = RequestMethod.POST)
    public ModelAndView executeTradePost(@Valid @ModelAttribute("tradeForm")  final TradeForm form, final BindingResult errors, final Authentication authentication){
        if(errors.hasErrors()){
            return executeTrade(form.getOfferId(), form.getAmount(), form,authentication);
        }

        Integer tradeId = tradeService.makeTrade(new Trade.Builder(form.getOfferId(),authentication.getName())
                .withQuantity(form.getAmount()));

        return receipt(tradeId,authentication);
    }
    @RequestMapping(value = "/receipt/{tradeId}", method = RequestMethod.GET)
    public ModelAndView receipt(@PathVariable("tradeId") final int tradeId, final Authentication authentication){
        ModelAndView mav = new ModelAndView("views/receipt");
        Optional<Trade> trade = tradeService.getTradeById(tradeId);

        if(!trade.isPresent()){
            return null;
        }

        mav.addObject("trade" , trade.get());
        Offer offer = offerService.getOfferById(trade.get().getOfferId()).get();
        mav.addObject("offer", offer);
        mav.addObject("sellerLastLogin", LastConnectionUtils.toRelativeTime(offer.getSeller().getLastLogin()));


        if(authentication != null){
            mav.addObject("username", authentication.getName());
            User user = us.getUserInformation(authentication.getName()).get();
            mav.addObject("user", user);
            mav.addObject("buyerLastLogin", LastConnectionUtils.toRelativeTime(user.getLastLogin()));
        }
        return mav;
    }



    @RequestMapping(value = "/receiptDescription/{tradeId}", method = RequestMethod.GET)
    public ModelAndView receiptDescription(@ModelAttribute("ratingForm") RatingForm form, @PathVariable("tradeId") final int tradeId, final Authentication authentication){
        ModelAndView mav = new ModelAndView("views/receiptDescription");
        Optional<Trade> trade = tradeService.getTradeById(tradeId);
        if(!trade.isPresent()){
            return null;
        }

        mav.addObject("trade" , trade.get());
        Offer offer = offerService.getOfferById(trade.get().getOfferId()).get();
        mav.addObject("offer", offer);
        mav.addObject("sellerLastLogin", LastConnectionUtils.toRelativeTime(offer.getSeller().getLastLogin()));

        if(authentication != null){
            mav.addObject("username", authentication.getName());
            User user = us.getUserInformation(authentication.getName()).get();
            mav.addObject("user", user);
            mav.addObject("buyerLastLogin", LastConnectionUtils.toRelativeTime(user.getLastLogin()));
        }
        return mav;

    }





}

package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.form.OfferBuyForm;
import ar.edu.itba.paw.cryptuki.utils.LastConnectionUtils;
import ar.edu.itba.paw.exception.NoSuchOfferException;
import ar.edu.itba.paw.exception.NoSuchTradeException;
import ar.edu.itba.paw.persistence.*;
import ar.edu.itba.paw.service.OfferService;
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
    public ModelAndView buyOffer(@PathVariable("offerId") final int offerId, @ModelAttribute("offerBuyForm") final OfferBuyForm form, final Authentication authentication) {

        ModelAndView mav = new ModelAndView("views/buy_offer");

        Optional<Offer> offerOptional =  offerService.getOfferById(offerId);
        if (!offerOptional.isPresent())
            throw new NoSuchOfferException(offerId);

        Offer offer = offerOptional.get();

        mav.addObject("offer", offer);
        mav.addObject("username", authentication.getName());
        mav.addObject("userEmail", us.getUserInformation(authentication.getName()).get().getEmail());
        mav.addObject("sellerLastLogin", LastConnectionUtils.toRelativeTime(offer.getSeller().getLastLogin()));

        return mav;
    }

    @RequestMapping(value = "/buy", method = RequestMethod.GET)
    public ModelAndView buyOffer(@Valid @ModelAttribute("offerBuyForm") final OfferBuyForm offerBuyForm, final BindingResult errors, final Authentication authentication){

        if(errors.hasErrors())
            return buyOffer(offerBuyForm.getOfferId(), offerBuyForm, authentication);

        return executeTrade(offerBuyForm, authentication);
    }


    @RequestMapping(value="/trade", method = RequestMethod.GET)
    public ModelAndView executeTrade( @ModelAttribute("offerBuyForm") final OfferBuyForm offerBuyForm, final Authentication authentication){

        int offerId = offerBuyForm.getOfferId();
        Optional<Offer> offerOptional = offerService.getOfferById(offerId);
        if (!offerOptional.isPresent())
            throw new NoSuchOfferException(offerId);

        Offer offer = offerOptional.get();

        ModelAndView mav = new ModelAndView("views/trade");
        mav.addObject("offer", offer);
        mav.addObject("amount", offerBuyForm.getAmount());
        mav.addObject("username", authentication.getName());
        mav.addObject("offerBuyForm", offerBuyForm);
        mav.addObject("sellerLastLogin", LastConnectionUtils.toRelativeTime(offer.getSeller().getLastLogin()));
        return mav;
    }


    @RequestMapping(value = "/trade", method = RequestMethod.POST)
    public ModelAndView executeTradePost(@Valid @ModelAttribute("offerBuyForm") final OfferBuyForm offerBuyForm, final BindingResult errors, final Authentication authentication){

        if(errors.hasErrors()) {
            return executeTrade(offerBuyForm, authentication);
        }

        int tradeId = tradeService.makeTrade(offerBuyForm.toTradeBuilder(authentication.getName()));
        return new ModelAndView("redirect:/receipt/"+tradeId);
    }

    @RequestMapping(value = "/receipt/{tradeId}", method = RequestMethod.GET)
    public ModelAndView receipt(@PathVariable("tradeId") final int tradeId, final Authentication authentication) {
        return receiptView("views/receipt", tradeId, authentication);
    }

    @RequestMapping(value = "/receiptDescription/{tradeId}", method = RequestMethod.GET)
    public ModelAndView receiptDescription(@PathVariable("tradeId") final int tradeId, final Authentication authentication){
        return receiptView("views/receiptDescription", tradeId, authentication);
    }

    private ModelAndView receiptView(String viewName, int tradeId, Authentication authentication) {
        ModelAndView mav = new ModelAndView(viewName);

        Optional<Trade> tradeOptional = tradeService.getTradeById(tradeId);

        if (!tradeOptional.isPresent())
            throw new NoSuchTradeException(tradeId);

        Trade trade = tradeOptional.get();


        Optional<Offer> offerOptional = offerService.getOfferById(trade.getOfferId());
        if (!offerOptional.isPresent())
            throw new NoSuchOfferException(trade.getOfferId());

        Offer offer = offerOptional.get();

        mav.addObject("trade" , trade);
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

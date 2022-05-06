package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.form.OfferBuyForm;
import ar.edu.itba.paw.cryptuki.form.TradeForm;
import ar.edu.itba.paw.cryptuki.utils.LastConnectionUtils;
import ar.edu.itba.paw.persistence.Offer;
import ar.edu.itba.paw.persistence.Trade;
import ar.edu.itba.paw.service.OfferService;
import ar.edu.itba.paw.service.TradeService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class TradeFluxController {
    private final OfferService offerService;
    private final TradeService tradeService;
    private final UserService us;

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

        TradeForm tradeForm =  new TradeForm();
        tradeForm.setAmount(form.getAmount());
        tradeForm.setOfferId(form.getOfferId());

        return executeTrade(tradeForm, authentication);

    } @RequestMapping(value="/trade", method = RequestMethod.GET)
    public ModelAndView executeTrade(final TradeForm form, final Authentication authentication){
        ModelAndView mav = new ModelAndView("views/trade");
        mav.addObject("tradeForm", form);
        Offer offer = offerService.getOfferById(form.getOfferId()).get();
        mav.addObject("offer", offer);
        mav.addObject("amount", form.getAmount());
        mav.addObject("username", authentication == null ? null : authentication.getName());
        mav.addObject("sellerLastLogin", LastConnectionUtils.toRelativeTime(offer.getSeller().getLastLogin()));
        return mav;
    }
    @RequestMapping(value = "/trade", method = RequestMethod.POST)
    public ModelAndView executeTradePost(@Valid @ModelAttribute("tradeForm")  final TradeForm form, final BindingResult errors, final Authentication authentication){
        if(errors.hasErrors()){
            return executeTrade(form,authentication);
        }
        //inserto el trade
//        tradeService.makeTrade(new Trade.Builder(form.getOfferId(),authentication.getName())
//                .withTradeStatus(TradeStatus.OPEN)
//                .withQuantity(form.getAmount())
//                .withSellerUsername("mdedeu"));

        //restarle el amount
        //mandarle los datos  del comprador al vendedor
        int tradeId= 2;
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
            mav.addObject("user", us.getUserInformation(authentication.getName()).get());
        }
        return mav;
    }
}

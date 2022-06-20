package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.form.*;
import ar.edu.itba.paw.cryptuki.utils.LastConnectionUtils;
import ar.edu.itba.paw.exception.NoSuchOfferException;
import ar.edu.itba.paw.exception.NoSuchTradeException;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.service.ChatService;
import ar.edu.itba.paw.service.OfferService;
import ar.edu.itba.paw.service.TradeService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@Controller
public class TradeFluxController {

    private final OfferService offerService;
    private final TradeService tradeService;
    private final ChatService chatService;
    private final UserService us;
    private final Environment environment;
    private static final int PAGE_SIZE = 7;

    @Autowired
    public TradeFluxController(OfferService offerService, TradeService tradeService, UserService us, ChatService chatService, Environment environment) {
        this.offerService = offerService;
        this.tradeService = tradeService;
        this.chatService = chatService;
        this.us = us;
        this.environment = environment;
    }

    @RequestMapping(value = "/seeOffer/{offerId}", method = RequestMethod.GET)
    public ModelAndView seeOffer(@PathVariable("offerId") final int offerId, @ModelAttribute("offerBuyForm") final OfferBuyForm form, final Authentication authentication) {

        ModelAndView mav = new ModelAndView("buyOffer");
        Offer offer =  offerService.getOfferById(offerId).orElseThrow(()->new NoSuchOfferException(offerId));
        mav.addObject("offer", offer);
        mav.addObject("userEmail", offer.getSeller().getEmail());
        mav.addObject("sellerLastLogin", LastConnectionUtils.toRelativeTime(offer.getSeller().getLastLogin()));
        mav.addObject("apiKey", environment.getProperty("API_KEY"));

        return mav;
    }


    @RequestMapping(value="/trade", method = RequestMethod.GET)
    public ModelAndView executeTrade(@ModelAttribute("messageForm") MessageForm messageForm, Integer tradeId , Authentication authentication){
        Trade trade = tradeService.getTradeById(tradeId).orElseThrow(()->new NoSuchTradeException(tradeId));

        Offer offer = trade.getOffer();
        boolean buying = !offer.getSeller().getUsername().get().equals(authentication.getName());

        if(trade.getStatus() == TradeStatus.SOLD){
            ModelAndView mav = new ModelAndView("redirect:/receiptDescription/"+trade.getTradeId());
            mav.addObject("buying",buying);
            return mav;
        }

        chatService.markBuyerMessagesAsSeen(tradeId);

        ModelAndView mav = new ModelAndView("trade");
        mav.addObject("buying",buying);
        mav.addObject("trade",trade);
        mav.addObject("otherLastLogin",LastConnectionUtils.toRelativeTime(offer.getSeller().getLastLogin()).getRelativeTime());
        mav.addObject("status",trade.getStatus().toString());

        return mav;
    }


    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public ModelAndView executeTradePost(@Valid @ModelAttribute("offerBuyForm") final OfferBuyForm form, final BindingResult errors, final Authentication authentication){

        if(errors.hasErrors()) {
            return seeOffer(form.getOfferId(), form, authentication);
        }

        User user = us.getUserByUsername(authentication.getName()).get(); // Already know that exists
        Trade trade = tradeService.makeTrade(form.getOfferId(), user.getId(), form.getAmount());
        return new ModelAndView("redirect:/trade"+"?tradeId="+trade.getTradeId());
    }

    @RequestMapping(value = "/receiptDescription/{tradeId}", method = RequestMethod.GET)
    public ModelAndView receiptDescription(@ModelAttribute("ratingForm") RatingForm ratingForm, @PathVariable("tradeId") final int tradeId, final Authentication authentication){
        ModelAndView mav = receiptView("receiptDescription", tradeId, authentication);
        mav.addObject("rated", false);
        return mav;
    }

    @RequestMapping(value = "/receiptDescription/{tradeId}/success", method = RequestMethod.GET)
    public ModelAndView receiptDescriptionSuccess(@ModelAttribute("ratingForm") RatingForm ratingForm, @PathVariable("tradeId") final int tradeId, final Authentication authentication){
        ModelAndView mav = receiptView("receiptDescription", tradeId, authentication);
        mav.addObject("rated", true);
        return mav;
    }


    private ModelAndView receiptView(String viewName, int tradeId, Authentication authentication) {
        ModelAndView mav = new ModelAndView(viewName);

        Trade trade = tradeService.getTradeById(tradeId).orElseThrow(()->new NoSuchTradeException(tradeId));
        Offer offer = offerService.getOfferById(trade.getOffer().getOfferId()).orElseThrow(()->new NoSuchOfferException(trade.getOffer().getOfferId()));
        User user = us.getUserByUsername(authentication.getName()).orElseThrow(()->new NoSuchUserException(authentication.getName()));
        boolean buying = !offer.getSeller().getUsername().get().equals(authentication.getName());

        mav.addObject("user", user);
        mav.addObject("trade", trade);
        mav.addObject("offer", offer);
        mav.addObject("buying",buying);
        mav.addObject("otherLastLogin",buying ? LastConnectionUtils.toRelativeTime(offer.getSeller().getLastLogin()) : LastConnectionUtils.toRelativeTime(trade.getBuyer().getLastLogin()));
        mav.addObject("ratedBySeller", trade.isSellerRated());
        mav.addObject("ratedByBuyer", trade.isBuyerRated());
        return mav;
    }


    @RequestMapping(value = "/rate", method = RequestMethod.POST)
    public ModelAndView rate(@Valid @ModelAttribute("ratingForm") RatingForm ratingForm, final  BindingResult errors, final Authentication authentication){
        if(errors.hasErrors()){
            return receiptDescription(ratingForm, ratingForm.getTradeId(), authentication);
        }

        tradeService.rateCounterPartUserRegardingTrade(authentication.getName(), ratingForm.getRating(), ratingForm.getTradeId());
        return new ModelAndView("redirect:/receiptDescription/"+ratingForm.getTradeId()+"/success");
    }

    @RequestMapping(value = "/deleteTrade/{tradeId}", method = RequestMethod.POST)
    public ModelAndView deleteTrade(@PathVariable("tradeId") final int tradeId){
        tradeService.deleteTrade(tradeId);
        return new ModelAndView("redirect:/buyer/");
    }

    @RequestMapping(value="/acceptOffer", method= RequestMethod.POST)
    public ModelAndView acceptOffer(@RequestParam(value = "tradeId") int tradeId, final Authentication authentication) {
        tradeService.acceptTrade(tradeId);
        return new ModelAndView("redirect:/chat?tradeId="+tradeId);
    }
    @RequestMapping(value="/rejectOffer", method= RequestMethod.POST)
    public ModelAndView rejectOffer(@RequestParam(value = "tradeId") int tradeId, final Authentication authentication) {
       Trade trade = tradeService.rejectTrade(tradeId);
        return new ModelAndView("redirect:/seller/associatedTrades/rejected/"+trade.getOffer().getOfferId());
    }
    @RequestMapping(value="/markAsSold", method= RequestMethod.POST)
    public ModelAndView markAsSold(@RequestParam(value = "tradeId") int tradeId, final Authentication authentication) {
        Trade trade = tradeService.sellTrade(tradeId);
        if (!trade.getOffer().getOfferStatus().equals(OfferStatus.SOL))
            return new ModelAndView("redirect:/seller/associatedTrades/completed/"+ trade.getOffer().getOfferId());

        ModelAndView mav = new ModelAndView("offerSold");
        mav.addObject("offerId", trade.getOffer().getOfferId());
        return mav;
    }



}

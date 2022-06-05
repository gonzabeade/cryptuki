package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.form.*;
import ar.edu.itba.paw.cryptuki.utils.LastConnectionUtils;
import ar.edu.itba.paw.exception.NoSuchOfferException;
import ar.edu.itba.paw.exception.NoSuchTradeException;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.persistence.*;
import ar.edu.itba.paw.service.OfferService;
import ar.edu.itba.paw.service.RatingService;
import ar.edu.itba.paw.service.TradeService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.xml.ws.Binding;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Controller
public class TradeFluxController {

    private final OfferService offerService;
    private final TradeService tradeService;
    private final RatingService ratingService;
    private final UserService us;
    private static final int PAGE_SIZE = 7;

    @Autowired
    public TradeFluxController(OfferService offerService, TradeService tradeService, RatingService ratingService, UserService us) {
        this.offerService = offerService;
        this.tradeService = tradeService;
        this.ratingService = ratingService;
        this.us = us;
    }

    @RequestMapping(value = "/buy/{offerId}", method = RequestMethod.GET)
    public ModelAndView buyOffer(@PathVariable("offerId") final int offerId, @ModelAttribute("offerBuyForm") final OfferBuyForm form, final Authentication authentication) {

        ModelAndView mav = new ModelAndView("buyOffer");
        Offer offer =  offerService.getOfferById(offerId).orElseThrow(()->new NoSuchOfferException(offerId));
        mav.addObject("offer", offer);
        mav.addObject("userEmail", offer.getSeller().getEmail());
        mav.addObject("sellerLastLogin", LastConnectionUtils.toRelativeTime(offer.getSeller().getLastLogin()));

        return mav;
    }


    @RequestMapping(value="/trade", method = RequestMethod.GET)
    public ModelAndView executeTrade(final @ModelAttribute("soldTradeForm") SoldTradeForm soldTradeForm, @ModelAttribute("statusTradeForm") StatusTradeForm statusTradeForm, @ModelAttribute("messageForm") MessageForm messageForm, Integer tradeId , Authentication authentication){
        Trade trade = tradeService.getTradeById(tradeId).orElseThrow(()->new NoSuchTradeException(tradeId));

        Offer offer = trade.getOffer();
        boolean buying = !offer.getSeller().getUsername().get().equals(authentication.getName());

        if(trade.getStatus() == TradeStatus.SOLD){
            ModelAndView mav = new ModelAndView("redirect:/receiptDescription/"+trade.getTradeId());
            mav.addObject("buying",buying);
            return mav;
        }

        ModelAndView mav = new ModelAndView("trade");
        mav.addObject("buying",buying);
        mav.addObject("trade",trade);
        mav.addObject("otherLastLogin",LastConnectionUtils.toRelativeTime(offer.getSeller().getLastLogin()).getRelativeTime());
        mav.addObject("status",trade.getStatus().toString());

        return mav;
    }


    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public ModelAndView executeTradePost(@Valid @ModelAttribute("offerBuyForm") final OfferBuyForm offerBuyForm, final BindingResult errors, final Authentication authentication){

        if(errors.hasErrors()) {
            return buyOffer(offerBuyForm.getOfferId(), offerBuyForm, authentication);
        }

        int tradeId = tradeService.makeTrade(offerBuyForm.toTradeBuilder(authentication.getName()));
        return new ModelAndView("redirect:/trade"+"?tradeId="+tradeId);
    }

    @RequestMapping(value = "/receipt/{tradeId}", method = RequestMethod.GET)
    public ModelAndView receipt(@PathVariable("tradeId") final int tradeId, final Authentication authentication) {
        return receiptView("receipt", tradeId, authentication);
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
        Offer offer = offerService.getOfferById(trade.getOfferId()).orElseThrow(()->new NoSuchOfferException(trade.getOfferId()));
        User user = us.getUserInformation(authentication.getName()).orElseThrow(()->new NoSuchUserException(authentication.getName()));
        boolean buying = !offer.getSeller().getUsername().get().equals(authentication.getName());

        mav.addObject("user", user);
        mav.addObject("trade", trade);
        mav.addObject("offer", offer);
        mav.addObject("buying",buying);
        mav.addObject("otherLastLogin",buying ? LastConnectionUtils.toRelativeTime(offer.getSeller().getLastLogin()) : LastConnectionUtils.toRelativeTime(trade.getUser().getLastLogin()));
        mav.addObject("ratedBySeller", trade.getRatedSeller());
        mav.addObject("ratedByBuyer", trade.getRatedBuyer());
        return mav;
    }


    @RequestMapping(value = "/rate", method = RequestMethod.POST)
    public ModelAndView rate(@Valid @ModelAttribute("ratingForm") RatingForm ratingForm, final  BindingResult errors, final Authentication authentication){
        if(errors.hasErrors()){
            return receiptDescription(ratingForm, ratingForm.getTradeId(), authentication);
        }

        ratingService.rate(ratingForm.getTradeId(), authentication.getName(),  ratingForm.getRating());
        return new ModelAndView("redirect:/receiptDescription/"+ratingForm.getTradeId()+"/success");
    }

    @RequestMapping(value = "/mytrades", method = RequestMethod.GET)
    public ModelAndView getMyTrades(Authentication authentication, @RequestParam(value = "page") final Optional<Integer> page, @RequestParam(value = "role", required = false) Optional<String> role, @RequestParam(value = "status", required = false) final Optional<String> status){
        ModelAndView mav = new ModelAndView("tradePage");
        String username = authentication.getName();
        int pageNumber= page.orElse(0);

        if(!role.isPresent()){
            role = Optional.of("buying");
        }
        if((!role.get().equals("buying")&&!role.get().equals("selling")))
            throw new IllegalArgumentException();

        int tradeCount;
        Collection<Trade> tradeList;

        if(role.get().equals("buying")){
            tradeCount= tradeService.getBuyingTradesByUsernameCount(username);
            tradeList = tradeService.getBuyingTradesByUsername(authentication.getName(), pageNumber,PAGE_SIZE);
        }
        else{
            tradeCount = tradeService.getSellingTradesByUsernameCount(username);
            tradeList = tradeService.getSellingTradesByUsername(authentication.getName(), pageNumber,PAGE_SIZE);
        }

        int pages=(tradeCount+PAGE_SIZE-1)/PAGE_SIZE;
        if(tradeList.isEmpty())
            if(role.get().equals("buying"))
                mav.addObject("noBuyingTrades",true);
            else
                mav.addObject("noSellingTrades",true);

        mav.addObject("tradeStatusList",TradeStatus.values());
        mav.addObject("tradeList",tradeList);
        mav.addObject("pages",pages);
        mav.addObject("activePage",pageNumber);
        return mav;
    }


    @RequestMapping(value = "/closeTrade",method = RequestMethod.POST)
    public ModelAndView closeTransaction(final @Valid @ModelAttribute("soldTradeForm") SoldTradeForm soldTradeForm, @ModelAttribute("statusTradeForm") final StatusTradeForm statusTradeForm, final BindingResult errors ,final Authentication authentication){
        if(errors.hasErrors())
            return executeTrade(soldTradeForm,new StatusTradeForm(), new MessageForm(), statusTradeForm.getTradeId(),authentication);
        Trade trade = tradeService.getTradeById(soldTradeForm.getTrade()).orElseThrow(()->new NoSuchTradeException(soldTradeForm.getTrade()));
        Offer offer = trade.getOffer();
        offerService.soldOffer(offer, trade.getQuantity(),trade.getTradeId());
        return new ModelAndView("redirect:/receiptDescription/"+trade.getTradeId());
    }


    @RequestMapping(value = "/deleteTrade/{tradeId}", method = RequestMethod.POST)
    public ModelAndView deleteTrade(@PathVariable("tradeId") final int tradeId){
        tradeService.deleteTrade(tradeId);
        return new ModelAndView("redirect:/buyer/");
    }


}

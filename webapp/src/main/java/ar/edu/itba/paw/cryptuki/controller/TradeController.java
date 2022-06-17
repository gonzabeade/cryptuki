package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.form.SoldTradeForm;
import ar.edu.itba.paw.cryptuki.form.StatusTradeForm;
import ar.edu.itba.paw.exception.NoSuchOfferException;
import ar.edu.itba.paw.model.Trade;
import ar.edu.itba.paw.model.TradeStatus;
import ar.edu.itba.paw.service.OfferService;
import ar.edu.itba.paw.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Optional;

@Controller
@RequestMapping("/seller/associatedTrades/")
public class TradeController {

    private static final int PAGE_SIZE = 6;
    private final TradeService tradeService;
    private final OfferService offerService;

    @Autowired
    public TradeController(TradeService tradeService, OfferService offerService) {
        this.tradeService = tradeService;
        this.offerService = offerService;
    }
    public ModelAndView baseTradesDashboard(@ModelAttribute("statusTradeForm") StatusTradeForm tradeForm,
                                            @ModelAttribute("soldTradeForm") SoldTradeForm soldTradeForm,
                                            Authentication authentication,
                                            Optional<Integer> page,
                                            final int offerId,
                                            TradeStatus status) {

        ModelAndView mav = new ModelAndView("seller/sellerTrade");

        Collection<Trade> trades = tradeService.getTradesAsSeller(authentication.getName(), page.orElse(0), PAGE_SIZE, status);
        long tradeCount = tradeService.getTradesFromOfferCount(authentication.getName(), offerId);
        int pages = (int)(tradeCount + PAGE_SIZE - 1) / PAGE_SIZE;
        int pageNumber = page.orElse(0);

        mav.addObject("offer", offerService.getOfferById(offerId).orElseThrow(()-> new NoSuchOfferException(offerId)));
        mav.addObject("trades", trades);
        mav.addObject("page", page.orElse(0));
        mav.addObject("status", status);

        mav.addObject("pages", pages);
        mav.addObject("activePage", pageNumber);
        return mav;
    }

    @RequestMapping(value = "/{offerId}", method = RequestMethod.GET)
    public ModelAndView tradeRedirect(@PathVariable("offerId") final int offerId){
        return new ModelAndView("redirect:/seller/associatedTrades/pending/" + offerId);
    }

    @RequestMapping(value = "/accepted/{offerId}", method = RequestMethod.GET)
    public ModelAndView trades(@PathVariable("offerId") final int offerId,
                               Authentication authentication,
                               @RequestParam("page") Optional<Integer> page,
                               @ModelAttribute("statusTradeForm") StatusTradeForm tradeForm,
                               @ModelAttribute("soldTradeForm") SoldTradeForm soldTradeForm
                               ){
        return baseTradesDashboard(tradeForm, soldTradeForm, authentication, page, offerId, TradeStatus.ACCEPTED);
    }

    @RequestMapping(value = "/pending/{offerId}", method = RequestMethod.GET)
    public ModelAndView pendingTrades(@PathVariable("offerId") final int offerId,
                                      Authentication authentication,
                                      @RequestParam("page") Optional<Integer> page,
                                      @ModelAttribute("statusTradeForm") StatusTradeForm tradeForm,
                                      @ModelAttribute("soldTradeForm") SoldTradeForm soldTradeForm){
        return baseTradesDashboard(tradeForm, soldTradeForm, authentication, page, offerId, TradeStatus.PENDING);
    }

    @RequestMapping(value = "/deletedByUser/{offerId}", method = RequestMethod.GET)
    public ModelAndView deletedByUserTrades(@PathVariable("offerId") final int offerId,
                                            Authentication authentication,
                                            @RequestParam("page") Optional<Integer> page,
                                            @ModelAttribute("statusTradeForm") StatusTradeForm tradeForm,
                                            @ModelAttribute("soldTradeForm") SoldTradeForm soldTradeForm){
        return baseTradesDashboard(tradeForm, soldTradeForm, authentication, page, offerId, TradeStatus.DELETED);
    }

    @RequestMapping(value = "/rejected/{offerId}", method = RequestMethod.GET)
    public ModelAndView rejectedTrades(@PathVariable("offerId") final int offerId,
                                       Authentication authentication,
                                       @RequestParam("page") Optional<Integer> page,
                                       @ModelAttribute("statusTradeForm") StatusTradeForm tradeForm,
                                       @ModelAttribute("soldTradeForm") SoldTradeForm soldTradeForm){
        return baseTradesDashboard(tradeForm, soldTradeForm, authentication, page, offerId, TradeStatus.REJECTED);
    }


    @RequestMapping(value = "/completed/{offerId}", method = RequestMethod.GET)
    public ModelAndView completedTrades(@PathVariable("offerId") final int offerId,
                                        Authentication authentication,
                                        @RequestParam("page") Optional<Integer> page,
                                        @ModelAttribute("statusTradeForm") StatusTradeForm tradeForm,
                                        @ModelAttribute("soldTradeForm") SoldTradeForm soldTradeForm){
        return baseTradesDashboard(tradeForm, soldTradeForm, authentication, page, offerId, TradeStatus.SOLD);
    }


}

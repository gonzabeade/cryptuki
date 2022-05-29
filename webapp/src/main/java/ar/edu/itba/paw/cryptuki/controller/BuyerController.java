package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.persistence.Trade;
import ar.edu.itba.paw.persistence.TradeStatus;
import ar.edu.itba.paw.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Optional;

@Controller
@RequestMapping("/buyer")
public class BuyerController {

    private final TradeService tradeService;
    private static final int PAGE_SIZE = 5;

    @Autowired
    public BuyerController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView buyer(Authentication authentication, @RequestParam(value = "page") final Optional<Integer> page, @RequestParam(value = "status", required = false) final Optional<String> status){
        ModelAndView mav = new ModelAndView("buyer/buyerIndex");

        String username = authentication.getName();
        int pageNumber= page.orElse(0);

        TradeStatus askedStatus = TradeStatus.valueOf(status.orElse("PENDING"));

        int tradeCount;
        Collection<Trade> tradeList;
        tradeCount= tradeService.getBuyingTradesByUsernameCount(username,askedStatus);
        tradeList = tradeService.getBuyingTradesByUsername(authentication.getName(), pageNumber, PAGE_SIZE, askedStatus);

        int pages = (tradeCount+PAGE_SIZE-1)/PAGE_SIZE;

        mav.addObject("noBuyingTrades", tradeList.isEmpty());
        mav.addObject("tradeStatusList",TradeStatus.values());
        mav.addObject("tradeList",tradeList);
        mav.addObject("pages",pages);
        mav.addObject("activePage",pageNumber);
        return mav;
    }

}

package ar.edu.itba.paw.cryptuki.controller;


import ar.edu.itba.paw.persistence.Trade;
import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.service.TradeService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/chat")
public class ChatController {

    private final UserService userService;
    private final TradeService tradeService;

    @Autowired
    public ChatController(UserService userService, TradeService tradeService) {
        this.userService = userService;
        this.tradeService = tradeService;
    }


    @RequestMapping(value = "/buyer", method = RequestMethod.GET)
    public ModelAndView createComplain(@RequestParam( value = "tradeId", required = true) final Integer tradeId, final Authentication authentication){
        ModelAndView mav = new ModelAndView("chat/chat");
        User otherUser = userService.getUserInformation("gonzabeade").get();
        Trade trade = tradeService.getTradeById(tradeId).get();
        mav.addObject("otherUser", otherUser);
        mav.addObject("trade", trade);
        return mav;


}

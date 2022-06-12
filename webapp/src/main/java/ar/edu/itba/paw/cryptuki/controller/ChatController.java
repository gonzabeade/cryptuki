package ar.edu.itba.paw.cryptuki.controller;


import ar.edu.itba.paw.cryptuki.form.MessageForm;
import ar.edu.itba.paw.cryptuki.form.SoldTradeForm;
import ar.edu.itba.paw.cryptuki.form.StatusTradeForm;
import ar.edu.itba.paw.cryptuki.utils.LastConnectionUtils;
import ar.edu.itba.paw.exception.NoSuchTradeException;
import ar.edu.itba.paw.model.Trade;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.MessageService;
import ar.edu.itba.paw.service.TradeService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
@RequestMapping("/chat")
public class ChatController {

    private final UserService userService;
    private final TradeService tradeService;

    private final MessageService messageService;

    @Autowired
    public ChatController(UserService userService, TradeService tradeService, MessageService messageService) {
        this.userService = userService;
        this.tradeService = tradeService;
        this.messageService = messageService;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView chatPage(@ModelAttribute("messageForm") MessageForm messageForm,
                                 @ModelAttribute("soldTradeForm") SoldTradeForm soldTradeForm,
                                 @ModelAttribute("statusTradeForm") StatusTradeForm statusTradeForm,
                                 @RequestParam( value = "tradeId", required = true) final Integer tradeId, final Authentication authentication) {
        ModelAndView mav = new ModelAndView("chat/chat");
        Trade trade = tradeService.getTradeById(tradeId).orElseThrow(()->new NoSuchTradeException(tradeId));
        User otherUser;
        if (trade.getBuyerUsername().equals(authentication.getName())) {
            otherUser = userService.getUserByUsername(trade.getSellerUsername()).get();
            tradeService.markBuyerMessagesAsSeen(tradeId);
        } else {
            otherUser = userService.getUserByUsername(trade.getBuyerUsername()).get();
            tradeService.markSellerMessagesAsSeen(tradeId);
        }

        mav.addObject("otherUser", otherUser);
        mav.addObject("otherLastLogin", LastConnectionUtils.toRelativeTime(otherUser.getLastLogin()).getRelativeTime());
        mav.addObject("trade", trade);
        return mav;
    }

    @RequestMapping(value="/send",method = RequestMethod.POST)
    public ModelAndView sendMessage(@Valid @ModelAttribute("messageForm") MessageForm messageForm,
                                    BindingResult errors,
                                    @ModelAttribute("soldTradeForm") SoldTradeForm soldTradeForm,
                                    @ModelAttribute("statusTradeForm") StatusTradeForm statusTradeForm,
                                    final Authentication authentication){
        if(errors.hasErrors()){
            return chatPage(messageForm, soldTradeForm, statusTradeForm, messageForm.getTradeId(), authentication);
        }
        messageService.sendMessage(messageForm.getUserId(), messageForm.getTradeId(), messageForm.getMessage());
        return new ModelAndView("redirect:/chat?tradeId="+messageForm.getTradeId());
    }

    @RequestMapping(value="/sendBuyer",method = RequestMethod.POST)
    public ModelAndView sendMessageBuyer(@Valid @ModelAttribute("messageForm") MessageForm messageForm, BindingResult errors, final Authentication authentication){
        if(errors.hasErrors()){
            return new ModelAndView("redirect:/trade?trade="+ messageForm.getTradeId());
        }
        messageService.sendMessage(messageForm.getUserId(), messageForm.getTradeId(), messageForm.getMessage());
        return new ModelAndView("redirect:/trade?tradeId="+messageForm.getTradeId());
    }

}

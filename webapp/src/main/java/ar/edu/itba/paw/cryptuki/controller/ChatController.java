package ar.edu.itba.paw.cryptuki.controller;


import ar.edu.itba.paw.cryptuki.form.MessageForm;
import ar.edu.itba.paw.persistence.Message;
import ar.edu.itba.paw.persistence.Trade;
import ar.edu.itba.paw.persistence.User;
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


@Controller
@RequestMapping("/chat")
public class ChatController {

    private final UserService userService;
    private final TradeService tradeService;

    private final MessageService messageService;

    @Autowired
    public ChatController(UserService userService, TradeService tradeService,MessageService messageService) {
        this.userService = userService;
        this.tradeService = tradeService;
        this.messageService = messageService;
    }


    @RequestMapping(value = "/buyer", method = RequestMethod.GET)
    public ModelAndView createComplain(@RequestParam( value = "tradeId", required = true) final Integer tradeId, final Authentication authentication) {
        ModelAndView mav = new ModelAndView("chat/chat");
        User otherUser = userService.getUserInformation("gonzabeade").get();
        Trade trade = tradeService.getTradeById(tradeId).get();
        mav.addObject("otherUser", otherUser);
        mav.addObject("trade", trade);
        return mav;
    }

    @RequestMapping(value="/send",method = RequestMethod.POST)
    public ModelAndView sendMessage(@ModelAttribute("messageForm")MessageForm messageForm, BindingResult errors){
        if(errors.hasErrors()){
            //TODO: redirect to the chat form again.
        }
        messageService.sendMessage(messageForm.toBuilder());
        //TODO: where does the post redirect
        return new ModelAndView("redirect:/");
    }

}

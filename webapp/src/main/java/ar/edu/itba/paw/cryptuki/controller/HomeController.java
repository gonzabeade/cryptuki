package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.form.*;
import ar.edu.itba.paw.persistence.Offer;
import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.persistence.UserAuth;
import ar.edu.itba.paw.persistence.OfferFilter;
import ar.edu.itba.paw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;


@Controller  /* Requests can be dispatched to this class */
public class HomeController {

    private final UserService us;
    private final OfferService offerService;
    private final ContactService<MailMessage> mailContactService;
    private final CryptocurrencyService cryptocurrencyService;


    @Autowired
    public HomeController(UserService us, OfferService offerService, ContactService<MailMessage> mailContactService, CryptocurrencyService cryptocurrencyService) {
        this.us = us;
        this.offerService = offerService;
        this.mailContactService = mailContactService;
        this.cryptocurrencyService = cryptocurrencyService;
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView helloWorld(@RequestParam(value = "page") final Optional<Integer> page) {

        final ModelAndView mav = new ModelAndView("views/index");/* Load a jsp file */
        int pageNumber = page.orElse(0);


        OfferFilter filter = new OfferFilter().withPageSize(2).fromPage(pageNumber);
        mav.addObject("offerList", offerService.getOfferBy(filter));
        mav.addObject("pages", offerService.countOffersBy(filter)/2);
        mav.addObject("activePage", pageNumber);

        return mav;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public ModelAndView support( @ModelAttribute("supportForm") final SupportForm form ){
        return new ModelAndView("views/contact");
    }
    @RequestMapping(value = "/contact", method = RequestMethod.POST)
    public ModelAndView createTicket(@Valid  @ModelAttribute("supportForm") final SupportForm form, final BindingResult errors){
        if(errors.hasErrors()){
            return support(form);
        }
        //send mail
        String user = form.getEmail();
        String message = form.getMessage();
        MailMessage mailMessage = mailContactService.createMessage(user);
        mailMessage.setBody("Tu consulta: " + message);
        mailMessage.setSubject("Hemos recibido tu consulta");
        mailContactService.sendMessage(mailMessage);

        return new ModelAndView("redirect:/");
    }
    @RequestMapping(value = "/buy/{offerId}", method = RequestMethod.GET)
    public ModelAndView buyOffer(@PathVariable("offerId") final int offerId, @ModelAttribute("offerBuyForm") final OfferBuyForm form){
        ModelAndView mav = new ModelAndView("views/buy_offer");
        mav.addObject("offer", offerService.getOfferById(offerId));
        return mav;
    }
    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public ModelAndView buyOffer(@Valid @ModelAttribute("offerBuyForm") final OfferBuyForm form, final BindingResult errors){
        if(errors.hasErrors()){
            return buyOffer(form.getOfferId(), form);
        }
        String user = form.getEmail();

        String message =  user + " ha demostrado interés en  " + offerService.getOfferById(form.getOfferId()).get().toString();
        message+="\nQuiere comprarte " + form.getAmount() + "ARS";

        message+="\nTambién te dejó un mensaje: " + form.getMessage();
        message+="\nContactalo ya por mail!";
        MailMessage mailMessage = mailContactService.createMessage(offerService.getOfferById(form.getOfferId()).get().getSeller().getEmail());
        mailMessage.setBody(message);

        mailMessage.setSubject("Recibiste una oferta por tu publicación en Cryptuki!");
        mailContactService.sendMessage(mailMessage);

        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/coins") /* When requests come to this path, requests are forwarded to this method*/
    public ModelAndView coins() {
        /*Alter the model (M) alters de view (V) via this Controller (C)*/
        final ModelAndView mav = new ModelAndView("views/coins_page"); /* Load a jsp file */
        mav.addObject("coinList", cryptocurrencyService.getAllCryptocurrencies());
        return mav;
    }


    @RequestMapping(value="/register",method = {RequestMethod.GET})
    public ModelAndView registerGet(@ModelAttribute("registerForm") final RegisterForm form){
        return new ModelAndView("views/register");
    }


    @RequestMapping(value="/register" , method={RequestMethod.POST})
    public ModelAndView register(@Valid @ModelAttribute("registerForm") RegisterForm form , final BindingResult errors){
        if( !form.getPassword().equals(form.getRepeatPassword()) || errors.hasErrors()){
            form.setPassword("");
            form.setRepeatPassword("");
            return registerGet(form);
        }

        Optional<User> maybeUser;
        //TODO: do not pass UserAuth.Builder, User.Builder
        try{
            maybeUser = us.registerUser( new UserAuth.Builder(form.getUsername(), form.getPassword()).role("seller") ,
                    User.builder().email(form.getEmail()).phoneNumber(Integer.parseInt(form.getPhoneNumber()))
            );

        }
        catch(Exception e ){
                //username o mail is already on db.
                System.out.println("username or mail is already on db");
                form = new RegisterForm();
                return registerGet(form);
        }

      return new ModelAndView("redirect:/verifyManual");
    }


    @RequestMapping("/login")
    public ModelAndView login(@RequestParam(value = "error" , required = false) String error) throws IOException {
        if(error != null)
            System.out.println("incorrect username or password");

        return new ModelAndView("views/login");
    }


    @RequestMapping("/verify")
    public ModelAndView verify(@RequestParam(value="user") String username, @RequestParam(value="code") Integer code){

        int validate = us.verifyUser(username,code);
            if(validate != 1){
                System.out.println("Username or code are invalid");
                return new ModelAndView("redirect:/403");
            }
            else
            {
                System.out.println("user has been verified");
                return new ModelAndView("redirect:/");
            }

    }


    @RequestMapping(value="/verifyManual",method = {RequestMethod.GET})
    public ModelAndView verifyManualGet(@ModelAttribute("CodeForm") final CodeForm form){
        return new ModelAndView("views/codeVerification");
    }

//    @RequestMapping(value = "/verifyManual",method = RequestMethod.POST)
//    public ModelAndView verifyManual(@Valid @ModelAttribute("CodeForm") CodeForm form, BindingResult errors){
//       if(errors.hasErrors()){
//           return verifyManualGet(form);
//       }
//
//        int validate = us.verifyUser(this.username, form.getCode());
//        if(validate != 1){
//            return new ModelAndView("redirect:/403");
//        }
//        else
//        {
//            return new ModelAndView("redirect:/");
//        }
//
//    }


    @RequestMapping(value="/passwordRecovery")
    public ModelAndView passwordSendMailGet(@Valid @ModelAttribute("EmailForm") EmailForm form){
        return new ModelAndView("views/ChangePassword");
    }

    @RequestMapping(value = "/passwordRecovery",method = RequestMethod.POST)
    public ModelAndView passwordSendMail(@Valid @ModelAttribute("EmailForm") EmailForm form, BindingResult errors){
        if(errors.hasErrors())
            return passwordSendMailGet(form);
        try{
            us.sendChangePasswordMail(form.getEmail());
        }catch (Exception e ){
            return forbidden();
        }
        return new ModelAndView("views/ChangePasswordMailSent");

    }
    

    @RequestMapping("/403")
    public ModelAndView forbidden() {
        return new ModelAndView("views/403");
    }


}
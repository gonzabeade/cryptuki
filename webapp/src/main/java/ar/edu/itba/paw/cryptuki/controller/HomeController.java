package ar.edu.itba.paw.cryptuki.controller;


import ar.edu.itba.paw.cryptuki.form.*;
import ar.edu.itba.paw.cryptuki.form.OfferBuyForm;
import ar.edu.itba.paw.cryptuki.form.SupportForm;
import ar.edu.itba.paw.cryptuki.form.UploadOfferForm;
import ar.edu.itba.paw.persistence.Offer;
import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.persistence.UserAuth;
import ar.edu.itba.paw.persistence.OfferFilter;
import ar.edu.itba.paw.service.*;
import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;


@Controller  /* Requests can be dispatched to this class */
public class HomeController {

    private final UserService us;
    private final OfferService offerService;
    private final CryptocurrencyService cryptocurrencyService;
    private final SupportService supportService;
    private final TradeService tradeService;

    private static final int PAGE_SIZE = 3;

    @Autowired
    public HomeController(UserService us, OfferService offerService, CryptocurrencyService cryptocurrencyService, SupportService supportService, TradeService tradeService) {
        this.us = us;
        this.offerService = offerService;
        this.supportService = supportService;
        this.cryptocurrencyService = cryptocurrencyService;
        this.tradeService = tradeService;
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView helloWorld(@RequestParam(value = "page") final Optional<Integer> page, final Authentication authentication) {

        final ModelAndView mav = new ModelAndView("views/index");/* Load a jsp file */
        int pageNumber = page.orElse(0);

        OfferFilter filter = new OfferFilter().withPageSize(PAGE_SIZE).fromPage(pageNumber);
        mav.addObject("offerList", offerService.getOfferBy(filter));
        mav.addObject("pages", offerService.countOffersBy(filter)/PAGE_SIZE);
        mav.addObject("activePage", pageNumber);
        mav.addObject("username", authentication == null ? null : authentication.getName());

        return mav;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public ModelAndView support( @ModelAttribute("supportForm") final SupportForm form, final Authentication authentication){
        ModelAndView mav =  new ModelAndView("views/contact");
        mav.addObject("username", authentication == null ? null : authentication.getName());

        return mav;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.POST)
    public ModelAndView createTicket(@Valid  @ModelAttribute("supportForm") final SupportForm form, final BindingResult errors, final Authentication authentication){
        if(errors.hasErrors()){
            return support(form,authentication);
        }

        supportService.getSupportFor(form.toDigest());
        ModelAndView mav = new ModelAndView("redirect:/");
        mav.addObject("username", authentication == null ? null : authentication.getName());

        return mav;

    }


    @RequestMapping(value = "/buy/{offerId}", method = RequestMethod.GET)
    public ModelAndView buyOffer(@PathVariable("offerId") final int offerId, @ModelAttribute("offerBuyForm") final OfferBuyForm form, final Authentication authentication){
        ModelAndView mav = new ModelAndView("views/buy_offer");
        Offer offer = offerService.getOfferById(offerId).orElseThrow(RuntimeException::new);
        mav.addObject("offer", offer);
        mav.addObject("username", authentication == null ? null : authentication.getName());

        return mav;

    }
    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public ModelAndView buyOffer(@Valid @ModelAttribute("offerBuyForm") final OfferBuyForm form, final BindingResult errors, final Authentication authentication){
        if(errors.hasErrors()){
            return buyOffer(form.getOfferId(), form,authentication);
        }
        tradeService.executeTrade(form.toDigest());
        ModelAndView mav = new ModelAndView("redirect:/");
        mav.addObject("username", authentication == null ? null : authentication.getName());

        return mav;

    }

    @RequestMapping(value = "/coins", method = RequestMethod.GET) /* When requests come to this path, requests are forwarded to this method*/
    public ModelAndView coins(final Authentication authentication) {
        /*Alter the model (M) alters de view (V) via this Controller (C)*/
        final ModelAndView mav = new ModelAndView("views/coins_page"); /* Load a jsp file */
        mav.addObject("coinList", cryptocurrencyService.getAllCryptocurrencies());
        mav.addObject("username", authentication == null ? null : authentication.getName());

        return mav;

    }
    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public ModelAndView uploadOffer(@ModelAttribute("uploadOfferForm") final UploadOfferForm form, final Authentication authentication){
        ModelAndView mav = new ModelAndView("views/upload_page");
        mav.addObject("cryptocurrencies", cryptocurrencyService.getAllCryptocurrencies());
        mav.addObject("paymentMethods", cryptocurrencyService.getAllCryptocurrencies());
        mav.addObject("username", authentication == null ? null : authentication.getName());

        return mav;

    }
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView uploadOffer(@Valid @ModelAttribute("uploadOfferForm") final UploadOfferForm form, final BindingResult errors, final Authentication authentication){
        if(errors.hasErrors()){
            return uploadOffer(form,authentication);
        }
        ModelAndView mav = new ModelAndView("redirect:/");
        mav.addObject("username", authentication == null ? null : authentication.getName());

        return mav;

    }


    @RequestMapping(value="/register",method = RequestMethod.GET)
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

      return new ModelAndView("redirect:/verify?user="+form.getUsername());
    }


    @RequestMapping("/login")
    public ModelAndView login(@RequestParam(value = "error" , required = false) String error) throws IOException {
        if(error != null)
            System.out.println("incorrect username or password");

        return new ModelAndView("views/login");
    }
    @RequestMapping(value="/verify",method = {RequestMethod.GET})
    public ModelAndView verify( @ModelAttribute("CodeForm") final CodeForm form, @RequestParam(value = "user") String username){
        ModelAndView mav = new ModelAndView("verify");
        mav.addObject("username", username);
        return mav;
    }

    @RequestMapping(value = "/verify",method = RequestMethod.POST)
    public ModelAndView verify( @Valid @ModelAttribute("CodeForm") CodeForm form, BindingResult errors){
       if(errors.hasErrors()){
           return verify(new CodeForm(), form.getUsername());
       }
       try{
           us.verifyUser(form.getUsername(), form.getCode());
       } catch (RuntimeException e){
           return verify(new CodeForm(), form.getUsername());
       }

       //log in programmatically
        UserAuth current = us.getUserByUsername(form.getUsername()).orElseThrow(()-> new RuntimeException());
        Authentication auth = new UsernamePasswordAuthenticationToken(current,null, Arrays.asList(new SimpleGrantedAuthority(current.getRole())));
        SecurityContextHolder.getContext().setAuthentication(auth);
       return new ModelAndView("redirect:/");

    }


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
            return new ModelAndView("redirect:/errors");
        }
        return new ModelAndView("views/ChangePasswordMailSent");

    }

}
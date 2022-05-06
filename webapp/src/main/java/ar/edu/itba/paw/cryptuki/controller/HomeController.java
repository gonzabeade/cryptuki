package ar.edu.itba.paw.cryptuki.controller;


import ar.edu.itba.paw.ComplainFilter;
import ar.edu.itba.paw.OfferFilter;
import ar.edu.itba.paw.cryptuki.form.*;
import ar.edu.itba.paw.cryptuki.form.OfferBuyForm;
import ar.edu.itba.paw.cryptuki.form.SupportForm;
import ar.edu.itba.paw.cryptuki.form.UploadOfferForm;
import ar.edu.itba.paw.cryptuki.utils.LastConnectionUtils;
import ar.edu.itba.paw.persistence.*;
import ar.edu.itba.paw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;


@Controller  /* Requests can be dispatched to this class */
public class HomeController {

    private final UserService us;
    private final OfferService offerService;
    private final CryptocurrencyService cryptocurrencyService;
    private final SupportService supportService;
    private final TradeService tradeService;
    private final PaymentMethodService paymentMethodService;
    private final ProfilePicService profilePicService;

    private final ComplainService complainService;

    private static final int PAGE_SIZE = 3;



    @Autowired
    public HomeController(UserService us, OfferService offerService, CryptocurrencyService cryptocurrencyService, SupportService supportService, TradeService tradeService, PaymentMethodService paymentMethodService, ProfilePicService profilePicService, ComplainService complainService) {
        this.us = us;
        this.offerService = offerService;
        this.supportService = supportService;
        this.cryptocurrencyService = cryptocurrencyService;
        this.tradeService = tradeService;
        this.paymentMethodService = paymentMethodService;
        this.profilePicService = profilePicService;
        this.complainService = complainService;
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView helloWorld(@RequestParam(value = "page") final Optional<Integer> page, @RequestParam(value = "coin", required = false) final String coin, @RequestParam(value = "pm", required = false) final String paymentMethod, @RequestParam(value = "price", required = false) final Float price, final Authentication authentication) {

        final ModelAndView mav = new ModelAndView("views/index");/* Load a jsp file */
        int pageNumber = page.orElse(0);

        OfferFilter filter = new OfferFilter();
        if(coin != null){
            filter = filter.byCryptoCode(coin);
        }
        if(paymentMethod != null){
            filter = filter.byPaymentMethod(paymentMethod);
        }
        if(price != null){
            filter = filter.byMinPrice(price);
        }
        filter = filter.withPageSize(PAGE_SIZE).fromPage(pageNumber);

        mav.addObject("offerList", offerService.getOfferBy(filter));
        int offerCount = offerService.countOffersBy(filter);
        int pages =  (offerCount + PAGE_SIZE - 1) / PAGE_SIZE;
        mav.addObject("pages", pages);
        mav.addObject("activePage", pageNumber);
        mav.addObject("cryptocurrencies", cryptocurrencyService.getAllCryptocurrencies());
        mav.addObject("paymentMethods", paymentMethodService.getAllPaymentMethods());
        mav.addObject("username", authentication == null ? null : authentication.getName());
        mav.addObject("offerCount", offerCount);
        return mav;
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public ModelAndView support(@ModelAttribute("supportForm") final SupportForm form, final Authentication authentication){
        ModelAndView mav =  new ModelAndView("views/contact");
        mav.addObject("username", authentication == null ? null : authentication.getName());

        return mav;
    }
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView adminHome(@RequestParam(value = "page") final Optional<Integer> page,
                                  @RequestParam(value = "fromDate", required = false) final String fromDate,
                                  @RequestParam(value = "toDate", required = false) final String toDate,
                                  @RequestParam(value = "offerId", required = false) final Double offerId,
                                  @RequestParam(value = "tradeId", required = false) final Double tradeId,
                                  @RequestParam(value = "username", required = false) final Double username,
                                  final Authentication authentication){
        ModelAndView mav = new ModelAndView("views/admin/complaints");
        mav.addObject("title", "Reclamos pendientes");
        mav.addObject("baseUrl", "/admin");
        mav.addObject("username", authentication == null ? null : authentication.getName());

        int offerCount = 10;
        int pageNumber = page.orElse(0);
        int pages =  (offerCount + PAGE_SIZE - 1) / PAGE_SIZE;
        mav.addObject("pages", pages);
        mav.addObject("activePage", pageNumber);

        return mav;
    }
    @RequestMapping(value = "/admin/assigned", method = RequestMethod.GET)
    public ModelAndView assignedComplaints(@RequestParam(value = "page") final Optional<Integer> page,
                                           @RequestParam(value = "fromDate", required = false) final String fromDate,
                                           @RequestParam(value = "toDate", required = false) final String toDate,
                                           @RequestParam(value = "offerId", required = false) final Double offerId,
                                           @RequestParam(value = "tradeId", required = false) final Double tradeId,
                                           @RequestParam(value = "username", required = false) final Double username,
                                           final Authentication authentication){
        ModelAndView mav = new ModelAndView("views/admin/complaints");
        mav.addObject("title", "Reclamos Asignados a mí");
        mav.addObject("username", authentication == null ? null : authentication.getName());
        mav.addObject("baseUrl", "/admin/assigned");

        int offerCount = 10;
        int pageNumber = page.orElse(0);
        int pages =  (offerCount + PAGE_SIZE - 1) / PAGE_SIZE;
        mav.addObject("pages", pages);
        mav.addObject("activePage", pageNumber);

        return mav;
    }
    @RequestMapping(value = "/admin/solved", method = RequestMethod.GET)
    public ModelAndView solvedComplaints(@RequestParam(value = "page") final Optional<Integer> page,
                                         @RequestParam(value = "fromDate", required = false) final String fromDate,
                                         @RequestParam(value = "toDate", required = false) final String toDate,
                                         @RequestParam(value = "offerId", required = false) final Double offerId,
                                         @RequestParam(value = "tradeId", required = false) final Double tradeId,
                                         @RequestParam(value = "username", required = false) final Double username,
                                         final Authentication authentication){
        ModelAndView mav = new ModelAndView("views/admin/complaints");
        mav.addObject("title", "Reclamos resueltos");
        mav.addObject("username", authentication == null ? null : authentication.getName());
        mav.addObject("baseUrl", "/admin/solved");
        int offerCount = 10;
        int pageNumber = page.orElse(0);
        int pages =  (offerCount + PAGE_SIZE - 1) / PAGE_SIZE;
        mav.addObject("pages", pages);
        mav.addObject("activePage", pageNumber);

        return mav;
    }
    @RequestMapping(value = "/admin/profile")
    public ModelAndView adminProfile(final Authentication authentication){
       return null;
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
        if( authentication != null ){
            mav.addObject("username", authentication == null ? null : authentication.getName());
            mav.addObject("userEmail", us.getUserInformation(authentication.getName()).get().getEmail());
        }

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

    }
    @RequestMapping(value="/trade", method = RequestMethod.GET)
    public ModelAndView executeTrade(final TradeForm form, final Authentication authentication){
        ModelAndView mav = new ModelAndView("views/trade");
        mav.addObject("tradeForm", form);
        mav.addObject("offer", offerService.getOfferById(form.getOfferId()).get());
        mav.addObject("amount", form.getAmount());
        mav.addObject("username", authentication == null ? null : authentication.getName());
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
        mav.addObject("offer", offerService.getOfferById(trade.get().getOfferId()).get());

        if(authentication != null){
            mav.addObject("username", authentication.getName());
            mav.addObject("user", us.getUserInformation(authentication.getName()).get());
        }
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
        mav.addObject("paymentMethods", paymentMethodService.getAllPaymentMethods());
        mav.addObject("username", authentication == null ? null : authentication.getName());

        return mav;

    }
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView uploadOffer(@Valid @ModelAttribute("uploadOfferForm") final UploadOfferForm form, final BindingResult errors, final Authentication authentication){
        if(errors.hasErrors()){
            return uploadOffer(form,authentication);
        }

        offerService.makeOffer(form.toOfferDigest(us.getUserInformation(authentication.getName()).get().getId()));

        ModelAndView mav = new ModelAndView("redirect:/");
        mav.addObject("username", authentication == null ? null : authentication.getName());
        return mav;

    }


    @RequestMapping(value="/register",method = RequestMethod.GET)
    public ModelAndView registerGet(@ModelAttribute("registerForm") final RegisterForm form){
        ModelAndView mav =  new ModelAndView("views/register");
        return mav;
    }


    @RequestMapping(value="/register" , method={RequestMethod.POST})
    public ModelAndView register(@Valid @ModelAttribute("registerForm") RegisterForm form , final BindingResult errors){
        if( !form.getPassword().equals(form.getRepeatPassword()) || errors.hasErrors()){
            return registerGet(form);
        }
        try{
            us.registerUser(form.toUserAuthBuilder(), form.toUserBuilder());
        }
        catch(Exception e ){
                errors.addError(new FieldError("registerForm","email","El nombre de usuario o correo electrónico ya fueron utilizados."));
                return registerGet(form);
        }

      return new ModelAndView("redirect:/verify?user="+form.getUsername());
    }


    @RequestMapping("/login")
    public ModelAndView login(@RequestParam(value = "error" , required = false) String error){
        ModelAndView mav = new ModelAndView("views/login");
        if(error != null){
            mav.addObject("error", true);
            return mav;
        }
        return mav;
    }
    @RequestMapping(value="/verify",method = {RequestMethod.GET})
    public ModelAndView verify( @ModelAttribute("CodeForm") final CodeForm form, @RequestParam(value = "user") String username){
        ModelAndView mav = new ModelAndView("views/code_verification");
        mav.addObject("username", username);
        return mav;
    }

    @RequestMapping(value = "/verify",method = RequestMethod.POST)
    public ModelAndView verify( @Valid @ModelAttribute("CodeForm") CodeForm form, BindingResult errors){
       if(errors.hasErrors()){
           return verify(form, form.getUsername());
       }
       try{
           us.verifyUser(form.getUsername(), form.getCode());
       } catch (RuntimeException e){
           errors.addError(new FieldError("CodeForm","code","El código ingresado no es correcto"));
           return verify(form, form.getUsername());
       }

       //log in programmatically

        UserAuth user = us.getUserByUsername(form.getUsername()).orElseThrow(RuntimeException::new);
        org.springframework.security.core.userdetails.User current = new org.springframework.security.core.userdetails.User(form.getUsername(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
        Authentication auth = new UsernamePasswordAuthenticationToken(current,null, Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
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


    @RequestMapping(value = "/profilepic/{username}", method = { RequestMethod.GET})
    public ResponseEntity<byte[]> imageGet(@PathVariable final String username){

        Image image = profilePicService.getProfilePicture(username).orElseThrow( () -> new RuntimeException("Unknown user"));
        return ResponseEntity.ok().contentType(MediaType.valueOf(image.getImageType())).body(image.getBytes());
    }


    @RequestMapping(value="/test", method = {RequestMethod.GET})
    public ModelAndView testGet(@ModelAttribute("ProfilePicForm") ProfilePicForm form){
        return new ModelAndView("views/upload_picture");
    }

    @RequestMapping(value = "/test", method = { RequestMethod.POST })
    public ModelAndView test(@Valid @ModelAttribute("ProfilePicForm") ProfilePicForm form, BindingResult bindingResult) throws IOException {
        profilePicService.uploadProfilePicture("holachau", form.getMultipartFile().getBytes(), form.getMultipartFile().getContentType());
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/caca", method = { RequestMethod.GET })
    public ModelAndView caca() {

        return new ModelAndView("redirect:/");
    }


}
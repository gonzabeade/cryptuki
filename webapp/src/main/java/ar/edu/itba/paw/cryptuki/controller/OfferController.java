package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.form.seller.ModifyOfferForm;
import ar.edu.itba.paw.cryptuki.form.SoldTradeForm;
import ar.edu.itba.paw.cryptuki.form.StatusTradeForm;
import ar.edu.itba.paw.cryptuki.form.seller.UploadOfferForm;
import ar.edu.itba.paw.cryptuki.utils.LastConnectionUtils;
import ar.edu.itba.paw.exception.NoSuchOfferException;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.Location;
import ar.edu.itba.paw.model.Offer;
import ar.edu.itba.paw.model.PaymentMethod;
import ar.edu.itba.paw.model.parameterObject.OfferPO;
import ar.edu.itba.paw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/offer")
public class OfferController {

    private final CryptocurrencyService cryptocurrencyService;
    private  final OfferService offerService;
    private final UserService userService;
    private final UserService us;

    private final TradeService  tradeService;


    @Autowired
    public OfferController(CryptocurrencyService cryptocurrencyService, OfferService offerService, UserService userService, UserService us, TradeService tradeService) {
        this.cryptocurrencyService = cryptocurrencyService;
        this.offerService = offerService;
        this.userService = userService;
        this.us = us;
        this.tradeService = tradeService;
    }

    @RequestMapping(value = "/{offerId}", method = RequestMethod.GET)
    public ModelAndView seeOffer(@PathVariable("offerId") final int offerId, final Authentication authentication, final @ModelAttribute("soldTradeForm") SoldTradeForm soldTradeForm, @ModelAttribute("statusTradeForm") final StatusTradeForm statusTradeForm){
        Offer offer = offerService.getOfferById(offerId).orElseThrow(()->new NoSuchOfferException(offerId));
        return seeOffer(offer, authentication, false, false);
    }

    @RequestMapping(value = "/{offerId}/creationsuccess", method = RequestMethod.GET)
    public ModelAndView seeOfferCreateSuccess(@PathVariable("offerId") final int offerId, final Authentication authentication,final @ModelAttribute("soldTradeForm") SoldTradeForm soldTradeForm, @ModelAttribute("statusTradeForm") final StatusTradeForm statusTradeForm){
        Offer offer = offerService.getOfferIfAuthorized(offerId).orElseThrow(()->new NoSuchOfferException(offerId));
        return seeOffer(offer, authentication, true, false);
    }

    @RequestMapping(value = "/{offerId}/editsuccess", method = RequestMethod.GET)
    public ModelAndView seeOfferEditSuccess(@PathVariable("offerId") final int offerId, final Authentication authentication,final @ModelAttribute("soldTradeForm") SoldTradeForm soldTradeForm, @ModelAttribute("statusTradeForm") final StatusTradeForm statusTradeForm){
        Offer offer = offerService.getOfferIfAuthorized(offerId).orElseThrow(()->new NoSuchOfferException(offerId));
        return seeOffer(offer, authentication, false, true);
    }


    private ModelAndView seeOffer(Offer offer, Authentication authentication, boolean creation, boolean edit) {
        ModelAndView mav = new ModelAndView("seeOffer");
        mav.addObject("offer", offer);
        mav.addObject("sellerLastLogin", LastConnectionUtils.toRelativeTime(offer.getSeller().getLastLogin()));
        mav.addObject("creation", creation);
        mav.addObject("edit", edit);
        mav.addObject("userEmail", us.getUserByUsername(authentication.getName()).orElseThrow(()->new NoSuchUserException(authentication.getName())).getEmail());
         return mav;
    }


    @RequestMapping(value = "/modify/{offerId}", method = RequestMethod.GET)
    public ModelAndView modify(@PathVariable("offerId") final int offerId,
                               @ModelAttribute("modifyOfferForm") final ModifyOfferForm form,
                               final Authentication authentication){

        if(tradeService.getTradesFromOfferCount(authentication.getName(), offerId) > 0){
           return new ModelAndView("unmodifiableOffer");
        }

        Offer offer = offerService.getOfferIfAuthorized(offerId).orElseThrow(()->new NoSuchOfferException(offerId));
        form.fillFromOffer(offer);

        ModelAndView mav = new ModelAndView("modify");
        mav.addObject("offer", offer);
        mav.addObject("cryptocurrencies", cryptocurrencyService.getAllCryptocurrencies());
        mav.addObject("paymentMethods", PaymentMethod.values());
        mav.addObject("selectedCrypto", offer.getCrypto().getCode());
        mav.addObject("location", Location.values());
        return mav;
    }

    @RequestMapping(value = "/modify/{offerId}", method = RequestMethod.POST)
    public ModelAndView modify(@PathVariable("offerId") final int offerId,
                               @Valid @ModelAttribute("modifyOfferForm") final ModifyOfferForm form,
                               final BindingResult errors,
                               final Authentication authentication){

        if(errors.hasErrors())
            return modify(offerId, form, authentication);

        //int id = us.getUserByUsername(authentication.getName()).orElseThrow(()->new NoSuchUserException(authentication.getName())).getId();
        OfferPO offerPO = form.toOfferParameterObject();
        offerService.modifyOffer(offerPO);
        return new ModelAndView("redirect:/offer/"+offerId+"/editsuccess");
    }

    @RequestMapping(value = "/delete/{offerId}", method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable("offerId") final int offerId){
        offerService.deleteOffer(offerId);
        ModelAndView mav = new ModelAndView("deletedOffer");
        return mav;
    }


//    @RequestMapping(value = "/myoffers", method = RequestMethod.GET)
//    public ModelAndView myOffers(@RequestParam("page")final Optional<Integer> page, final Authentication authentication, final @ModelAttribute("soldTradeForm") SoldTradeForm soldTradeForm, @ModelAttribute("statusTradeForm") final StatusTradeForm statusTradeForm){
//        ModelAndView mav = new ModelAndView("myOffers");
//        int pageNumber = page.orElse(0);
//        int offerCount = offerService.countOffersByUsername(authentication.getName());
//        int pages =  (offerCount + PAGE_SIZE - 1) / PAGE_SIZE;
//        Collection<Offer> offers = offerService.getOffersByUsername(authentication.getName() , pageNumber, PAGE_SIZE);
//        if(offers.isEmpty())
//            mav.addObject("noOffers",true);
//
//        mav.addObject("offerList",offers);
//        mav.addObject("pages", pages);
//        mav.addObject("activePage", pageNumber);
//        mav.addObject("userEmail", us.getUserInformation(authentication.getName()).orElseThrow(()->new NoSuchUserException(authentication.getName())).getEmail());
//
//        return mav;
//    }


    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public ModelAndView uploadOffer(@ModelAttribute("uploadOfferForm") UploadOfferForm form, Authentication authentication){
        ModelAndView mav = new ModelAndView("uploadPage");
        int id = userService.getUserByUsername(authentication.getName()).orElseThrow(()->new NoSuchUserException(authentication.getName())).getId();
        form.setSellerId(id);

        mav.addObject("cryptocurrencies", cryptocurrencyService.getAllCryptocurrencies());
        mav.addObject("paymentMethods", PaymentMethod.values());
        mav.addObject("location", Location.values());

        // TODO: Decide whether we will give support for payment methods
        // if (form.getPaymentMethods() != null){
        //    List<String> paymentCodesAlreadySelected = Arrays.asList(form.getPaymentMethods());
        //    mav.addObject("selectedPayments", paymentCodesAlreadySelected);
        // }
        return mav;
    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView uploadOffer(@Valid @ModelAttribute("uploadOfferForm") final UploadOfferForm form, final BindingResult errors, final Authentication authentication ){
        if (errors.hasErrors())
            return uploadOffer(form, authentication);
        Offer offer = offerService.makeOffer(form.toOfferParameterObject());
        return new ModelAndView("redirect:/offer/"+offer.getOfferId()+"/creationsuccess");
    }



}

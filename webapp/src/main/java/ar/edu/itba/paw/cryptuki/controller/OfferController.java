package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.form.seller.ModifyOfferForm;
import ar.edu.itba.paw.cryptuki.form.seller.UploadOfferForm;
import ar.edu.itba.paw.cryptuki.utils.LastConnectionUtils;
import ar.edu.itba.paw.exception.NoSuchOfferException;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.Location;
import ar.edu.itba.paw.model.Offer;
import ar.edu.itba.paw.model.PaymentMethod;
import ar.edu.itba.paw.model.TradeStatus;
import ar.edu.itba.paw.model.parameterObject.OfferPO;
import ar.edu.itba.paw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.EnumSet;
import java.util.Optional;

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
    public ModelAndView seeOffer(@PathVariable("offerId") final int offerId, final Authentication authentication){
        Offer offer = offerService.getOfferById(offerId).orElseThrow(()->new NoSuchOfferException(offerId));
        return seeOffer(offer, authentication, false, false);
    }

    @RequestMapping(value = "/{offerId}/creationsuccess", method = RequestMethod.GET)
    public ModelAndView seeOfferCreateSuccess(@PathVariable("offerId") final int offerId, final Authentication authentication){
        Offer offer = offerService.getOfferIfAuthorized(offerId).orElseThrow(()->new NoSuchOfferException(offerId));
        return seeOffer(offer, authentication, true, false);
    }

    @RequestMapping(value = "/{offerId}/editsuccess", method = RequestMethod.GET)
    public ModelAndView seeOfferEditSuccess(@PathVariable("offerId") final int offerId, final Authentication authentication){
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
    public ModelAndView modify(@PathVariable("offerId") final int offerId, @ModelAttribute("modifyOfferForm") final ModifyOfferForm form,
                               final Authentication authentication){

        if (tradeService.getTradesAsSellerCount(authentication.getName(), EnumSet.allOf(TradeStatus.class), offerId) > 0){
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
        OfferPO offerPO = form.toOfferParameterObject();
        offerService.modifyOffer(offerPO);
        return new ModelAndView("redirect:/offer/"+offerId+"/editsuccess");
    }

    @RequestMapping(value = "/pause/{offerId}", method = RequestMethod.POST)
    public ModelAndView pause(@PathVariable("offerId") final int offerId){
        offerService.sellerPauseOffer(offerId);
        return new ModelAndView("redirect:/seller/");
    }

    @RequestMapping(value = "/resume/{offerId}", method = RequestMethod.POST)
    public ModelAndView resume(@PathVariable("offerId") final int offerId){
        offerService.resumeOffer(offerId);
        return new ModelAndView("redirect:/seller/");
    }

    @RequestMapping(value = "/delete/{offerId}", method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable("offerId") final int offerId){
        offerService.deleteOffer(offerId);
        return new ModelAndView("deletedOffer");
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public ModelAndView uploadOffer(@ModelAttribute("uploadOfferForm") UploadOfferForm form, Authentication authentication, @RequestParam(value = "like", required = false) Optional<Integer> likeId){
        ModelAndView mav = new ModelAndView("uploadPage");
        int id = userService.getUserByUsername(authentication.getName()).orElseThrow(()->new NoSuchUserException(authentication.getName())).getId();

        if (likeId.isPresent()) {
            Offer offer = offerService.getOfferById(likeId.get()).orElseThrow(()->new NoSuchOfferException(likeId.get()));
            form.fillFromOffer(offer);
        }

        mav.addObject("sellerId", id);
        mav.addObject("cryptocurrencies", cryptocurrencyService.getAllCryptocurrencies());
        mav.addObject("paymentMethods", PaymentMethod.values());
        mav.addObject("location", Location.values());
        mav.addObject("selectedCrypto", form.getCryptoCode());

        return mav;
    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView uploadOffer(@Valid @ModelAttribute("uploadOfferForm") final UploadOfferForm form, final BindingResult errors, final Authentication authentication ){
        if (errors.hasErrors())
            return uploadOffer(form, authentication, Optional.empty());
        Offer offer = offerService.makeOffer(form.toOfferParameterObject());
        return new ModelAndView("redirect:/offer/"+offer.getOfferId()+"/creationsuccess");
    }



}

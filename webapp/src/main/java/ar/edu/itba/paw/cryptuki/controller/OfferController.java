package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.form.ModifyOfferForm;
import ar.edu.itba.paw.cryptuki.form.UploadOfferForm;
import ar.edu.itba.paw.cryptuki.utils.LastConnectionUtils;
import ar.edu.itba.paw.persistence.Offer;
import ar.edu.itba.paw.service.CryptocurrencyService;
import ar.edu.itba.paw.service.OfferService;
import ar.edu.itba.paw.service.PaymentMethodService;
import ar.edu.itba.paw.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class OfferController {

    private final CryptocurrencyService cryptocurrencyService;
    private final PaymentMethodService paymentMethodService;
    private  final OfferService offerService;
    private final UserService us;

    @Autowired
    public OfferController(CryptocurrencyService cryptocurrencyService,
                           PaymentMethodService paymentMethodService,
                           OfferService offerService,
                           UserService us) {

        this.cryptocurrencyService = cryptocurrencyService;
        this.paymentMethodService = paymentMethodService;
        this.offerService = offerService;
        this.us = us;
    }



    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public ModelAndView uploadOffer(@ModelAttribute("uploadOfferForm") final UploadOfferForm form, final Authentication authentication){

        ModelAndView mav = new ModelAndView("views/upload_page");
        mav.addObject("cryptocurrencies", cryptocurrencyService.getAllCryptocurrencies());
        mav.addObject("paymentMethods", paymentMethodService.getAllPaymentMethods());
        mav.addObject("username", authentication == null ? null : authentication.getName());
        if(form.getPaymentMethods()!=null){
            List<String> stringList = new ArrayList<>(Arrays.asList(form.getPaymentMethods()));
            mav.addObject("selectedPayments", stringList);
        }


        return mav;

    }
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView uploadOffer(@Valid @ModelAttribute("uploadOfferForm") final UploadOfferForm form, final BindingResult errors, final Authentication authentication){

        if(errors.hasErrors()){
            return uploadOffer(form,authentication);
        }

        int offerId = offerService.makeOffer(form.toOfferDigest(us.getUserInformation(authentication.getName()).get().getId()));
        return seeOffer(offerId,authentication, true,false);

    }
    @RequestMapping(value = "/modify/{offerId}", method = RequestMethod.GET)
    public ModelAndView modify(@PathVariable("offerId") final int offerId,
                               @ModelAttribute("modifyOfferForm") final ModifyOfferForm form,
                               final Authentication authentication){

        Offer offer = checkOfferPermissionAndGetOffer(offerId,authentication);

        if(offer == null){
            return new ModelAndView("redirect:/errors");
        }

        ModelAndView mav = new ModelAndView("views/modify");

        form.setMinAmount(offer.getMinQuantity());
        form.setMaxAmount(offer.getMaxQuantity());
        form.setCryptocurrency(offer.getCrypto().getCode());
        form.setPrice(offer.getAskingPrice());
        form.setMessage(offer.getComments());

        ArrayList<String> pm =  new ArrayList<>();
        offer.getPaymentMethods().forEach(paymentMethod -> pm.add(paymentMethod.getName()));
        form.setPaymentMethods(pm.toArray(new String[0]));

        mav.addObject("offer", offer);
        mav.addObject("username", authentication == null ? null : authentication.getName());
        mav.addObject("cryptocurrencies", cryptocurrencyService.getAllCryptocurrencies());
        mav.addObject("paymentMethods", paymentMethodService.getAllPaymentMethods());
        mav.addObject("selectedCrypto", offer.getCrypto().getCode());
        mav.addObject("selectedPayments", pm);

        return mav;
    }
    @RequestMapping(value = "/modify/{offerId}", method = RequestMethod.POST)
    public ModelAndView modify(@PathVariable("offerId") final int offerId,
                               @Valid @ModelAttribute("modifyOfferForm") final ModifyOfferForm form,
                               final BindingResult errors,
                               final Authentication authentication){

        if(errors.hasErrors()){
            return modify(offerId, form, authentication);
        }
        Offer offer = checkOfferPermissionAndGetOffer(offerId,authentication);
        if(offer == null){
            return new ModelAndView("redirect:/errors");
        }
        offerService.modifyOffer(form.toOfferDigest(us.getUserInformation(authentication.getName()).get().getId()));
        return seeOffer(offer.getId(),authentication,false,true);
    }
    @RequestMapping(value = "/delete/{offerId}", method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable("offerId") final int offerId,
                               final Authentication authentication){
        Offer offer = checkOfferPermissionAndGetOffer(offerId,authentication);
        if(offer == null){
            return new ModelAndView("redirect:/errors");
        }
        offerService.deleteOffer(offerId);
        ModelAndView mav = new ModelAndView("views/deleted_offer");
        if(null != authentication){
            mav.addObject("username", authentication.getName());
        }
        return mav;
    }

    @RequestMapping(value = "/offer/{offerId}", method = RequestMethod.GET)
    public  ModelAndView seeOffer(@PathVariable("offerId") final int offerId,
                                  final Authentication authentication,
                                  @RequestParam(value = "creation", required = false) boolean creation,
                                  @RequestParam(value = "edit", required = false) boolean edit){

        Optional<Offer> offer = offerService.getOfferById(offerId);
        if( !offer.isPresent()) {
            return null;
        }

        ModelAndView mav = new ModelAndView("views/see_offer");
        mav.addObject("offer", offer.get());
        mav.addObject("sellerLastLogin", LastConnectionUtils.toRelativeTime(offer.get().getSeller().getLastLogin()));
        mav.addObject("creation", creation);
        mav.addObject("edit", edit);
        if(null != authentication){
            mav.addObject("username", authentication.getName());
            mav.addObject("userEmail", us.getUserInformation(authentication.getName()).get().getEmail());
            mav.addObject("isAdmin", authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN")));
        }
        return mav;
    }
    private Offer checkOfferPermissionAndGetOffer(int offerId, final Authentication authentication){
        Offer offer = offerService.getOfferById(offerId).orElseThrow(RuntimeException::new);
        if(authentication !=null){
            if(!(offer.getSeller().getEmail().equals(us.getUserInformation(authentication.getName()).get().getEmail())) && authentication.getAuthorities().stream().noneMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))){
                return null;
            }
        }

        return offer;
    }
}

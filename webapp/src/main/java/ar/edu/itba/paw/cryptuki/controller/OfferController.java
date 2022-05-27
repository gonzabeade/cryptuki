package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.OfferDigest;
import ar.edu.itba.paw.cryptuki.form.ModifyOfferForm;
import ar.edu.itba.paw.cryptuki.form.UploadOfferForm;
import ar.edu.itba.paw.cryptuki.utils.LastConnectionUtils;
import ar.edu.itba.paw.exception.NoSuchOfferException;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.persistence.Offer;
import ar.edu.itba.paw.service.CryptocurrencyService;
import ar.edu.itba.paw.service.OfferService;
import ar.edu.itba.paw.service.PaymentMethodService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Controller
public class OfferController {

    private final CryptocurrencyService cryptocurrencyService;
    private final PaymentMethodService paymentMethodService;
    private  final OfferService offerService;
    private final UserService us;
    private static final int PAGE_SIZE= 10;



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
        ModelAndView mav = new ModelAndView("uploadPage");
        mav.addObject("cryptocurrencies", cryptocurrencyService.getAllCryptocurrencies());
        mav.addObject("paymentMethods", paymentMethodService.getAllPaymentMethods());


        if (form.getPaymentMethods() != null){
            List<String> paymentCodesAlreadySelected = Arrays.asList(form.getPaymentMethods());
            mav.addObject("selectedPayments", paymentCodesAlreadySelected);
        }

        return mav;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView uploadOffer(@Valid @ModelAttribute("uploadOfferForm") final UploadOfferForm form, final BindingResult errors, final Authentication authentication){

        if (errors.hasErrors())
            return uploadOffer(form, authentication);

        int id = us.getUserInformation(authentication.getName()).orElseThrow(()->new NoSuchUserException(authentication.getName())).getId();
        int offerId = offerService.makeOffer(form.toOfferDigest(id));
        return new ModelAndView("redirect:/offer/"+offerId+"/creationsuccess");
    }

    @RequestMapping(value = "/offer/{offerId}", method = RequestMethod.GET)
    public ModelAndView seeOffer(@PathVariable("offerId") final int offerId, final Authentication authentication){
        Offer offer = offerService.getOfferById(offerId).orElseThrow(()->new NoSuchOfferException(offerId));
        return seeOffer(offer, authentication, false, false);
    }

    @RequestMapping(value = "/offer/{offerId}/creationsuccess", method = RequestMethod.GET)
    public ModelAndView seeOfferCreateSuccess(@PathVariable("offerId") final int offerId, final Authentication authentication){
        Offer offer = offerService.getOfferIfAuthorized(offerId).orElseThrow(()->new NoSuchOfferException(offerId));
        return seeOffer(offer, authentication, true, false);
    }

    @RequestMapping(value = "/offer/{offerId}/editsuccess", method = RequestMethod.GET)
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
        mav.addObject("userEmail", us.getUserInformation(authentication.getName()).orElseThrow(()->new NoSuchUserException(authentication.getName())).getEmail());
         return mav;
    }


    @RequestMapping(value = "/modify/{offerId}", method = RequestMethod.GET)
    public ModelAndView modify(@PathVariable("offerId") final int offerId,
                               @ModelAttribute("modifyOfferForm") final ModifyOfferForm form,
                               final Authentication authentication){

        Offer offer = offerService.getOfferIfAuthorized(offerId).orElseThrow(()->new NoSuchOfferException(offerId));
        form.fillFromOffer(offer);

        ModelAndView mav = new ModelAndView("modify");
        mav.addObject("offer", offer);
        mav.addObject("cryptocurrencies", cryptocurrencyService.getAllCryptocurrencies());
        mav.addObject("paymentMethods", paymentMethodService.getAllPaymentMethods());
        mav.addObject("selectedCrypto", offer.getCrypto().getCode());
        mav.addObject("selectedPayments", Arrays.asList(form.getPaymentMethods()));
        return mav;
    }

    @RequestMapping(value = "/modify/{offerId}", method = RequestMethod.POST)
    public ModelAndView modify(@PathVariable("offerId") final int offerId,
                               @Valid @ModelAttribute("modifyOfferForm") final ModifyOfferForm form,
                               final BindingResult errors,
                               final Authentication authentication){

        if(errors.hasErrors())
            return modify(offerId, form, authentication);

        int id = us.getUserInformation(authentication.getName()).orElseThrow(()->new NoSuchUserException(authentication.getName())).getId();
        OfferDigest digest = form.toOfferDigest(id);
        offerService.modifyOffer(digest);
        return new ModelAndView("redirect:/offer/"+offerId+"/editsuccess");
    }

    @RequestMapping(value = "/delete/{offerId}", method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable("offerId") final int offerId){
        offerService.deleteOffer(offerId);
        ModelAndView mav = new ModelAndView("deletedOffer");
        return mav;
    }


    @RequestMapping(value = "/myoffers", method = RequestMethod.GET)
    public ModelAndView myOffers(@RequestParam("page")final Optional<Integer> page, final Authentication authentication){
        ModelAndView mav = new ModelAndView("myOffers");
        int pageNumber = page.orElse(0);
        int offerCount = offerService.countOffersByUsername(authentication.getName());
        int pages =  (offerCount + PAGE_SIZE - 1) / PAGE_SIZE;
        Collection<Offer> offers = offerService.getOffersByUsername(authentication.getName() , pageNumber, PAGE_SIZE);
        if(offers.isEmpty())
            mav.addObject("noOffers",true);

        mav.addObject("offerList",offers);
        mav.addObject("pages", pages);
        mav.addObject("activePage", pageNumber);
        mav.addObject("userEmail", us.getUserInformation(authentication.getName()).orElseThrow(()->new NoSuchUserException(authentication.getName())).getEmail());

        return mav;
    }

}

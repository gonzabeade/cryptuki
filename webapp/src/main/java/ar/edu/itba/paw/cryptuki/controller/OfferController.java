package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.OfferDigest;
import ar.edu.itba.paw.cryptuki.form.ModifyOfferForm;
import ar.edu.itba.paw.cryptuki.form.UploadOfferForm;
import ar.edu.itba.paw.cryptuki.utils.LastConnectionUtils;
import ar.edu.itba.paw.exception.NoSuchOfferException;
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
import java.util.List;
import java.util.Optional;

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
        ModelAndView mav = new ModelAndView("upload_page");
        mav.addObject("cryptocurrencies", cryptocurrencyService.getAllCryptocurrencies());
        mav.addObject("paymentMethods", paymentMethodService.getAllPaymentMethods());
        mav.addObject("username", authentication.getName());

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

        int id = us.getUserInformation(authentication.getName()).get().getId();
        int offerId = offerService.makeOffer(form.toOfferDigest(id));
        return new ModelAndView("redirect:/offer/"+offerId+"/creationsuccess");
    }

    @RequestMapping(value = "/offer/{offerId}", method = RequestMethod.GET)
    public ModelAndView seeOffer(@PathVariable("offerId") final int offerId, final Authentication authentication){

        Optional<Offer> offer = offerService.getOfferById(offerId);
        if (!offer.isPresent())
            throw new NoSuchOfferException(offerId);

        return seeOffer(offer.get(), authentication, false, false);
    }

    @RequestMapping(value = "/offer/{offerId}/creationsuccess", method = RequestMethod.GET)
    public ModelAndView seeOfferCreateSuccess(@PathVariable("offerId") final int offerId, final Authentication authentication){
        Offer offer = offerService.getOfferIfAuthorized(offerId).get();
        return seeOffer(offer, authentication, true, false);
    }

    @RequestMapping(value = "/offer/{offerId}/editsuccess", method = RequestMethod.GET)
    public ModelAndView seeOfferEditSuccess(@PathVariable("offerId") final int offerId, final Authentication authentication){
        Offer offer = offerService.getOfferIfAuthorized(offerId).get();
        return seeOffer(offer, authentication, false, true);
    }


    private ModelAndView seeOffer(Offer offer, Authentication authentication, boolean creation, boolean edit) {
        ModelAndView mav = new ModelAndView("see_offer");
        mav.addObject("offer", offer);
        mav.addObject("sellerLastLogin", LastConnectionUtils.toRelativeTime(offer.getSeller().getLastLogin()));
        mav.addObject("creation", creation);
        mav.addObject("edit", edit);
        mav.addObject("username", authentication.getName());
        mav.addObject("userEmail", us.getUserInformation(authentication.getName()).get().getEmail());
        mav.addObject("isAdmin", authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN")));
        return mav;
    }


    @RequestMapping(value = "/modify/{offerId}", method = RequestMethod.GET)
    public ModelAndView modify(@PathVariable("offerId") final int offerId,
                               @ModelAttribute("modifyOfferForm") final ModifyOfferForm form,
                               final Authentication authentication){

        Optional<Offer> offerOptional = offerService.getOfferIfAuthorized(offerId);

        if (!offerOptional.isPresent())
            throw new NoSuchOfferException(offerId);

        Offer offer = offerOptional.get();
        form.fillFromOffer(offer);

        ModelAndView mav = new ModelAndView("modify");
        mav.addObject("offer", offer);
        mav.addObject("username", authentication.getName());
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

        int id = us.getUserInformation(authentication.getName()).get().getId();
        OfferDigest digest = form.toOfferDigest(id);
        offerService.modifyOffer(digest);
        return new ModelAndView("redirect:/offer/"+offerId+"/editsuccess");
    }

    @RequestMapping(value = "/delete/{offerId}", method = RequestMethod.POST)
    public ModelAndView delete(@PathVariable("offerId") final int offerId,
                               final Authentication authentication){
        offerService.deleteOffer(offerId);
        ModelAndView mav = new ModelAndView("deleted_offer");
        mav.addObject("username", authentication.getName());
        return mav;
    }


    @RequestMapping(value = "/myoffers", method = RequestMethod.GET)
    public ModelAndView myOffers(@RequestParam("page")final Optional<Integer> page, final Authentication authentication){
        ModelAndView mav = new ModelAndView("my_offers");
        int pageNumber = page.orElse(0);
        int offerCount = offerService.countOffersByUsername(authentication.getName());
        int pages =  (offerCount + PAGE_SIZE - 1) / PAGE_SIZE;

        mav.addObject("offerList", offerService.getOffersByUsername(authentication.getName(), PAGE_SIZE));
        mav.addObject("pages", pages);
        mav.addObject("activePage", pageNumber);
        mav.addObject("username",  authentication.getName());
        mav.addObject("userEmail", us.getUserInformation(authentication.getName()).get().getEmail());
        mav.addObject("isAdmin", authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN")));

        return mav;
    }

}

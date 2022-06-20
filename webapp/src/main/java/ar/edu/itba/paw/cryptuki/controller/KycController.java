package ar.edu.itba.paw.cryptuki.controller;
import ar.edu.itba.paw.model.IdType;
import ar.edu.itba.paw.cryptuki.form.seller.KycForm;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.KycInformation;
import ar.edu.itba.paw.service.KycService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/kyc")
public class KycController {

    private final KycService kycService;
    @Autowired
    public KycController(final KycService kycService) {
        this.kycService = kycService;
    }

    @RequestMapping(value ="", method = {RequestMethod.GET})
    private ModelAndView kyc(@ModelAttribute("kycForm") final KycForm form, Authentication authentication) {

        if ( kycService.getPendingKycRequest(authentication.getName()).isPresent() )
            return new ModelAndView("redirect:/kyc/success");

        ModelAndView mav = new ModelAndView("kyc/kyc");
        mav.addObject("idTypes", IdType.values());
        mav.addObject("countries", kycService.getAllCountries());
        return mav;
    }

    @RequestMapping(value ="/success", method = {RequestMethod.GET})
    private ModelAndView kycSuccess() {
        return new ModelAndView("kyc/kycSuccess");
    }

    @RequestMapping(value ="", method = {RequestMethod.POST})
    private ModelAndView kycPost(@Valid @ModelAttribute("kycForm") final KycForm form, final BindingResult errors, Authentication authentication) throws IOException {
        if (errors.hasErrors()) {
            return kyc(form, authentication);
        }

        kycService.newKycRequest(form.toParameterObject());
        return new ModelAndView("redirect:/kyc/success");
    }

    @RequestMapping(value = "/validationphoto/{username}", method = { RequestMethod.GET})
    public ResponseEntity<byte[]> validationPhoto(@PathVariable final String username) {
        Optional<KycInformation> maybeKyc = kycService.getPendingKycRequest(username);
        KycInformation kyc = maybeKyc.orElseThrow(()->new NoSuchUserException(username));
        return ResponseEntity.ok().contentType(MediaType.valueOf(kyc.getValidationPhotoType())).body(kyc.getValidationPhoto());
    }

    @RequestMapping(value = "/idphoto/{username}", method = { RequestMethod.GET})
    public ResponseEntity<byte[]> idPhoto(@PathVariable final String username) {
        Optional<KycInformation> maybeKyc = kycService.getPendingKycRequest(username);
        KycInformation kyc = maybeKyc.orElseThrow(()->new NoSuchUserException(username));
        return ResponseEntity.ok().contentType(MediaType.valueOf(kyc.getIdPhotoType())).body(kyc.getIdPhoto());
    }

}


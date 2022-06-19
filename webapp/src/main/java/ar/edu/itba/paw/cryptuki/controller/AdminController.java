package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.form.admin.KycApprovalForm;
import ar.edu.itba.paw.cryptuki.form.admin.SolveComplainForm;
import ar.edu.itba.paw.cryptuki.form.admin.ComplainFilterForm;
import ar.edu.itba.paw.exception.NoSuchComplainException;
import ar.edu.itba.paw.exception.NoSuchKycException;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.service.ComplainService;
import ar.edu.itba.paw.service.KycService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final int COMPLAIN_PAGE_SIZE = 5;
    private static final int KYC_PAGE_SIZE = 10;
    private final ComplainService complainService;
    private final KycService kycService;

    @Autowired
    public AdminController(KycService kycService, ComplainService complainService) {
        this.complainService = complainService;
        this.kycService = kycService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView adminHome(@RequestParam("page") Optional<Integer> page, @Valid ComplainFilterForm complainFilterForm){

        ModelAndView mav = new ModelAndView("admin/complaints");
        ComplainFilter filter = complainFilterForm.toComplainFilter()
                .withPage(page.orElse(0))
                .withPageSize(COMPLAIN_PAGE_SIZE);

        long complainCount = complainService.countComplainsBy(filter);
        Collection<Complain> complainCollection = complainService.getComplainsBy(filter);
        int pageNumber = filter.getPage();
        long pages =  (complainCount + COMPLAIN_PAGE_SIZE - 1) / COMPLAIN_PAGE_SIZE;

        mav.addObject("baseUrl", "/admin");
        mav.addObject("pages", pages);
        mav.addObject("activePage", pageNumber);
        mav.addObject("complainList", complainCollection);
        return mav;
    }

    @RequestMapping(value = "/complaint/{complaintId}", method = RequestMethod.GET)
    public ModelAndView complaintDetail(@PathVariable(value = "complaintId") final int complaintId, @ModelAttribute("solveComplainFormKickout") SolveComplainForm solveComplainFormKickout, @ModelAttribute("solveComplainFormDismiss") SolveComplainForm solveComplainFormDismiss){
        Complain complain = complainService.getComplainById(complaintId).orElseThrow(()->new NoSuchComplainException(complaintId));
        User complainer = complain.getComplainer();
        Trade trade = complain.getTrade();
        ModelAndView mav = new ModelAndView("admin/complaint");
        mav.addObject("trade", trade);
        mav.addObject("complain", complain);
        mav.addObject("complainer", complainer);
        return mav;
    }

    @RequestMapping(value = "/complaint/kickout/{complaintId}", method = RequestMethod.POST)
    public ModelAndView solveComplaintKickout(@Valid @ModelAttribute("solveComplainFormKickout") SolveComplainForm form, BindingResult result, @PathVariable(value = "complaintId") final int complaintId, @RequestParam(value = "user") final int kickedUserId, Authentication authentication){
        if(result.hasErrors())
            return complaintDetail(complaintId, form, new SolveComplainForm());
        complainService.closeComplainWithKickout(complaintId, authentication.getName(), form.getComments(), kickedUserId);
        return new ModelAndView("redirect:/admin?success");
    }

    @RequestMapping(value = "/complaint/dismiss/{complaintId}", method = RequestMethod.POST)
    public ModelAndView solveComplaintDismiss(@Valid @ModelAttribute("solveComplaintFormDismiss") SolveComplainForm form, final BindingResult result, @PathVariable(value = "complaintId") final int complaintId, Authentication authentication){
        if(result.hasErrors())
            return complaintDetail(complaintId, new SolveComplainForm(), form);
        complainService.closeComplainWithDismiss(complaintId, authentication.getName(), form.getComments());
        return new ModelAndView("redirect:/admin?success");
    }

    @RequestMapping(value = "/kyccheck", method = RequestMethod.GET)
    public ModelAndView kycCheckHome(@RequestParam("page") Optional<Integer> page, @ModelAttribute("kycApprovalForm") KycApprovalForm kycApprovalForm) {
        ModelAndView mav = new ModelAndView("admin/kycAll");

        long kycRequestsCount = kycService.getPendingKycRequestsCount();
        long pages =  (kycRequestsCount + KYC_PAGE_SIZE - 1) / KYC_PAGE_SIZE;
        int pageNumber = page.orElse(0);
        Collection<KycInformation> pendingKycs = kycService.getPendingKycRequests(pageNumber, KYC_PAGE_SIZE);
        mav.addObject("pendingKycs", pendingKycs);
        mav.addObject("activePage", pageNumber);
        mav.addObject("pages", pages);
        return mav;
    }

    @RequestMapping(value = "/kyccheck/{username}", method = RequestMethod.GET)
    public ModelAndView kycCheckGet(@ModelAttribute("kycApprovalForm") KycApprovalForm kycApprovalForm, @PathVariable(value = "username") String username) {
        ModelAndView modelAndView = new ModelAndView("admin/kycProfile");
        Optional<KycInformation> maybeKyc = kycService.getPendingKycRequest(username);
        KycInformation kyc = maybeKyc.orElseThrow(()-> new NoSuchKycException(username));
        modelAndView.addObject("kyc", kyc);
        return modelAndView;
    }

    @RequestMapping(value ="/kyccheck/approve/{kycid}", method = RequestMethod.POST)
    public ModelAndView kycApprovePost(@PathVariable(value = "kycid") final int kycId){
        kycService.validateKycRequest(kycId);
        return new ModelAndView("redirect:/admin/kyccheck?success");
    }

    @RequestMapping(value ="/kyccheck/reject/{kycid}", method = RequestMethod.POST)
    public ModelAndView kycRejectPost(@Valid @ModelAttribute("kycApprovalForm") KycApprovalForm kycApprovalForm, final BindingResult errors, @PathVariable(value = "kycid") final int kycId){
        if(errors.hasErrors())
            return kycCheckGet(kycApprovalForm, kycApprovalForm.getUsername());
        kycService.rejectKycRequest(kycId, kycApprovalForm.getMessage());
        return new ModelAndView("redirect:/admin/kyccheck?success");
    }

}

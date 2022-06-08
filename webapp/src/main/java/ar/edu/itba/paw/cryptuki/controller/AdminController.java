package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.ComplainFilter;
import ar.edu.itba.paw.cryptuki.form.KycApprovalForm;
import ar.edu.itba.paw.cryptuki.form.SolveComplainForm;
import ar.edu.itba.paw.cryptuki.form.admin.ComplainFilterResult;
import ar.edu.itba.paw.exception.NoSuchComplainException;
import ar.edu.itba.paw.exception.NoSuchKycException;
import ar.edu.itba.paw.exception.NoSuchTradeException;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.persistence.*;
import ar.edu.itba.paw.service.ComplainService;
import ar.edu.itba.paw.service.KycService;
import ar.edu.itba.paw.service.TradeService;
import ar.edu.itba.paw.service.UserService;
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

    private static final int PAGE_SIZE = 5;

    private static final int KYC_PAGE_SIZE = 10;
    private final ComplainService complainService;
    private final UserService userService;
    private final TradeService tradeService;

    private final KycService kycService;

    @Autowired
    public AdminController(KycService kycService, ComplainService complainService, UserService userService, TradeService tradeService) {
        this.complainService = complainService;
        this.userService = userService;
        this.tradeService = tradeService;
        this.kycService = kycService;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView adminHome(@RequestParam("page") Optional<Integer> page, @Valid ComplainFilterResult complainFilterResult, BindingResult result ){
        ComplainFilter.Builder builder = complainFilterResult.toComplainFilterBuilder(page,PAGE_SIZE,ComplainStatus.PENDING);
        ComplainFilter filter = builder.build();
        return getComplaintsByFilter(filter,"admin/complaints","/admin", "pendingClaims");
    }


    @RequestMapping(value = "/assigned", method = RequestMethod.GET)
    public ModelAndView assignedComplains(@RequestParam("page") Optional<Integer> page, ComplainFilterResult complainFilterResult, final Authentication authentication){
        ComplainFilter.Builder builder = complainFilterResult.toComplainFilterBuilder(page,PAGE_SIZE,ComplainStatus.ASSIGNED);
        ComplainFilter filter = builder
                .withModeratorUsername(authentication.getName())
                .build();
        return getComplaintsByFilter(filter,"admin/complaints","/admin/assigned", "self-assignedClaims");
    }
    @RequestMapping(value = "/solved", method = RequestMethod.GET)
    public ModelAndView solvedComplains(@RequestParam("page") Optional<Integer> page, ComplainFilterResult complainFilterResult){
        ComplainFilter.Builder builder = complainFilterResult.toComplainFilterBuilder(page,PAGE_SIZE,ComplainStatus.CLOSED);
        ComplainFilter filter = builder
                .build();
        return getComplaintsByFilter(filter,"admin/complaints","/admin/solved", "solvedClaims");
    }

    @RequestMapping(value = "/complaint/{complaintId}", method = RequestMethod.GET)
    public ModelAndView complaintDetail(@PathVariable(value = "complaintId") final int complaintId){
        return setUpComplaintView("admin/complaint",complaintId);
    }

    @RequestMapping(value = "/solve/{complaintId}", method = RequestMethod.GET)
    public ModelAndView solveComplaint(@ModelAttribute("solveComplaintForm") SolveComplainForm form, @PathVariable(value = "complaintId") final int complaintId){
        return setUpComplaintView("admin/solveComplaint",complaintId);
    }

    @RequestMapping(value = "/solve/{complaintId}", method = RequestMethod.POST)
    public ModelAndView solveComplaint(@Valid @ModelAttribute("solveComplaintForm") SolveComplainForm form, BindingResult result, @PathVariable(value = "complaintId") final int complaintId){
        if(result.hasErrors()){
            return solveComplaint(form,complaintId);
        }
        complainService.closeComplainWithComment(complaintId, form.getComments());
        return new ModelAndView("redirect:/admin/success");
    }


    @RequestMapping(value = "/unassign/{complaintId}", method = RequestMethod.POST)
    public ModelAndView unassign(@PathVariable(value = "complaintId") final int complaintId) {
        complainService.unassignComplain(complaintId);
        return new ModelAndView("redirect:/admin/");
    }

    @RequestMapping(value = "/selfassign/{complaintId}", method = RequestMethod.POST)
    public ModelAndView selfAssign(@PathVariable(value = "complaintId") final int complaintId, final Authentication authentication) {
        complainService.assignComplain(complaintId, authentication.getName());
        return new ModelAndView("redirect:/admin/solve/"+complaintId);
    }

    @RequestMapping(value = "/kyccheck/{username}", method = RequestMethod.GET)
    public ModelAndView kycCheckGet(@ModelAttribute("kycApprovalForm") KycApprovalForm  kycApprovalForm, @PathVariable(value = "username") final String username) {
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
        if(errors.hasErrors()){
            return kycCheckGet(kycApprovalForm, kycApprovalForm.getUsername());
        }
        kycService.rejectKycRequest(kycId, kycApprovalForm.getMessage());
        return new ModelAndView("redirect:/admin/kyccheck?success");
    }

    @RequestMapping(value = "/kyccheck", method = RequestMethod.GET)
    public ModelAndView kycCheckHome(@ModelAttribute("kycApprovalForm") KycApprovalForm  kycApprovalForm, @RequestParam("page") Optional<Integer> page) {
        ModelAndView mav = new ModelAndView("admin/kycAll");

        Collection<KycInformation> pendingKycs = kycService.getPendingKycRequests(page.orElse(0), KYC_PAGE_SIZE);
        mav.addObject("pendingKycs", pendingKycs);

        return mav;
    }


    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public ModelAndView solveSuccess() {
        return new ModelAndView("admin/solvedComplaint");
    }


    private ModelAndView getComplaintsByFilter(ComplainFilter filter,String view,String url,String title){
        ModelAndView mav = new ModelAndView(view);
        mav.addObject("baseUrl", url);
        mav.addObject("title", title);
        int complainCount = complainService.countComplainsBy(filter);
        int pageNumber = filter.getPage();
        int pages =  (complainCount + PAGE_SIZE - 1) / PAGE_SIZE;
        mav.addObject("pages", pages);
        mav.addObject("activePage", pageNumber);
        mav.addObject("complainList", complainService.getComplainsBy(filter));
        return mav;

    }


    private ModelAndView setUpComplaintView(String view,int complaintId){
        Complain complain = complainService.getComplainById(complaintId).orElseThrow(()->new NoSuchComplainException(complaintId));
        User complainer = userService.getUserInformation(complain.getComplainerUsername()).orElseThrow(()->new NoSuchUserException(complain.getComplainerUsername()));
        Trade trade;
        if(complain.getTradeId().isPresent()){
            trade = tradeService.getTradeById(complain.getTradeId().get()).orElseThrow(() -> new NoSuchTradeException(complain.getTradeId().get()));
        }else
            trade=null;
        ModelAndView mav = new ModelAndView(view);
        mav.addObject("trade", trade);
        mav.addObject("complain", complain);
        mav.addObject("complainer", complainer);
        return mav;
    }


}

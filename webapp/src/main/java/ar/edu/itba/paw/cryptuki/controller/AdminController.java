package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.ComplainFilter;
import ar.edu.itba.paw.cryptuki.form.SolveComplainForm;
import ar.edu.itba.paw.cryptuki.form.admin.ComplainFilterResult;
import ar.edu.itba.paw.persistence.Complain;
import ar.edu.itba.paw.persistence.ComplainStatus;
import ar.edu.itba.paw.persistence.Trade;
import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.service.ComplainService;
import ar.edu.itba.paw.service.TradeService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final int PAGE_SIZE = 5;
    private final ComplainService complainService;
    private final UserService userService;
    private final TradeService tradeService;

    @Autowired
    public AdminController(ComplainService complainService, UserService userService, TradeService tradeService) {
        this.complainService = complainService;
        this.userService = userService;
        this.tradeService = tradeService;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView adminHome(@RequestParam("page") Optional<Integer> page, @Valid ComplainFilterResult complainFilterResult, BindingResult result ,   final Authentication authentication){
        if(result.hasErrors()){
            return null;
        }
        ComplainFilter.Builder builder = complainFilterResult.toComplainFilterBuilder();
        ModelAndView mav = new ModelAndView("views/admin/complaints");
        ComplainFilter filter = builder
                .withComplainStatus(ComplainStatus.PENDING)
                .withPage(page.orElse(0))
                .withPageSize(PAGE_SIZE)
                .build();


        mav.addObject("baseUrl", "/admin"); // TODO: check
        mav.addObject("title", "Reclamos pendientes");
        mav.addObject("username", authentication == null ? null : authentication.getName()); // TODO: check

        int complainCount = complainService.countComplainsBy(filter);
        int pageNumber = page.orElse(0);
        int pages =  (complainCount + PAGE_SIZE - 1) / PAGE_SIZE;
        mav.addObject("pages", pages);
        mav.addObject("activePage", pageNumber);
        mav.addObject("complainList", complainService.getComplainsBy(filter));

        return mav;
    }


    @RequestMapping(value = "/assigned", method = RequestMethod.GET)
    public ModelAndView assignedComplains(@RequestParam("page") Optional<Integer> page, ComplainFilterResult complainFilterResult, final Authentication authentication){

        ComplainFilter.Builder builder = complainFilterResult.toComplainFilterBuilder();
        ModelAndView mav = new ModelAndView("views/admin/complaints");
        ComplainFilter filter = builder
                .withComplainStatus(ComplainStatus.ASSIGNED)
                .withPage(page.orElse(0))
                .withPageSize(PAGE_SIZE)
                .withModeratorUsername(authentication.getName())
                .build();


        mav.addObject("baseUrl", "/admin/assigned"); // TODO: check
        mav.addObject("title", "Reclamos asignados a mí");
        mav.addObject("username", authentication == null ? null : authentication.getName()); // TODO: check

        int complainCount = complainService.countComplainsBy(filter);
        int pageNumber = page.orElse(0);
        int pages =  (complainCount + PAGE_SIZE - 1) / PAGE_SIZE;
        mav.addObject("pages", pages);
        mav.addObject("activePage", pageNumber);
        mav.addObject("complainList", complainService.getComplainsBy(filter));

        return mav;
    }
    @RequestMapping(value = "/solved", method = RequestMethod.GET)
    public ModelAndView solvedComplains(@RequestParam("page") Optional<Integer> page, ComplainFilterResult complainFilterResult, final Authentication authentication){

        ComplainFilter.Builder builder = complainFilterResult.toComplainFilterBuilder();
        ModelAndView mav = new ModelAndView("views/admin/complaints");
        ComplainFilter filter = builder
                .withComplainStatus(ComplainStatus.CLOSED)
                .withPage(page.orElse(0))
                .withPageSize(PAGE_SIZE)
                .build();


        mav.addObject("baseUrl", "/admin/solved"); // TODO: check
        mav.addObject("title", "Reclamos resueltos");
        mav.addObject("username", authentication == null ? null : authentication.getName()); // TODO: check

        int complainCount = complainService.countComplainsBy(filter);
        int pageNumber = page.orElse(0);
        int pages =  (complainCount + PAGE_SIZE - 1) / PAGE_SIZE;
        mav.addObject("pages", pages);
        mav.addObject("activePage", pageNumber);
        mav.addObject("complainList", complainService.getComplainsBy(filter));

        return mav;
    }

    @RequestMapping(value = "/complaint/{complaintId}", method = RequestMethod.GET)
    public ModelAndView complaintDetail(@PathVariable(value = "complaintId") final int complaintId, final Authentication authentication){
        ModelAndView mav = new ModelAndView("views/admin/complaint");


        Complain complain = complainService.getComplainsBy(new ComplainFilter.Builder().withComplainId(complaintId).build()).iterator().next();  // TODO: Refactor, ugly
        User complainer = userService.getUserInformation(complain.getComplainer()).orElseThrow(RuntimeException::new);
        Trade trade = tradeService.getTradeById(complain.getTradeId().orElse(-1)).orElse(null);

        mav.addObject("username", authentication == null ? null : authentication.getName());
        mav.addObject("trade", trade);
        mav.addObject("complain", complain);
        mav.addObject("complainer", complainer);

        return mav;
    }


    @RequestMapping(value = "/selfassign/{complaintId}", method = RequestMethod.POST)
    public ModelAndView selfAssign(@PathVariable(value = "complaintId") final int complaintId, final Authentication authentication) {

        // TODO: Esto esta mal: tiene que ser Transactional y estar en la capa de servicio
        complainService.updateComplainStatus(complaintId, ComplainStatus.ASSIGNED);
        complainService.updateModerator(complaintId, authentication.getName());
        return new ModelAndView("redirect:/admin/solve/"+complaintId); //TODO !!!!!!!!!!!!!!
    }
    @RequestMapping(value = "/unassign/{complaintId}", method = RequestMethod.POST)
    public ModelAndView unassign(@PathVariable(value = "complaintId") final int complaintId, final Authentication authentication) {

        // TODO: Esto esta mal: tiene que ser Transactional y estar en la capa de servicio
        complainService.updateComplainStatus(complaintId, ComplainStatus.PENDING);
        complainService.updateModerator(complaintId, null);
        return new ModelAndView("redirect:/admin/"); //TODO !!!!!!!!!!!!!!
    }

    @RequestMapping(value = "/solve/{complaintId}", method = RequestMethod.GET)
    public ModelAndView solveComplaint(@ModelAttribute("solveComplaintForm") SolveComplainForm form, @PathVariable(value = "complaintId") final int complaintId, final Authentication authentication){

        ModelAndView mav = new ModelAndView("views/admin/solve_complaint");


        Complain complain = complainService.getComplainsBy(new ComplainFilter.Builder().withComplainId(complaintId).build()).iterator().next();  // TODO: Refactor, ugly
        User complainer = userService.getUserInformation(complain.getComplainer()).orElseThrow(RuntimeException::new);
        Trade trade = tradeService.getTradeById(complain.getTradeId().orElse(-1)).orElse(null);

        mav.addObject("username", authentication == null ? null : authentication.getName());
        mav.addObject("isAdmin", true);
        mav.addObject("trade", trade);
        mav.addObject("complain", complain);
        mav.addObject("complainer", complainer);
        return mav;
    }
    @RequestMapping(value = "/solve/{complaintId}", method = RequestMethod.POST)
    public ModelAndView solveComplaint(@Valid @ModelAttribute("solveComplaintForm") SolveComplainForm form, BindingResult result, @PathVariable(value = "complaintId") final int complaintId, final Authentication authentication){
        if(result.hasErrors()){
            return solveComplaint(form,complaintId,authentication);
        }


        // TODO!!! Deberia ser un metodo del servicio Transactional
        complainService.updateComplainStatus(complaintId, ComplainStatus.CLOSED);
        complainService.updateModeratorComment(complaintId, form.getComments());

        return new ModelAndView("redirect:/admin/success");
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public ModelAndView solveSuccess() {
        ModelAndView mav = new ModelAndView("views/admin/solved_complaint");
        return mav;
    }

    @RequestMapping(value = "/profile")
    public ModelAndView adminProfile(final Authentication authentication){
        return null;
    }
}

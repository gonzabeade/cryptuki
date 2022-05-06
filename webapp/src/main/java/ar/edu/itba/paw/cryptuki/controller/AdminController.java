package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.ComplainFilter;
import ar.edu.itba.paw.cryptuki.form.SupportForm;
import ar.edu.itba.paw.service.ComplainService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final int PAGE_SIZE = 5;
//    private final ComplainService complainService;


//    public AdminController(ComplainService complainService) {
//        this.complainService = complainService;
//    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView adminHome(@RequestParam(value = "page") final Optional<Integer> page,
                                  @ModelAttribute("complainFilterForm") final SupportForm form,
                                  final Authentication authentication){



        ModelAndView mav = new ModelAndView("views/admin/complaints");
        mav.addObject("title", "Reclamos pendientes");
        mav.addObject("baseUrl", "/admin/");
        mav.addObject("username", authentication == null ? null : authentication.getName());


        int offerCount = 10;
        int pageNumber = page.orElse(0);
        int pages =  (offerCount + PAGE_SIZE - 1) / PAGE_SIZE;
        mav.addObject("pages", pages);
        mav.addObject("activePage", pageNumber);

        return mav;
    }
    @RequestMapping(value = "/assigned", method = RequestMethod.GET)
    public ModelAndView assignedComplaints(@RequestParam(value = "page") final Optional<Integer> page,
                                           @RequestParam(value = "fromDate", required = false) final String fromDate,
                                           @RequestParam(value = "toDate", required = false) final String toDate,
                                           @RequestParam(value = "offerId", required = false) final Double offerId,
                                           @RequestParam(value = "tradeId", required = false) final Double tradeId,
                                           @RequestParam(value = "username", required = false) final Double username,
                                           final Authentication authentication){
        ModelAndView mav = new ModelAndView("views/admin/complaints");
        mav.addObject("title", "Reclamos asignados a mi");
        mav.addObject("username", authentication == null ? null : authentication.getName());
        mav.addObject("baseUrl", "/admin/assigned");

        int offerCount = 10;
        int pageNumber = page.orElse(0);
        int pages =  (offerCount + PAGE_SIZE - 1) / PAGE_SIZE;
        mav.addObject("pages", pages);
        mav.addObject("activePage", pageNumber);

        return mav;
    }
    @RequestMapping(value = "/solved", method = RequestMethod.GET)
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
    @RequestMapping(value = "/profile")
    public ModelAndView adminProfile(final Authentication authentication){
        return null;
    }
}

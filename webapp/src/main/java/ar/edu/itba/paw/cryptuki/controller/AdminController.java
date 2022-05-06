package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.ComplainFilter;
import ar.edu.itba.paw.cryptuki.form.SupportForm;
import ar.edu.itba.paw.cryptuki.form.admin.ComplainFilterResult;
import ar.edu.itba.paw.persistence.ComplainStatus;
import ar.edu.itba.paw.service.ComplainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
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
    private final ComplainService complainService;


    @Autowired
    public AdminController(ComplainService complainService) {
        this.complainService = complainService;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView adminHome(Optional<Integer> page, ComplainFilterResult complainFilterResult, final Authentication authentication){

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
    public ModelAndView assignedComplains(Optional<Integer> page, ComplainFilterResult complainFilterResult, final Authentication authentication){

        ComplainFilter.Builder builder = complainFilterResult.toComplainFilterBuilder();
        ModelAndView mav = new ModelAndView("views/admin/complaints");
        ComplainFilter filter = builder
                .withComplainStatus(ComplainStatus.ASSIGNED)
                .withPage(page.orElse(0))
                .withPageSize(PAGE_SIZE)
                .withModeratorUsername(authentication.getName())
                .build();


        mav.addObject("baseUrl", "/admin"); // TODO: check
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
    public ModelAndView solvedComplains(Optional<Integer> page, ComplainFilterResult complainFilterResult, final Authentication authentication){

        ComplainFilter.Builder builder = complainFilterResult.toComplainFilterBuilder();
        ModelAndView mav = new ModelAndView("views/admin/complaints");
        ComplainFilter filter = builder
                .withComplainStatus(ComplainStatus.CLOSED)
                .withPage(page.orElse(0))
                .withPageSize(PAGE_SIZE)
                .build();


        mav.addObject("baseUrl", "/admin"); // TODO: check
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

    @RequestMapping(value = "/profile")
    public ModelAndView adminProfile(final Authentication authentication){
        return null;
    }
}

package ar.edu.itba.paw.cryptuki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/seller")
public class SellerController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView seller(){
        ModelAndView mav = new ModelAndView("seller/sellerIndex");
        return mav;
    }

}

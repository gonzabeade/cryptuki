package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.form.CodeForm;
import ar.edu.itba.paw.cryptuki.form.EmailForm;
import ar.edu.itba.paw.cryptuki.form.ProfilePicForm;
import ar.edu.itba.paw.cryptuki.form.RegisterForm;
import ar.edu.itba.paw.persistence.Image;
import ar.edu.itba.paw.persistence.UserAuth;
import ar.edu.itba.paw.service.ProfilePicService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;

@Controller
public class UserController {
    private  final UserService userService;
    private final ProfilePicService profilePicService;


    @RequestMapping(value="/register",method = RequestMethod.GET)
    public ModelAndView registerGet(@ModelAttribute("registerForm") final RegisterForm form){
        return new ModelAndView("views/register");
    }

    @RequestMapping(value="/register" , method={RequestMethod.POST})
    public ModelAndView register(@Valid @ModelAttribute("registerForm") RegisterForm form , final BindingResult errors){
        if( !form.getPassword().equals(form.getRepeatPassword()) || errors.hasErrors()){
            return registerGet(form);
        }
        try{
            userService.registerUser(form.toUserAuthBuilder(), form.toUserBuilder());
        }
        catch(Exception e ){
            errors.addError(new FieldError("registerForm","email","El nombre de usuario o correo electrónico ya fueron utilizados."));
            return registerGet(form);
        }

        return new ModelAndView("redirect:/verify?user="+form.getUsername());
    }


    @RequestMapping("/login")
    public ModelAndView login(@RequestParam(value = "error" , required = false) String error){
        ModelAndView mav = new ModelAndView("views/login");
        if(error != null){
            mav.addObject("error", true);
            return mav;
        }
        return mav;
    }
    @RequestMapping(value="/verify",method = {RequestMethod.GET})
    public ModelAndView verify(@ModelAttribute("CodeForm") final CodeForm form, @RequestParam(value = "user") String username){
        ModelAndView mav = new ModelAndView("views/code_verification");
        mav.addObject("username", username);
        return mav;
    }

    public UserController(UserService userService, ProfilePicService profilePicService) {
        this.userService = userService;
        this.profilePicService = profilePicService;
    }

    @RequestMapping(value = "/verify",method = RequestMethod.POST)
    public ModelAndView verify( @Valid @ModelAttribute("CodeForm") CodeForm form, BindingResult errors){
        if(errors.hasErrors()){
            return verify(form, form.getUsername());
        }
        try{
            userService.verifyUser(form.getUsername(), form.getCode());
        } catch (RuntimeException e){
            errors.addError(new FieldError("CodeForm","code","El código ingresado no es correcto"));
            return verify(form, form.getUsername());
        }

        //log in programmatically

        UserAuth user = userService.getUserByUsername(form.getUsername()).orElseThrow(RuntimeException::new);
        org.springframework.security.core.userdetails.User current = new org.springframework.security.core.userdetails.User(form.getUsername(), user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
        Authentication auth = new UsernamePasswordAuthenticationToken(current,null, Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return new ModelAndView("redirect:/");

    }


    @RequestMapping(value="/passwordRecovery")
    public ModelAndView passwordSendMailGet(@Valid @ModelAttribute("EmailForm") EmailForm form){
        return new ModelAndView("views/ChangePassword");
    }

    @RequestMapping(value = "/passwordRecovery",method = RequestMethod.POST)
    public ModelAndView passwordSendMail(@Valid @ModelAttribute("EmailForm") EmailForm form, BindingResult errors){
        if(errors.hasErrors())
            return passwordSendMailGet(form);
        try{
            userService.sendChangePasswordMail(form.getEmail());
        }catch (Exception e ){
            return new ModelAndView("redirect:/errors");
        }
        return new ModelAndView("views/ChangePasswordMailSent");

    }


    @RequestMapping(value = "/profilepic/{username}", method = { RequestMethod.GET})
    public ResponseEntity<byte[]> imageGet(@PathVariable final String username){

        Image image = profilePicService.getProfilePicture(username).orElseThrow( () -> new RuntimeException("Unknown user"));
        return ResponseEntity.ok().contentType(MediaType.valueOf(image.getImageType())).body(image.getBytes());
    }
    @RequestMapping(value = "/test", method = { RequestMethod.POST })
    public ModelAndView test(@Valid @ModelAttribute("ProfilePicForm") ProfilePicForm form, BindingResult bindingResult) throws IOException {
        profilePicService.uploadProfilePicture("holachau", form.getMultipartFile().getBytes(), form.getMultipartFile().getContentType());
        return new ModelAndView("redirect:/");
    }
    @RequestMapping(value="/test", method = {RequestMethod.GET})
    public ModelAndView testGet(@ModelAttribute("ProfilePicForm") ProfilePicForm form){
        return new ModelAndView("views/upload_picture");
    }

}

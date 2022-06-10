package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.form.*;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.persistence.Image;
import ar.edu.itba.paw.persistence.Trade;
import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.persistence.UserAuth;
import ar.edu.itba.paw.service.ProfilePicService;
import ar.edu.itba.paw.service.TradeService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Controller
public class UserController {
    private  final UserService userService;
    private final ProfilePicService profilePicService;
    private final TradeService tradeService;



    @Autowired
    public UserController(UserService userService, ProfilePicService profilePicService, TradeService tradeService) {
        this.userService = userService;
        this.profilePicService = profilePicService;
        this.tradeService=tradeService;
    }


    @RequestMapping(value="/register",method = RequestMethod.GET)
    public ModelAndView registerGet(@ModelAttribute("registerForm") final RegisterForm form){
        return new ModelAndView("register");
    }

    @RequestMapping(value="/register" , method={RequestMethod.POST})
    public ModelAndView register(@Valid @ModelAttribute("registerForm") RegisterForm form , final BindingResult errors){
        if(errors.hasErrors()){
            return registerGet(form);
        }
        userService.registerUser(form.toUserAuthBuilder(), form.toUserBuilder());
        return new ModelAndView("redirect:/verify?user="+form.getUsername());
    }


    @RequestMapping("/login")
    public ModelAndView login(@RequestParam(value = "error" , required = false) String error){
        ModelAndView mav = new ModelAndView("login");
        if(error != null){
            mav.addObject("error", true);
            return mav;
        }
        return mav;
    }


    @RequestMapping(value="/verify",method = {RequestMethod.GET})
    public ModelAndView verify( @ModelAttribute("CodeForm") final CodeForm form, @RequestParam(value = "user") String username, @RequestParam(value = "error", required = false) boolean error){
        ModelAndView mav = new ModelAndView("codeVerification");
        mav.addObject("error", error);
        mav.addObject("username", username);
        return mav;
    }

    @RequestMapping(value = "/verify",method = RequestMethod.POST)
    public ModelAndView verify( @Valid @ModelAttribute("CodeForm") CodeForm form, BindingResult errors){

        if(errors.hasErrors())
            return verify(form, form.getUsername(), false);

        if ( ! userService.verifyUser(form.getUsername(), form.getCode()) )
              return verify(form, form.getUsername(), true);

        return logInProgrammatically(form.getUsername());
    }


    @RequestMapping(value="/passwordRecovery")
    public ModelAndView passwordSendMailGet(@ModelAttribute("EmailForm") EmailForm form){
        return new ModelAndView("passwordRecovery");
    }

    @RequestMapping(value = "/passwordRecovery",method = RequestMethod.POST)
    public ModelAndView passwordSendMail(@Valid @ModelAttribute("EmailForm") EmailForm form, BindingResult errors){
        if(errors.hasErrors())
            return passwordSendMailGet(form);
        userService.changePasswordAnonymously(form.getEmail());

        return new ModelAndView("changePasswordMailSent");

    }


    @RequestMapping(value = "/profilepic/{username}", method = { RequestMethod.GET})
    public ResponseEntity<byte[]> imageGet(@PathVariable final String username) throws IOException, URISyntaxException {
        Optional<Image> maybeImage = profilePicService.getProfilePicture(username);
        if(!maybeImage.isPresent()){
            BufferedImage bufferedImage = ImageIO.read(new File(this.getClass().getClassLoader().getResource("default-Profile.png").toURI()));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage,"png",byteArrayOutputStream);
            byte [] data=byteArrayOutputStream.toByteArray();
            return ResponseEntity.ok().contentType(MediaType.valueOf("image/png")).body(data);
        }else {
            Image image= maybeImage.get();
            return ResponseEntity.ok().contentType(MediaType.valueOf(image.getImageType())).body(image.getBytes());
        }
    }


    @RequestMapping(value= "/profilePicSelector", method = {RequestMethod.GET})
    public ModelAndView profilePicSelectorGet(@ModelAttribute("ProfilePicForm") ProfilePicForm form){
        return new ModelAndView("uploadPicture");
    }

    @RequestMapping(value = "/profilePicSelector", method = { RequestMethod.POST })
    public ModelAndView profilePicSelector(@Valid @ModelAttribute("ProfilePicForm") ProfilePicForm form, BindingResult bindingResult,Authentication authentication) throws IOException {
        if(bindingResult.hasErrors())
            return profilePicSelectorGet(new ProfilePicForm());
        if(form.getMultipartFile().isEmpty()){
            bindingResult.addError(new FieldError("ProfilePicForm","multipartFile","Debe escoger una foto para continuar."));
            return profilePicSelectorGet(form);
        }

        profilePicService.uploadProfilePicture(authentication.getName(), form.getMultipartFile().getBytes(), form.getMultipartFile().getContentType());
        if(form.isBuyer()){
            return new ModelAndView("redirect:/buyer/");
        }
        return new ModelAndView("redirect:/seller/");
    }

    @RequestMapping(value="/user")
    public ModelAndView user(@ModelAttribute("ProfilePicForm") ProfilePicForm form, BindingResult bindingResult, Authentication authentication,@RequestParam(value = "page") final Optional<Integer> page, @RequestParam(value = "updatedPass",required = false) final boolean updatedPass){
        if(bindingResult.hasErrors()){
            throw new IllegalArgumentException();
        }
        String username = authentication.getName();
        User user = userService.getUserInformation(username).orElseThrow(()->new NoSuchUserException(username));

        ModelAndView mav = new ModelAndView("userProfile");
        mav.addObject("user",user);
        mav.addObject("updatedPass", updatedPass);
        return mav;
    }


    @RequestMapping(value="/changePassword", method = {RequestMethod.GET})
    public ModelAndView changePasswordGet(@ModelAttribute("changePasswordForm") ChangePasswordForm form){
        return new ModelAndView("changePassword");
    }

    @RequestMapping(value="/changePassword", method = {RequestMethod.POST})
    public ModelAndView changePassword(@Valid @ModelAttribute("changePasswordForm") ChangePasswordForm form, BindingResult bindingResult, Authentication authentication){
        if(bindingResult.hasErrors())
            return changePasswordGet(new ChangePasswordForm());
        userService.changePassword(authentication.getName(), form.getPassword());
        return new ModelAndView("redirect:/user"+"?updatedPass=true");
    }


    @RequestMapping(value ="/recoverPassword", method = {RequestMethod.GET})
    public ModelAndView recoverPasswordGet(@ModelAttribute("recoverPasswordForm") RecoverPasswordForm form, @RequestParam(value = "user") String username, @RequestParam(value = "code") Integer code){
        ModelAndView mav = new ModelAndView("recoverPassword");
        mav.addObject("code", code);
        mav.addObject("username", username);
        return mav;
    }


    @RequestMapping(value ="/recoverPassword", method = {RequestMethod.POST})
    public ModelAndView recoverPasswordPost(@Valid @ModelAttribute("recoverPasswordForm") RecoverPasswordForm form, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return recoverPasswordGet(new RecoverPasswordForm(),form.getUsername(), form.getCode());
        if(!userService.changePassword(form.getUsername(), form.getCode(), form.getPassword()))
            throw new NoSuchUserException(form.getUsername());

        return logInProgrammatically(form.getUsername());
    }

    private ModelAndView logInProgrammatically(String username ){
        UserAuth user = userService.getUserByUsername(username).orElseThrow(()->new NoSuchUserException(username));
        org.springframework.security.core.userdetails.User current = new org.springframework.security.core.userdetails.User(username, user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())));
        Authentication auth = new UsernamePasswordAuthenticationToken(current,null, Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return new ModelAndView("redirect:/");
    }




}

package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.form.*;
import ar.edu.itba.paw.cryptuki.form.auth.*;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.ProfilePicture;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.ProfilePicService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Optional;

@Controller
public class UserController {
    private  final UserService userService;
    private final ProfilePicService profilePicService;

    @Autowired
    public UserController(UserService userService, ProfilePicService profilePicService) {
        this.userService = userService;
        this.profilePicService = profilePicService;
    }

    @RequestMapping(value="/register",method = RequestMethod.GET)
    public ModelAndView registerGet(@ModelAttribute("registerForm") RegisterForm form){
        return new ModelAndView("auth/register");
    }

    @RequestMapping(value="/register" , method={RequestMethod.POST})
    public ModelAndView register(@Valid @ModelAttribute("registerForm") RegisterForm form, BindingResult errors, HttpServletRequest request){
        if (errors.hasErrors()) {
            return registerGet(form);
        }
        userService.registerUser(form.getEmail(), form.getUsername(), form.getPassword(), form.getPhoneNumber(), request.getLocale());
        return new ModelAndView("redirect:/verify?user="+form.getUsername());
    }

    @RequestMapping("/login")
    public ModelAndView login(@RequestParam(value = "error", required = false) String error) {
        ModelAndView mav = new ModelAndView("auth/login");
        if(error != null)
            mav.addObject("error", true);
        return mav;
    }

    @RequestMapping(value="/verify", method = {RequestMethod.GET})
    public ModelAndView verify(@ModelAttribute("codeForm") final CodeForm form, @RequestParam(value = "user") String username){
        ModelAndView mav = new ModelAndView("emailVerification/codeVerification");
        mav.addObject("username", username); // Still not logged in
        return mav;
    }
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public ModelAndView verify(@Valid @ModelAttribute("codeForm") CodeForm form, BindingResult errors){
        if (errors.hasErrors())
            return verify(form, form.getUsername());
        userService.verifyUser(form.getUsername(), form.getCode());
        return logInProgrammatically(form.getUsername());
    }
    @RequestMapping(value = "/verifyByMail", method = RequestMethod.POST)
    public ModelAndView verify(@RequestParam("code") int code, @RequestParam("username") String username){
        userService.verifyUser(username,  code);
        return logInProgrammatically(username);
    }

    @RequestMapping(value = "/profilepic/{username}", method = { RequestMethod.GET})
    public ResponseEntity<byte[]> imageGet(@PathVariable final String username) throws IOException, URISyntaxException {
        Optional<ProfilePicture> maybeImage = profilePicService.getProfilePicture(username);
        if (!maybeImage.isPresent()) {
            BufferedImage bufferedImage = ImageIO.read(new File(this.getClass().getClassLoader().getResource("default-Profile.png").toURI()));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage,"png",byteArrayOutputStream);
            byte [] data=byteArrayOutputStream.toByteArray();
            return ResponseEntity.ok().contentType(MediaType.valueOf("image/png")).body(data);
        } else {
            ProfilePicture image= maybeImage.get();
            return ResponseEntity.ok().contentType(MediaType.valueOf(image.getImageType())).body(image.getBytes());
        }
    }

    @RequestMapping(value = "/profilePicSelector", method = { RequestMethod.POST })
    public ModelAndView profilePicSelector(@Valid @ModelAttribute("profilePicForm") ProfilePicForm form, BindingResult bindingResult,Authentication authentication) throws IOException {
        if (!bindingResult.hasErrors())
            profilePicService.uploadProfilePicture(authentication.getName(), form.getMultipartFile().getBytes(), form.getMultipartFile().getContentType());
        if (form.isBuyer())
            return new ModelAndView("redirect:/buyer/");
        return new ModelAndView("redirect:/seller/");
    }

    @RequestMapping(value="/changePassword", method = RequestMethod.GET)
    public ModelAndView changePasswordGet(@ModelAttribute("changePasswordForm") ChangePasswordForm form){
        return new ModelAndView("auth/changePassword");
    }

    @RequestMapping(value="/changePassword", method = RequestMethod.POST)
    public ModelAndView changePassword(@Valid @ModelAttribute("changePasswordForm") ChangePasswordForm form, BindingResult bindingResult, Authentication authentication){
        if(bindingResult.hasErrors())
            return changePasswordGet(new ChangePasswordForm());
        userService.changePassword(authentication.getName(), form.getPassword());
        return new ModelAndView("redirect:/changePassword?success");
    }

    @RequestMapping(value="/emailPasswordRecovery", method = RequestMethod.GET)
    public ModelAndView passwordSendMailGet(@ModelAttribute("emailForm") EmailForm form){
        return new ModelAndView("auth/emailPasswordRecovery");
    }

    @RequestMapping(value = "/emailPasswordRecovery", method = RequestMethod.POST)
    public ModelAndView passwordSendMail(@Valid @ModelAttribute("emailForm") EmailForm form, BindingResult errors){
        if(errors.hasErrors())
            return passwordSendMailGet(form);
        userService.changePasswordAnonymously(form.getEmail());
        return new ModelAndView("auth/changePasswordMailSent");
    }

    @RequestMapping(value ="/recoverPassword", method = {RequestMethod.GET})
    public ModelAndView recoverPasswordGet(@ModelAttribute("recoverPasswordForm") RecoverPasswordForm form, @RequestParam(value = "user") String username, @RequestParam(value = "code") int code){
        form.setCode(code);
        ModelAndView mav = new ModelAndView("auth/recoverPassword");
        mav.addObject("username", username);
        return mav;
    }

    @RequestMapping(value ="/recoverPassword", method = {RequestMethod.POST})
    public ModelAndView recoverPasswordPost(@Valid @ModelAttribute("recoverPasswordForm") RecoverPasswordForm form, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return recoverPasswordGet(new RecoverPasswordForm(), form.getUsername(), form.getCode());
        userService.changePassword(form.getUsername(), form.getCode(), form.getPassword());
        return logInProgrammatically(form.getUsername());
    }

    private ModelAndView logInProgrammatically(String username){
        User user = userService.getUserByUsername(username).orElseThrow(()->new NoSuchUserException(username));
        org.springframework.security.core.userdetails.User current = new org.springframework.security.core.userdetails.User(username, user.getUserAuth().getPassword(), Collections.singletonList(new SimpleGrantedAuthority(user.getUserAuth().getRole().name())));
        Authentication auth = new UsernamePasswordAuthenticationToken(current,null, Collections.singletonList(new SimpleGrantedAuthority(user.getUserAuth().getRole().name())));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return new ModelAndView("redirect:/");
    }
}

package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.form.*;
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
import org.springframework.security.access.AccessDeniedException;
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

    private static int PAGE_SIZE = 3 ;

    @Autowired
    public UserController(UserService userService, ProfilePicService profilePicService, TradeService tradeService) {
        this.userService = userService;
        this.profilePicService = profilePicService;
        this.tradeService=tradeService;
    }


    @RequestMapping(value="/register",method = RequestMethod.GET)
    public ModelAndView registerGet(@ModelAttribute("registerForm") final RegisterForm form){
        return new ModelAndView("views/register");
    }

    @RequestMapping(value="/register" , method={RequestMethod.POST})
    public ModelAndView register(@Valid @ModelAttribute("registerForm") RegisterForm form , final BindingResult errors){
        if(errors.hasErrors()){
            return registerGet(form);
        }
        try{
            userService.registerUser(form.toUserAuthBuilder(), form.toUserBuilder());
        }
        catch(Exception e ){
            errors.addError(new FieldError("registerForm","email","El nombre de usuario o correo electrónico ya fueron utilizados."));
            form.setUsername("");
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
    public ModelAndView verify( @ModelAttribute("CodeForm") final CodeForm form, @RequestParam(value = "user") String username){
        ModelAndView mav = new ModelAndView("views/code_verification");
        mav.addObject("username", username);
        return mav;
    }

    @RequestMapping(value = "/verify",method = RequestMethod.POST)
    public ModelAndView verify( @Valid @ModelAttribute("CodeForm") CodeForm form, BindingResult errors){
        if(errors.hasErrors()){
            return verify(form, form.getUsername());
        }
           if ( ! userService.verifyUser(form.getUsername(), form.getCode()) ) {
               errors.addError(new FieldError("CodeForm", "code", "El código ingresado no es correcto"));
               return verify(form, form.getUsername());
           }

        return logInProgrammatically(form.getUsername());
    }


    @RequestMapping(value="/passwordRecovery")
    public ModelAndView passwordSendMailGet(@ModelAttribute("EmailForm") EmailForm form){
        return new ModelAndView("/views/passwordRecovery");
    }

    @RequestMapping(value = "/passwordRecovery",method = RequestMethod.POST)
    public ModelAndView passwordSendMail(@Valid @ModelAttribute("EmailForm") EmailForm form, BindingResult errors){
        if(errors.hasErrors())
            return passwordSendMailGet(form);
        try{
            userService.changePasswordAnonymously(form.getEmail());
        }catch (Exception e ){
            return new ModelAndView("redirect:/errors");
        }
        return new ModelAndView("views/ChangePasswordMailSent");

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


        return new ModelAndView("views/upload_picture");
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
        return new ModelAndView("redirect:/user");
    }

    @RequestMapping(value="/user")
    public ModelAndView user(@ModelAttribute("ProfilePicForm") ProfilePicForm form, BindingResult bindingResult, Authentication authentication,@RequestParam(value = "page") final Optional<Integer> page){
        String username = authentication.getName();
        User user = userService.getUserInformation(username).get();
        ModelAndView mav = new ModelAndView("views/user_profile");
        mav.addObject("username",username);
        mav.addObject("user",user);

        int pageNumber= page.orElse(0);
        int tradeCount = tradeService.getTradesByUsernameCount(username);
        int pages=(tradeCount+PAGE_SIZE-1)/PAGE_SIZE;
        Collection<Trade> tradeList = tradeService.getTradesByUsername(authentication.getName(),pageNumber,PAGE_SIZE);
        mav.addObject("tradeList",tradeList);
        mav.addObject("pages",pages);
        mav.addObject("activePage",pageNumber);

        return mav;
    }


    @RequestMapping(value="/changePassword", method = {RequestMethod.GET})
    public ModelAndView changePasswordGet(@ModelAttribute("changePasswordForm") changePasswordForm form, Authentication authentication){
        ModelAndView mav = new ModelAndView("views/changePassword");
        mav.addObject("username",authentication.getName());
        return mav;
    }

    @RequestMapping(value="/changePassword", method = {RequestMethod.POST})
    public ModelAndView changePassword(@Valid @ModelAttribute("changePasswordForm") changePasswordForm form, BindingResult bindingResult, Authentication authentication){
        if(bindingResult.hasErrors())
            return changePasswordGet(new changePasswordForm(),authentication);

        //check current password.
        userService.changePassword(authentication.getName(), form.getPassword());
        return new ModelAndView("redirect:/user");
    }


    @RequestMapping(value ="/recoverPassword", method = {RequestMethod.GET})
    public ModelAndView recoverPasswordGet(@ModelAttribute("recoverPasswordForm") recoverPasswordForm form,@RequestParam(value = "user") String username,@RequestParam(value = "code") Integer code){
        ModelAndView mav = new ModelAndView("views/recoverPassword");
        mav.addObject("username",username);
        mav.addObject("code",code);
        return mav;
    }


    @RequestMapping(value ="/recoverPassword", method = {RequestMethod.POST})
    public ModelAndView recoverPasswordPost(@Valid @ModelAttribute("recoverPasswordForm") recoverPasswordForm form,BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return recoverPasswordGet(new recoverPasswordForm(),form.getUsername(), form.getCode());
        //check this before login

        userService.changePassword(form.getUsername(), form.getCode(), form.getPassword());
        return logInProgrammatically(form.getUsername());
    }

    private ModelAndView logInProgrammatically(String username ){
        UserAuth user = userService.getUserByUsername(username).orElseThrow(RuntimeException::new);
        org.springframework.security.core.userdetails.User current = new org.springframework.security.core.userdetails.User(username, user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
        Authentication auth = new UsernamePasswordAuthenticationToken(current,null, Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return new ModelAndView("redirect:/");
    }




}

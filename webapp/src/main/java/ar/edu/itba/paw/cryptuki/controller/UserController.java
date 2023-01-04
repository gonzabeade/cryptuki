package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.dto.UserDto;
import ar.edu.itba.paw.cryptuki.dto.UserInformationDto;
import ar.edu.itba.paw.cryptuki.dto.UserNonceDto;
import ar.edu.itba.paw.cryptuki.form.UserEmailValidationForm;
import ar.edu.itba.paw.cryptuki.form.legacy.auth.ChangePasswordForm;
import ar.edu.itba.paw.cryptuki.form.RegisterForm;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.Role;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.parameterObject.UserPO;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.*;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Locale;
import java.util.Set;

@Path("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService, Validator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    private final Validator validator;


    @Context
    public UriInfo uriInfo;


    @GET
    @Path("/{username}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUser(@PathParam("username") String username) {

        UserDto userDto = userService.getUserByUsername(username)
                .map(u->UserDto.fromUser(u, uriInfo))
                .orElseThrow(()-> new NoSuchUserException(username));

        return Response.ok(userDto).build();
    }

    public Response toUserNonce(String email) {
        userService.changePasswordAnonymously(email);
        return Response.ok(UserNonceDto.fromEmail(email)).build();
    }

    public Response toNewUser(RegisterForm registerForm, Locale locale) {

        /* Manual validation - @Valid does not work on non-controller methods*/
        Set<ConstraintViolation<RegisterForm>> violations = validator.validate(registerForm);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

        UserPO userPO = registerForm.toParameterObject().withLocale(locale);
        userService.registerUser(userPO);

        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(userPO.getUsername())
                .build();

        return Response.created(uri).build();
    }
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    public Response postUser(RegisterForm registerForm, @Context HttpServletRequest request, @QueryParam("email") String email) {
        return email == null ? toNewUser(registerForm, request.getLocale()) : toUserNonce(email);
    }

    @GET
    @Path("/{username}/secrets")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserInformation(@PathParam("username") String username) {

        UserInformationDto userInformationDto = userService.getUserByUsername(username)
                .map(u->UserInformationDto.fromUser(u, uriInfo))
                .orElseThrow(()->new NoSuchUserException(username));

        String principalName = SecurityContextHolder.getContext().getAuthentication().getName();
        User principal = userService.getUserByUsername(principalName).orElseThrow(()->new NoSuchUserException(principalName));

        // The service does not expose a method for validating this
        // It is simpler to validate this in the controller than to create a dummy method in the service
        if (!principalName.equals(username) && !principal.getUserAuth().getRole().equals(Role.ROLE_ADMIN))
            throw new ForbiddenException();

        return Response.ok(userInformationDto).build();
    }

    // TODO - How is the consumer of the api supposed to know the location of these endpoints?

    @PUT
    @Path("/{username}/password")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    public Response changePassword(@Valid ChangePasswordForm changePasswordForm, @PathParam("username") String username){
        userService.changePassword(username, changePasswordForm.getPassword());
        return Response.ok().build();
    }

    @POST
    @Path("/{username}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    public Response createValidation(
            @Valid UserEmailValidationForm userEmailValidationForm,
            @PathParam("username") String username
    ){

        if (!userService.verifyUser(username, userEmailValidationForm.getCode()))
            throw new BadRequestException();

        final URI uri = uriInfo.getAbsolutePathBuilder().build();

        return Response.created(uri).build();
    }

}

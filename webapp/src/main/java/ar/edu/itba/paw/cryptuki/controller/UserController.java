package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.dto.UserDto;
import ar.edu.itba.paw.cryptuki.dto.UserNonceDto;
import ar.edu.itba.paw.cryptuki.form.ChangePasswordForm;
import ar.edu.itba.paw.cryptuki.form.RegisterForm;
import ar.edu.itba.paw.cryptuki.form.UserEmailValidationForm;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.parameterObject.UserPO;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.validation.*;
import javax.ws.rs.Path;
import javax.ws.rs.*;
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

    private final ValidatorFactory validatorFactory;
    @Autowired
    public UserController(UserService userService, ValidatorFactory validatorFactory) {
        this.userService = userService;
        this.validatorFactory = validatorFactory;
    }

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
        if(registerForm == null)
            throw new BadRequestException("User data must be provided.");


        /* Manual validation - @Valid does not work on non-controller methods*/
        Validator validator = validatorFactory.getValidator();
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


    // TODO - How is the consumer of the api supposed to know the location of these endpoints?

    @PUT
    @Path("/{username}/password")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    public Response changePassword(@Valid ChangePasswordForm changePasswordForm, @PathParam("username") String username){
        userService.changePassword(username, changePasswordForm.getPassword());
        return Response.noContent().build();
    }

    @POST
    @Path("/{username}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    public Response createValidation(
            @Valid UserEmailValidationForm userEmailValidationForm,
            @PathParam("username") String username
    ){

        if (!userService.verifyUser(username, userEmailValidationForm.getCode()))
            throw new BadRequestException("Bad request");

        final URI uri = uriInfo.getAbsolutePathBuilder().build();
        return Response.created(uri).build();
    }

}

package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.dto.OfferDto;
import ar.edu.itba.paw.cryptuki.dto.UserDto;
import ar.edu.itba.paw.cryptuki.form.RegisterForm;
import ar.edu.itba.paw.cryptuki.form.UploadOfferForm;
import ar.edu.itba.paw.exception.NoSuchOfferException;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.Offer;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.parameterObject.UserPO;
import ar.edu.itba.paw.service.OfferService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Locale;
import java.util.Optional;

// TODO: verify user
// TODO: repeat password?
// TODO: 'me' endpoint is not correct. Check for Sotuyos answer

@Path("/api/users")
public class UserController {

    public final UserService userService;

    @Context
    public UriInfo uriInfo;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Path("/{username}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUser(@PathParam("username") String username) {

        Optional<UserDto> maybeUser = userService.getUserByUsername(username).map( u -> UserDto.fromUser(u, uriInfo));

        if (!maybeUser.isPresent())
            throw new NoSuchUserException(username);

        return Response.ok(maybeUser.get()).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createUser(@Valid RegisterForm registerForm, @Context HttpServletRequest request) {

        UserPO userPO = registerForm.toParameterObject().withLocale(request.getLocale());
        userService.registerUser(userPO);

        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(userPO.getUsername())
                .build();

        return Response.created(uri).build();
    }

}

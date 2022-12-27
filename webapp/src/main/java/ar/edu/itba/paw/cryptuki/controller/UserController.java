package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.auth.jwt.JwtUtils;
import ar.edu.itba.paw.cryptuki.dto.UserDto;
import ar.edu.itba.paw.cryptuki.dto.UserInformationDto;
import ar.edu.itba.paw.cryptuki.form.legacy.auth.ChangePasswordForm;
import ar.edu.itba.paw.cryptuki.form.RegisterForm;
import ar.edu.itba.paw.cryptuki.form.legacy.ProfilePicForm;
import ar.edu.itba.paw.exception.BadMultipartFormatException;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.ProfilePicture;
import ar.edu.itba.paw.model.Role;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.parameterObject.UserPO;
import ar.edu.itba.paw.service.ProfilePicService;
import ar.edu.itba.paw.service.UserService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Path("/api/users")
public class UserController {

    private final UserService userService;

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

        UserDto userDto = userService.getUserByUsername(username)
                .map(u->UserDto.fromUser(u, uriInfo))
                .orElseThrow(()-> new NoSuchUserException(username));

        return Response.ok(userDto).build();
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

    @GET
    @Path("/{username}/contactInformation")
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


    // TODO - These two endpoints are the two most polemic ones !!!!
    @PUT
    @Path("/{username}/password")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    public Response changePassword(
            @Valid ChangePasswordForm changePasswordForm,
            @PathParam("username") String username,
            @QueryParam("code") Integer code
    ){

        User user = userService.getUserByUsername(username).orElseThrow(()->new NoSuchUserException(username));

        if(changePasswordForm.getPassword() == null)
            userService.changePasswordAnonymously(user.getEmail());
        else {

            String password = changePasswordForm.getPassword();

            if(code != null)
                userService.changePassword(username, code, password);
            else
                userService.changePassword(username, password);
        }

        return Response.ok().build();
    }

    // TODO - How is the consumer of the api supposed to know the location of this endpoint?
    @POST
    @Path("/{username}/validations")
    public Response createValidation(@NotNull @QueryParam("code") Integer code, @PathParam("username") String username){

        User user = userService.getUserByUsername(username).orElseThrow(()->new NoSuchUserException(username));

        if (!userService.verifyUser(username, code))
            throw new BadRequestException();

        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path("/api/users")
                .path(username)
                .build();

        return Response.created(uri).build();
    }


}

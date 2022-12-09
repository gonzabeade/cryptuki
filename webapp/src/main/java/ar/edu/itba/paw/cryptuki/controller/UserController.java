package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.auth.jwt.JwtUtils;
import ar.edu.itba.paw.cryptuki.dto.UserDto;
import ar.edu.itba.paw.cryptuki.form.ChangePasswordForm;
import ar.edu.itba.paw.cryptuki.form.RegisterForm;
import ar.edu.itba.paw.cryptuki.form.legacy.ProfilePicForm;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.ProfilePicture;
import ar.edu.itba.paw.model.parameterObject.UserPO;
import ar.edu.itba.paw.service.ProfilePicService;
import ar.edu.itba.paw.service.UserService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;

// TODO: verify user
// TODO: repeat password?
// TODO: 'me' endpoint is not correct. Check for Sotuyos answer

@Path("/api/users")
public class UserController {

    private final UserService userService;
    private final UserDetailsService userDetailsService;
   private final ProfilePicService profilePicService;

    @Context
    public UriInfo uriInfo;

    @Autowired
    public UserController(UserService userService, UserDetailsService userDetailsService, ProfilePicService profilePicService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.profilePicService = profilePicService;
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

    @PUT
    @Path("/{username}/password")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    public Response changePassword(
            @Valid ChangePasswordForm changePasswordForm,
            @PathParam("username") String username,
            @QueryParam("code") Integer code ){

        Optional<UserDto> maybeUser = userService.getUserByUsername(username).map( u -> UserDto.fromUser(u, uriInfo));

        if (!maybeUser.isPresent())
            throw new NoSuchUserException(username);

        //TODO: ver que los valores posibles de los codigos generados sean numeros positivos
        //TODO: no se si hay una opcion mejor entre pasar el codigo por parametro o en el mismo objeto
        //TODO: cuando no se esta autenticado y se tiene codigo parece que no le gusta el authentication.principal.username y tira illegal argument exception, mirar que hacer con eso

        if(changePasswordForm.getPassword() == null)
            userService.changePasswordAnonymously(maybeUser.get().getEmail());
        else {

            String password = changePasswordForm.getPassword();

            if(code != null)
                userService.changePassword(username, code, password);
            else
                userService.changePassword(username, password);
        }

        return Response.ok().build();
    }

    @POST
    @Path("/{username}/validations")
    public Response createValidation(@NotNull @QueryParam("code") Integer code, @PathParam("username") String username){

        if(userService.verifyUser(username, code))
            return Response.status(Response.Status.CREATED).build();

        return Response.status(Response.Status.BAD_REQUEST).build();
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

        UserDetails userDetails = userDetailsService.loadUserByUsername(userPO.getUsername());

        return Response
                .created(uri)
                .header("Refresh-Token", JwtUtils.generateRefreshToken(userDetails))
                .header("Access-Token", JwtUtils.generateAccessToken(userDetails))
                .build();
    }

    //TODO manejar excepciones que vienen del manejo de las imagenes
    @PUT
    @Path("/{username}/picture")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response changeProfilePic(@FormDataParam("picture") InputStream picture,
                                     @FormDataParam("picture") FormDataContentDisposition pictureMetadata,
                                     @FormDataParam("isBuyer") Boolean isBuyer,
                                     @FormDataParam("type") String type,
                                     @PathParam("username") String username) throws IOException {
        //TODO: ver como manejar las IOExceptions

        //TODO: el type se puede extraer del nombre del archivo, sacando la extension, hay que ver como se manejaria si se lo quiere usar de esa manera
        profilePicService.uploadProfilePicture(username, IOUtils.toByteArray(picture), type);

        return Response.ok().build();
    }

    @GET
    @Path("/{username}/picture")
    @Produces({MediaType.MULTIPART_FORM_DATA})
    public Response getProfilePic(@PathParam("username") String username) throws IOException, URISyntaxException {
        Optional<ProfilePicture> picture = profilePicService.getProfilePicture(username);
        if(picture.isPresent()){
            return Response.ok(picture.get().getBytes()).build();
        }
        //TODO: ver que hacer en el caso en el que la foto sea la default
        return Response.ok().build();
    }

}

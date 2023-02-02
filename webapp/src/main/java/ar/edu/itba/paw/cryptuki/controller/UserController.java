package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.annotation.validation.ValueOfEnum;
import ar.edu.itba.paw.cryptuki.dto.UserDto;
import ar.edu.itba.paw.cryptuki.dto.UserNonceDto;
import ar.edu.itba.paw.cryptuki.form.ChangePasswordForm;
import ar.edu.itba.paw.cryptuki.form.RegisterForm;
import ar.edu.itba.paw.cryptuki.helper.ResponseHelper;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.KycInformation;
import ar.edu.itba.paw.model.KycStatus;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.parameterObject.UserPO;
import ar.edu.itba.paw.service.KycService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/users")
public class UserController {

    private final UserService userService;

    private final KycService kycService;

    @Autowired
    public UserController(UserService userService, KycService kycService) {
        this.userService = userService;
        this.kycService = kycService;
    }

    @Context
    public UriInfo uriInfo;


    @GET
    @Path("/{username}")
    @Produces("application/vnd.cryptuki.v1.user+json")
    public Response getUser(@PathParam("username") String username) {

        UserDto userDto = userService.getUserByUsername(username)
                .map(u->UserDto.fromUser(u, uriInfo))
                .orElseThrow(()-> new NoSuchUserException(username));

        return Response.ok(userDto).build();
    }

    @GET
    @Produces("application/vnd.cryptuki.v1.user-list+json")
    public Response getUsers(@QueryParam("page") @DefaultValue("0") int page,
                                  @QueryParam("per_page") @DefaultValue("5") int pageSize,
                                  @QueryParam("kyc_status") @ValueOfEnum(enumClass = KycStatus.class)
                                      String kycStatus) {
        if( kycStatus == null || !KycStatus.PEN.equals(KycStatus.valueOf(kycStatus)))
            throw new BadRequestException("Only pending kyc can be listed.");
        Collection<KycInformation> pendingKycRequests = this.kycService.getPendingKycRequests(page, pageSize);
       long pendingKycRequestsCount = this.kycService.getPendingKycRequestsCount();

        if (pendingKycRequests.isEmpty())
            return Response.noContent().build();

        List<UserDto> pendingKycUsers = pendingKycRequests
                .stream()
                .map(kycInformation -> UserDto.fromUser(kycInformation.getUser(), uriInfo))
                .collect(Collectors.toList());

        Response.ResponseBuilder rb = Response.ok(new GenericEntity<Collection<UserDto>>(pendingKycUsers) {});
        ResponseHelper.genLinks(rb, uriInfo, page, pageSize, pendingKycRequestsCount);
        return rb.build();
    }


    @POST
    @Produces("application/vnd.cryptuki.v1.nonce-ack+json")
    public Response toUserNonce(@NotNull @QueryParam("email") String email) {
        userService.changePasswordAnonymously(email);
        return Response.ok(UserNonceDto.fromEmail(email)).build();
    }

    @POST
    @Consumes("application/vnd.cryptuki.v1.user+json")
    @Produces("application/vnd.cryptuki.v1.user+json")
    public Response toNewUser(@Valid @NotNull(message = "Body required") RegisterForm registerForm,  @Context HttpServletRequest request) {

        UserPO userPO = registerForm.toParameterObject().withLocale(request.getLocale());
        User newUser = userService.registerUser(userPO);

        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(userPO.getUsername())
                .build();

        return Response.created(uri).entity(UserDto.fromUser(newUser, uriInfo)).build();
    }


    @PUT
    @Path("/{username}/password")
    @Consumes("application/vnd.cryptuki.v1.user-password+json")
    public Response changePassword(@NotNull @Valid ChangePasswordForm changePasswordForm, @PathParam("username") String username){
        userService.changePassword(username, changePasswordForm.getPassword());
        return Response.noContent().build();
    }

    @POST
    @Path("/{username}")
    public Response createValidation(
            @NotNull @QueryParam("code") Integer code,
            @PathParam("username") String username
    ){

        if (!userService.verifyUser(username, code))
            throw new BadRequestException("Bad request");

        final URI uri = uriInfo.getAbsolutePathBuilder().build();
        return Response.noContent().build();
    }

}

package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.annotation.validation.ValueOfEnum;
import ar.edu.itba.paw.cryptuki.dto.UserDto;
import ar.edu.itba.paw.cryptuki.dto.UserNonceDto;
import ar.edu.itba.paw.cryptuki.form.ChangePasswordForm;
import ar.edu.itba.paw.cryptuki.form.RegisterForm;
import ar.edu.itba.paw.cryptuki.form.UserEmailValidationForm;
import ar.edu.itba.paw.cryptuki.helper.ResponseHelper;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.KycInformation;
import ar.edu.itba.paw.model.KycStatus;
import ar.edu.itba.paw.model.parameterObject.UserPO;
import ar.edu.itba.paw.service.KycService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.validation.*;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Path;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/api/users")
public class UserController {

    private final UserService userService;

    private final KycService kycService;
    private final ValidatorFactory validatorFactory;
    @Autowired
    public UserController(UserService userService, KycService kycService, ValidatorFactory validatorFactory) {
        this.userService = userService;
        this.kycService = kycService;
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

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPendingKyc(@QueryParam("page") @DefaultValue("0") int page,
                                  @QueryParam("per_page") @DefaultValue("5") int pageSize,
                                  @QueryParam("kyc_status") @ValueOfEnum(enumClass = KycStatus.class)
                                      String kyc_status) {
        if(!KycStatus.valueOf(kyc_status).equals(KycStatus.PEN))
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
    public Response changePassword(@NotNull @Valid ChangePasswordForm changePasswordForm, @PathParam("username") String username){
        userService.changePassword(username, changePasswordForm.getPassword());
        return Response.noContent().build();
    }

    @POST
    @Path("/{username}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    public Response createValidation(
            @NotNull @Valid UserEmailValidationForm userEmailValidationForm,
            @PathParam("username") String username
    ){

        if (!userService.verifyUser(username, userEmailValidationForm.getCode()))
            throw new BadRequestException("Bad request");

        final URI uri = uriInfo.getAbsolutePathBuilder().build();
        return Response.created(uri).build();
    }

}

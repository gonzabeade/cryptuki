package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.dto.KycDto;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.KycInformation;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.KycService;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Optional;

@Path("/api/users/{username}/kyc")
public class KycController {

    private final UserService userService;

    @Autowired
    public KycController(UserService userService) {
        this.userService = userService;
    }

    @Context
    public UriInfo uriInfo;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getKyc(@PathParam("username") String username) {

        Optional<User> maybeUser = userService.getUserByUsername(username);

        if (!maybeUser.isPresent())
            throw new NoSuchUserException(username);

        Optional<KycInformation> maybeKycInformation = maybeUser.get().getKyc();

        if (!maybeKycInformation.isPresent())
            return Response.noContent().build();

        KycDto dto = KycDto.fromKycInformation(maybeKycInformation.get(), uriInfo);
        return Response.ok(dto).build();
    }

    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteKyc(@PathParam("username") String username) {
        return null;
    }


    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    public Response postKyc(@PathParam("username") String username) {
        return null;
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    public Response putKyc(@PathParam("username") String username) {
        return null;
    }

}

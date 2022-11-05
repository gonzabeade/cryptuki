package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.dto.KycDto;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.KycInformation;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.KycService;
import ar.edu.itba.paw.service.UserService;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.ByteArrayInputStream;
import java.util.Optional;

@Path("/api/users/{username}/kyc")
public class KycController {

    private final UserService userService;
    private final KycService kycService;

    @Autowired
    public KycController(UserService userService, KycService kycService) {
        this.userService = userService;
        this.kycService = kycService;
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

//
//    // TODO: @Consumes() https://eclipse-ee4j.github.io/jersey.github.io/documentation/latest/media.html#multipart
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    @Produces({MediaType.APPLICATION_JSON})
//    public Response postKyc(
//            @PathParam("username") String username,
//            @FormDataParam("data") KycDto kycDto,
//            @FormDataParam("data") FileData bean,
//            ) {
//
//        return null;
//    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON})
    public Response putKyc(@PathParam("username") String username) {
        return null;
    }


    /***
     If we want to get strict, the following 2 endpoints are not REST.
     Instead, they may return different media types depending on the photos submitted.
     However, it is for a good reason: if we wanted to use JSON files, we would have to use Base64 and consume 33% more space.
     It is better to abandon the REST model for a moment and optimize data flow through the network.
     TODO: Add Content-length? Are there any Headers missing?
     ***/
    @GET
    @Path(("/idPhoto"))
    public Response getIdPhoto(@PathParam("username") String username) {

        Optional<User> maybeUser = userService.getUserByUsername(username);

        if (!maybeUser.isPresent())
            throw new NoSuchUserException(username);

        Optional<KycInformation> maybeKycInformation = maybeUser.get().getKyc();

        if (!maybeKycInformation.isPresent())
            return Response.noContent().build();

        KycInformation kycInformation = maybeKycInformation.get();
        return Response
                .ok(new ByteArrayInputStream(kycInformation.getIdPhoto()))
                .type(kycInformation.getIdPhotoType())
                .build();
    }

    @GET
    @Path(("/validationPhoto"))
    public Response getValidationPhoto(@PathParam("username") String username) {

        Optional<User> maybeUser = userService.getUserByUsername(username);

        if (!maybeUser.isPresent())
            throw new NoSuchUserException(username);

        Optional<KycInformation> maybeKycInformation = maybeUser.get().getKyc();

        if (!maybeKycInformation.isPresent())
            return Response.noContent().build();

        KycInformation kycInformation = maybeKycInformation.get();
        return Response
                .ok(new ByteArrayInputStream(kycInformation.getValidationPhoto()))
                .type(kycInformation.getValidationPhotoType())
                .build();
    }

}

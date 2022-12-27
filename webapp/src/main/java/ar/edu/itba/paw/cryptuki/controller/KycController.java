package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.dto.KycDto;
import ar.edu.itba.paw.cryptuki.form.KycForm;
import ar.edu.itba.paw.exception.BadMultipartFormatException;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.KycInformation;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.parameterObject.KycInformationPO;
import ar.edu.itba.paw.service.KycService;
import ar.edu.itba.paw.service.UserService;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Path("/api/users/{username}/kyc")
public class KycController {

    private static long MAX_SIZE = 1 << 21;
    private final UserService userService;
    private final KycService kycService;

    private final Collection<BadMultipartFormatException.MultipartDescriptor> kycMultipartFormat;

    @Autowired
    public KycController(UserService userService, KycService kycService) {
        this.userService = userService;
        this.kycService = kycService;
        this.kycMultipartFormat = Arrays.asList(
                new BadMultipartFormatException.MultipartDescriptor("application/json", "kyc-information"),
                new BadMultipartFormatException.MultipartDescriptor("image/*", "id-photo"),
                new BadMultipartFormatException.MultipartDescriptor("image/*", "validation-photo")
        );
    }

    @Context
    public UriInfo uriInfo;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getKyc(@PathParam("username") String username) {

        User user = userService.getUserByUsername(username).orElseThrow(()->new NoSuchUserException(username));

        Optional<KycInformation> maybeKycInformation = user.getKyc();

        if (!maybeKycInformation.isPresent())
            return Response.noContent().build();

        KycDto dto = KycDto.fromKycInformation(maybeKycInformation.get(), uriInfo);
        return Response.ok(dto).build();
    }


    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postKyc(
            @Valid @FormDataParam("kyc-information") KycForm kycInformation,
            @FormDataParam("id-photo") FormDataBodyPart idPhoto,
            @FormDataParam("validation-photo") FormDataBodyPart validationPhoto
    ) throws IOException {

        if (idPhoto == null || validationPhoto == null || kycInformation == null )
            throw new BadMultipartFormatException(kycMultipartFormat);

        byte[] idPhotoBytes = IOUtils.toByteArray(idPhoto.getValueAs(InputStream.class));
        byte[] validationPhotoBytes = IOUtils.toByteArray(idPhoto.getValueAs(InputStream.class));

        // TODO - If enough time, write it as an annotation
        // --  Replace @MultipartCheck ?
        // --  Replace @MultipartSizeCheck(maxSize = (1<<21)) ?
        if (idPhotoBytes.length > MAX_SIZE || validationPhotoBytes.length > MAX_SIZE)
            throw new IllegalArgumentException(String.format("Uploaded files have a max size of %d bytes", MAX_SIZE));

        KycInformationPO kycInformationPO = kycInformation.toParameterObject()
                .withIdPhoto(idPhotoBytes)
                .withIdPhotoType(idPhoto.getContentDisposition().getType())
                .withValidationPhoto(validationPhotoBytes)
                .withValidationPhotoType(validationPhoto.getContentDisposition().getType());

        kycService.newKycRequest(kycInformationPO);
        final URI uri = uriInfo.getRequestUri();

        return Response.created(uri).build();
    }

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
     ***/
    @GET
    @Path(("/idPhoto"))
    public Response getIdPhoto(@PathParam("username") String username) {

        User user = userService.getUserByUsername(username).orElseThrow(()->new NoSuchUserException(username));

        Optional<KycInformation> maybeKycInformation = user.getKyc();

        if (!maybeKycInformation.isPresent())
            return Response.noContent().build();

        KycInformation kycInformation = maybeKycInformation.get();

        return Response // TODO - Are any headers missing? - Check
                .ok(new ByteArrayInputStream(kycInformation.getIdPhoto()))
                .type(kycInformation.getIdPhotoType())
                .build();
    }

    @GET
    @Path(("/validationPhoto"))
    public Response getValidationPhoto(@PathParam("username") String username) {

        User user = userService.getUserByUsername(username).orElseThrow(()->new NoSuchUserException(username));

        Optional<KycInformation> maybeKycInformation = user.getKyc();

        if (!maybeKycInformation.isPresent())
            return Response.noContent().build();

        KycInformation kycInformation = maybeKycInformation.get();
        return Response
                .ok(new ByteArrayInputStream(kycInformation.getValidationPhoto()))
                .type(kycInformation.getValidationPhotoType())
                .build();
    }

}

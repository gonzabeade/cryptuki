package ar.edu.itba.paw.cryptuki.controller;

import ar.edu.itba.paw.cryptuki.exception.BadMultipartFormatException;
import ar.edu.itba.paw.cryptuki.helper.MultipartDescriptor;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.ProfilePicture;
import ar.edu.itba.paw.service.ProfilePicService;
import ar.edu.itba.paw.service.UserService;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Path("/api/users/{username}/picture")
public class UserProfilePictureController {

    private final UserService userService;
    private final ProfilePicService profilePicService;
    private final Collection<MultipartDescriptor> pictureMultipartFormat;

    @Context
    public UriInfo uriInfo;

    @Autowired
    public UserProfilePictureController(UserService userService, ProfilePicService profilePicService) {
        this.userService = userService;
        this.profilePicService = profilePicService;
        this.pictureMultipartFormat =  Arrays.asList(
                new MultipartDescriptor("image/*", "picture")
        );
    }

    @PUT
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public Response changeProfilePic(@FormDataParam("picture") FormDataBodyPart picture, @PathParam("username") String username) throws IOException {

        if (picture == null)
            throw new BadMultipartFormatException(pictureMultipartFormat);

        // For 404 validation
        userService.getUserByUsername(username).orElseThrow(()->new NoSuchUserException(username));

        Optional<ProfilePicture> profilePicture = profilePicService.getProfilePicture(username);

        byte[] pictureBytes = IOUtils.toByteArray(picture.getValueAs(InputStream.class));
        profilePicService.uploadProfilePicture(username, pictureBytes, picture.getContentDisposition().getType());

        URI uri = uriInfo.getRequestUri();
        return Response.noContent().build();
    }
    @GET
    public Response getProfilePic(@PathParam("username") String username) {

        userService.getUserByUsername(username).orElseThrow(()->new NoSuchUserException(username));
        Optional<ProfilePicture> picture = profilePicService.getProfilePicture(username);

        if (!picture.isPresent())
            return Response.noContent().build();

        return Response
                    .ok(new ByteArrayInputStream(picture.get().getBytes()))
                    .type(picture.get().getImageType())
                    .build();
    }
}

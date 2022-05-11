package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Image;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

public interface ProfilePicService {

    Optional<Image> getProfilePicture(String username) throws URISyntaxException, IOException;

    void uploadProfilePicture(String username, byte[] profilePicture, String type);

}

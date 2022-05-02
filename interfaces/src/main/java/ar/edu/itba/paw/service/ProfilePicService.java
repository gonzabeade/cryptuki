package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Image;
import java.util.Optional;

public interface ProfilePicService {

    Optional<Image> getProfilePicture(String username);

    void uploadProfilePicture(String username, byte[] profilePicture, String type);

}

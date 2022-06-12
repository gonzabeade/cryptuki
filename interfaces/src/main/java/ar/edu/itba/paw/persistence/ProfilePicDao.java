package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.model.ProfilePicture;

import java.util.Optional;

public interface ProfilePicDao {
    Optional<ProfilePicture> getProfilePicture(String username);
    void uploadProfilePicture(String username, byte[] profilePicture, String type);
}

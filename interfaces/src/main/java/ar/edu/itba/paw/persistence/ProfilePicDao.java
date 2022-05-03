package ar.edu.itba.paw.persistence;
import java.util.Optional;

public interface ProfilePicDao {

    Optional<Image> getProfilePicture(String username);

    void uploadProfilePicture(String username, byte[] profilePicture, String type);




}

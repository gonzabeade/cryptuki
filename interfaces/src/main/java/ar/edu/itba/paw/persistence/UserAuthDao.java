package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserAuth;

import java.util.Optional;

public interface UserAuthDao {

    /** User auth creation */
    UserAuth createUserAuth(User user, String username, String password, int verificationCode);

    /** User auth getters. Both username and email are unique, so the result is deterministic. */
    Optional<UserAuth> getUserAuthByUsername(String username);
    Optional<UserAuth> getUserAuthByEmail(String email);

    /** Changes the state of a user to UserStatus.VERIFIED, given that the code matches with the one stored*/
    boolean verifyUser(String username, int code);

    /** Changes the password of a user*/
    boolean changePassword(String username, String newPassword);

    /** Sets the status of a User to USER.KICKED, and disallows it from entering the site in the future */
    void kickoutUser(int userId);
}

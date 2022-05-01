package ar.edu.itba.paw.persistence;

import java.util.Optional;

public interface UserAuthDao {

    Optional<UserAuth> getUserAuthByUsername(String username);

    UserAuth createUserAuth(UserAuth.Builder userAuth);

    boolean verifyUser(String username, int code);

    boolean changePassword(String username, String newPassword);

    Optional<UserAuth> getUsernameByEmail(String email);



}

package ar.edu.itba.paw.persistence;

import java.util.Optional;

public interface UserAuthDao {

    Optional<UserAuth> getUserAuthByUsername(String username);

    UserAuth createUserAuth(UserAuth.Builder userAuth);

    int verifyUser(String username , Integer code);

    int changePassword(String username , Integer code , String password);

    Optional<UserAuth> getUsernameByEmail(String email);


}

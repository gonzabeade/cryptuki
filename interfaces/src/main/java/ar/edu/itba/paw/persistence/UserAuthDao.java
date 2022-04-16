package ar.edu.itba.paw.persistence;

import java.util.Optional;

public interface UserAuthDao {

    Optional<UserAuth> getUserAuthByUsername(String username);

    UserAuth createUserAuth(UserAuth.Builder userAuth);


}

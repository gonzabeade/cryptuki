package ar.edu.itba.paw.persistence;

import java.util.Optional;

public interface UserDao {
    Optional<User> getUserByEmail(String email);

    User createUser(User.Builder user);
    Optional<User> getUserByUsername(String username);
    void updateLastLogin(String username);


}

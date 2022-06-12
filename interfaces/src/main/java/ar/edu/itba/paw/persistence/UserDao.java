package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.User;

import java.util.Optional;

public interface UserDao {

    /** User creation - without auth */
    User createUser(String email, String phoneNumber);

    /** User data getters */
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserByUsername(String username);

    /** User data - modifiers */
    void updateLastLogin(String username);
    void incrementUserRating(String username, int rating);

}

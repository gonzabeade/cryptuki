package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserAuth;

import java.util.Locale;
import java.util.Optional;

public interface UserDao {

    /** User creation */
    User createUser(String email, String phoneNumber, Locale locale);
    User createUser(String email, String phoneNumber, Locale locale, UserAuth userAuth);


    /** User data getters */
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserByUsername(String username);

    /** User data - modifiers */
    void updateUserConfigurationOnLogin(String username, Locale locale);
    void incrementUserRating(String username, int rating);

}

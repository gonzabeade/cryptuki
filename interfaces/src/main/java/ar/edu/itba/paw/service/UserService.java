package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserAuth;
import ar.edu.itba.paw.model.parameterObject.UserPO;

import java.util.Locale;
import java.util.Optional;

public interface UserService {

    /** User creation */
    void registerUser(UserPO userPO);

    /** User getters - both email and usernames are unique */
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserByUsername(String username);

    /** Password manipulation */
    boolean changePassword(String username, int code, String newPassword);
    boolean changePassword(String username, String newPassword);
    void changePasswordAnonymously(String email);

    /** User email account verification */
    boolean verifyUser(String username, Integer code);

    /** Update User statistics */
    void updateUserConfigurationOnLogin(String username, Locale locale);
    void updateRatingBy(String username, int rating);

}

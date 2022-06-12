package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserAuth;

import java.util.Optional;

public interface UserService {

    /** User creation */
    void registerUser(String email, String username, String plainPassword, String phoneNumber);

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
    void updateLastLogin(String username);
    void incrementUserRating(String username, int rating);

}

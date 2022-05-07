package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.persistence.UserAuth;

import java.util.Optional;

public interface UserService {

    void registerUser(UserAuth.Builder authBuilder, User.Builder userBuilder);
    Optional<UserAuth> getUserByUsername(String username);
    void verifyUser(String username , Integer code);
    void sendChangePasswordMail(String email);
    boolean changePassword(String username, int code, String newPassword);
    boolean changePassword(String username, String newPassword);
    void updateLastLogin(String username);
    Optional<User> getUserInformation(String username);

    void incrementUserRating(String username, int rating);

}

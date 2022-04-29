package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.persistence.UserAuth;

import java.util.Optional;

public interface UserService {


    Optional<User> registerUser(UserAuth.Builder authBuilder, User.Builder userBuilder);
    Optional<UserAuth> getUserByUsername(String username);
    void verifyUser(String username , Integer code);
    void sendChangePasswordMail(String email);
    int changePassword(String username, Integer code, String password);

    Optional<User> getUserInformation(String username);
}

package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.persistence.UserAuth;

import java.util.Optional;

public interface UserService {


    Optional<User> registerUser(UserAuth.Builder authBuilder, User.Builder userBuilder);
    Optional<UserAuth> getUserByUsername(String username);
}
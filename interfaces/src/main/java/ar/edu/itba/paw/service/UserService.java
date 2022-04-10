package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.persistence.UserAuth;

public interface UserService {

    User getUser(int id);
    User makeUser(UserAuth.Builder authBuilder, User.Builder userBuilder); // TODO: for the next iteration
}
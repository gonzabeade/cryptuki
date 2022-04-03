package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.User;

public interface UserDao {
    User findById(int id );
}

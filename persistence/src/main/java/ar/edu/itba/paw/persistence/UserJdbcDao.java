package ar.edu.itba.paw.persistence;


import org.springframework.stereotype.Repository;

@Repository
public class UserJdbcDao implements UserDao{

    @Override
    public User findById(int id) {
        return null;
    }
}

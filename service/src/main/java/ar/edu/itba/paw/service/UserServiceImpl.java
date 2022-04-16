package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.persistence.UserAuth;
import ar.edu.itba.paw.persistence.UserAuthDao;
import ar.edu.itba.paw.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserAuthDao userAuthDao;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(final UserDao userDao,final UserAuthDao userAuthDao,final PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.userAuthDao = userAuthDao;
        this.passwordEncoder=passwordEncoder;
    }


    @Override
    public Optional<User> registerUser(UserAuth.Builder authBuilder, User.Builder userBuilder){
        if(userDao.getUserByEmail(userBuilder.getEmail()).isPresent() || userAuthDao.getUserAuthByUsername(authBuilder.getUsername()).isPresent())
           return Optional.empty();
        User user = userDao.createUser(userBuilder);
        authBuilder.id(user.getId());
        authBuilder.password(passwordEncoder.encode(authBuilder.getPassword()));
        userAuthDao.createUserAuth(authBuilder);
        return Optional.of(user);
    }

    @Override
    public Optional<UserAuth> getUserByUsername(String username) {
        return userAuthDao.getUserAuthByUsername(username);
    }
}

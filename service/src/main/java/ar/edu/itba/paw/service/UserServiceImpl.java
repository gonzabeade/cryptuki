package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.exception.PersistenceException;
import ar.edu.itba.paw.exception.ServiceDataAccessException;
import ar.edu.itba.paw.exception.UncategorizedPersistenceException;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserAuth;
import ar.edu.itba.paw.model.UserStatus;
import ar.edu.itba.paw.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserAuthDao userAuthDao;
    private final PasswordEncoder passwordEncoder;
    private final MessageSenderFacade messageSenderFacade;


    @Autowired
    public UserServiceImpl(final UserDao userDao, final UserAuthDao userAuthDao, final PasswordEncoder passwordEncoder, MessageSenderFacade messageSender) {
        this.userDao = userDao;
        this.userAuthDao = userAuthDao;
        this.passwordEncoder = passwordEncoder;
        this.messageSenderFacade = messageSender;
    }

    @Override
    @Transactional
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    public void registerUser(String email, String username, String plainPassword, String phoneNumber){

        if (email == null || username == null || plainPassword == null || phoneNumber == null)
            throw new NullPointerException("Neither Auth nor User builder can be null");

        User user = userDao.createUser(email, phoneNumber);
        String hashedPassword = passwordEncoder.encode(plainPassword);
        int verifyCode = (int)(Math.random()*Integer.MAX_VALUE);
        userAuthDao.createUserAuth(user.getId(), username,  hashedPassword, verifyCode);

        messageSenderFacade.sendWelcomeMessage(email, username, verifyCode);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    @Transactional
    @PreAuthorize("@customPreAuthorizer.doesUserHaveCode(#username, #code)")
    public boolean changePassword(String username, int code, String newPassword) {

        if (newPassword == null)
            throw new NullPointerException("New password cannot be null");

        if (code < 0)
            throw new IllegalArgumentException("Code must be non negative");

        Optional<UserAuth> userAuth;
        try {
            userAuth = userAuthDao.getUserAuthByUsername(username);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }

        if (userAuth.isPresent() && userAuth.get().getCode() == code)
            return userAuthDao.changePassword(username, passwordEncoder.encode(newPassword));
        else return false;
    }

    @Override
    @Transactional
    @PreAuthorize("#username == authentication.principal.username")
    public boolean changePassword(String username, String newPassword) {

        if (newPassword == null)
            throw new NullPointerException("New password cannot be null");

        try {
            return userAuthDao.changePassword(username, passwordEncoder.encode(newPassword));
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    public void changePasswordAnonymously(String email) {
        Optional<UserAuth> maybeUser = userAuthDao.getUserAuthByEmail(email);
        if (!maybeUser.isPresent() || maybeUser.get().getUserStatus().equals(UserStatus.UNVERIFIED))
            throw new NoSuchUserException(email);

        UserAuth user = maybeUser.get();
        messageSenderFacade.sendChangePasswordMessage(user.getUsername(), user.getCode());
    }

    @Override
    @Transactional
    public boolean verifyUser(String username, Integer code) {
        return userAuthDao.verifyUser(username,code);
    }

    @Override
    @PreAuthorize("#username == authentication.principal.username")
    @Transactional
    public void updateLastLogin(String username) {
        userDao.updateLastLogin(username);
    }

    @Override
    public void updateRatingBy(String username, int rating) {
        userDao.incrementUserRating(username, rating);
    }


}

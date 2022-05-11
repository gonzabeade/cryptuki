package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.exception.PersistenceException;
import ar.edu.itba.paw.exception.ServiceDataAccessException;
import ar.edu.itba.paw.exception.UncategorizedPersistenceException;
import ar.edu.itba.paw.persistence.*;
import ar.edu.itba.paw.service.mailing.ChangePasswordThymeleafMailMessage;
import ar.edu.itba.paw.service.mailing.MailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final ar.edu.itba.paw.persistence.UserDao userDao;
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
    public void registerUser(UserAuth.Builder authBuilder, User.Builder userBuilder){

        if (authBuilder == null || userBuilder == null)
            throw new NullPointerException("Neither Auth nor User builder can be null");

        User user;
        try {
           user = userDao.createUser(userBuilder);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }


        authBuilder.withId(user.getId());
        authBuilder.withPassword(passwordEncoder.encode(authBuilder.getPassword()));
        authBuilder.withCode((int)(Math.random()*Integer.MAX_VALUE));
        authBuilder.withUserStatus(UserStatus.UNVERIFIED);
        authBuilder.withRole("ROLE_USER");

        try {
            userAuthDao.createUserAuth(authBuilder);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }

        messageSenderFacade.sendWelcomeMessage(userBuilder.getEmail(), authBuilder.getUsername(), authBuilder.getCode());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserAuth> getUserByUsername(String username) {
        try {
            return userAuthDao.getUserAuthByUsername(username);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @Transactional()
    public boolean verifyUser(String username, Integer code) {

        boolean verified;

        try {
            verified = userAuthDao.verifyUser(username,code);
        } catch (PersistenceException pe) {
            throw new UncategorizedPersistenceException(pe);
        }

        return verified;
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
            boolean result = userAuthDao.changePassword(username, passwordEncoder.encode(newPassword));

            messageSenderFacade.sendChangePasswordMessage(getUserInformation(username).get().getEmail(),
                    username,
                    getUserByUsername(username).get().getCode()
                    );

            return result;
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    @PreAuthorize("#username == authentication.principal.username")
    public void updateLastLogin(String username) {

        try {
            userDao.updateLastLogin(username);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    public Optional<User> getUserInformation(String username) {

        if (username == null) {
            throw new NullPointerException("Username cannot be null");
        }

        try {
            return userDao.getUserByUsername(username);
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }

    @Override
    public Optional<UserAuth> getUserAuthByEmail(String email) {
        return userAuthDao.getUserAuthByEmail(email);
    }

    @Override
    public void incrementUserRating(String username, int rating) {

        if (rating < 0)
            throw new IllegalArgumentException("Rating can only be non negative");

        if (username == null) {
            throw new NullPointerException("Username cannot be null");
        }

        try {
            userDao.incrementUserRating(username, rating);
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
        messageSenderFacade.sendChangePasswordMessage(email,user.getUsername(), user.getCode());
    }

    @Override
    public boolean userExists(String username, String email) {
         return userDao.getUserByEmail(email).isPresent() || userDao.getUserByUsername(username).isPresent();
    }
}

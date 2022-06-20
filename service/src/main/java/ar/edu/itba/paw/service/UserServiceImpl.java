package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserAuth;
import ar.edu.itba.paw.model.UserStatus;
import ar.edu.itba.paw.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
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
    public void registerUser(String email, String username, String plainPassword, String phoneNumber, Locale locale){

        if (email == null || username == null || plainPassword == null || phoneNumber == null)
            throw new NullPointerException("Neither Auth nor User builder can be null");

        User user = userDao.createUser(email, phoneNumber, locale);
        String hashedPassword = passwordEncoder.encode(plainPassword);
        int verifyCode = (int)(Math.random()*Integer.MAX_VALUE);
        userAuthDao.createUserAuth(user, username,  hashedPassword, verifyCode);
        messageSenderFacade.sendWelcomeMessage(user, verifyCode);
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

        Optional<UserAuth> userAuth = userAuthDao.getUserAuthByUsername(username);
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

        return userAuthDao.changePassword(username, passwordEncoder.encode(newPassword));
    }

    @Override
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    public void changePasswordAnonymously(String email) {
        Optional<UserAuth> maybeUser = userAuthDao.getUserAuthByEmail(email);
        if (!maybeUser.isPresent() || maybeUser.get().getUserStatus().equals(UserStatus.UNVERIFIED))
            throw new NoSuchUserException(email);

        UserAuth user = maybeUser.get();
        messageSenderFacade.sendForgotPasswordMessage(user.getUser(), user.getCode());
    }

    @Override
    @Transactional
    public boolean verifyUser(String username, Integer code) {
        return userAuthDao.verifyUser(username,code);
    }

    @Override
    @PreAuthorize("#username == authentication.principal.username")
    @Transactional
    public void updateUserConfigurationOnLogin(String username, Locale locale) {
        LocaleContextHolder.setLocale(locale);
        userDao.updateUserConfigurationOnLogin(username, locale);
    }

    @Override
    public void updateRatingBy(String username, int rating) {
        userDao.incrementUserRating(username, rating);
    }


}


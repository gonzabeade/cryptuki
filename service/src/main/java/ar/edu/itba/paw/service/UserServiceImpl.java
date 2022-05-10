package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.PersistenceException;
import ar.edu.itba.paw.exception.ServiceDataAccessException;
import ar.edu.itba.paw.exception.UncategorizedPersistenceException;
import ar.edu.itba.paw.persistence.User;
import ar.edu.itba.paw.persistence.UserAuth;
import ar.edu.itba.paw.persistence.UserAuthDao;
import ar.edu.itba.paw.persistence.UserStatus;
import ar.edu.itba.paw.service.mailing.MailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final ar.edu.itba.paw.persistence.UserDao userDao;
    private final UserAuthDao userAuthDao;
    private final PasswordEncoder passwordEncoder;
    private final ContactService<MailMessage> contactService;


    @Autowired
    public UserServiceImpl(final ar.edu.itba.paw.persistence.UserDao userDao, final UserAuthDao userAuthDao, final PasswordEncoder passwordEncoder, ContactService<MailMessage> contactService) {
        this.userDao = userDao;
        this.userAuthDao = userAuthDao;
        this.passwordEncoder = passwordEncoder;
        this.contactService = contactService;
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

        // TODO: modularizar y logica de negocio con Salva!

       String message_body =  "Hola " + authBuilder.getUsername() + ",\n"
                + "Antes de que puedas comenzar a comprar y vender crypto debes verificar tu identidad.\n"
                + "Puedes hacer esto ingresando el codigo " + authBuilder.getCode()
                + " en el lugar indicado o entrando al siguiente link:\n"
                + "http://pawserver.it.itba.edu.ar/paw-2022a-01/verify?user="+authBuilder.getUsername() +"&code="+authBuilder.getCode();
        MailMessage message = contactService.createMessage(userBuilder.getEmail());
        message.setSubject("Verifica tu cuenta.");
        message.setBody(message_body);
        contactService.sendMessage(message);
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
            return userAuthDao.changePassword(username, passwordEncoder.encode(newPassword));
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
            throw new RuntimeException("Invalid email");

        UserAuth user = maybeUser.get();
        MailMessage message = contactService.createMessage(email);
        message.setSubject("Change your password");
        message.setBody("http://localhost:8080/webapp/recoverPassword?user=" + user.getUsername() + "&code=" + user.getCode());
        contactService.sendMessage(message);
    }

    @Override
    public boolean userExists(String username, String email) {
         return userDao.getUserByEmail(email).isPresent() || userDao.getUserByUsername(username).isPresent();
    }
}

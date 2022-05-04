package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserAuthDao userAuthDao;
    private final PasswordEncoder passwordEncoder;
    private final ContactService<MailMessage> contactService;


    @Autowired
    public UserServiceImpl(final UserDao userDao, final UserAuthDao userAuthDao, final PasswordEncoder passwordEncoder, ContactService<MailMessage> contactService) {
        this.userDao = userDao;
        this.userAuthDao = userAuthDao;
        this.passwordEncoder=passwordEncoder;
        this.contactService = contactService;
    }



    @Transactional
    @Override
    public void registerUser(UserAuth.Builder authBuilder, User.Builder userBuilder){
        User user = userDao.createUser(userBuilder);
        authBuilder.withId(user.getId());
        authBuilder.withPassword(passwordEncoder.encode(authBuilder.getPassword()));
        authBuilder.withCode((int)(Math.random()*Integer.MAX_VALUE));
        authBuilder.withUserStatus(UserStatus.UNVERIFIED);
        authBuilder.withRole("seller");
        userAuthDao.createUserAuth(authBuilder);

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
    public Optional<UserAuth> getUserByUsername(String username) {
        Optional<UserAuth> maybeUserAuth = userAuthDao.getUserAuthByUsername(username);
        return maybeUserAuth;
    }

    @Override
    public void verifyUser(String username, Integer code) {
        if (!userAuthDao.verifyUser(username,code)){
            throw new RuntimeException("Incorrect Verification");
        };
    }

    public void sendChangePasswordMail(String email){
        Optional<UserAuth> maybeUser = userAuthDao.getUsernameByEmail(email);
        if(!maybeUser.isPresent() || maybeUser.get().getUserStatus().equals(UserStatus.UNVERIFIED))
            throw new RuntimeException("Invalid email");

        UserAuth user = maybeUser.get();
        MailMessage message = contactService.createMessage(email);
        message.setSubject("Change your password");
        message.setBody("http://localhost:8080/webapp/recoverPassword?user="+user.getUsername()+"&code="+user.getCode());
        contactService.sendMessage(message);
    }

    @Override
    public boolean changePassword(String username, int code, String newPassword) {
        Optional<UserAuth> userAuth = userAuthDao.getUserAuthByUsername(username);
        if (userAuth.isPresent() && userAuth.get().getCode() == code)
            return userAuthDao.changePassword(username, passwordEncoder.encode(newPassword));
        return false;
    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        //validate oldPassword.

        return userAuthDao.changePassword(username, passwordEncoder.encode(newPassword));
    }

    @Override
    public void updateLastLogin(String username) {
        userDao.updateLastLogin(username);
    }

    @Override
    public Optional<User> getUserInformation(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public void incrementUserRating(String username, int rating) {
        userDao.incrementUserRating(username, rating);
    }
}

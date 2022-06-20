package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserAuth;
import ar.edu.itba.paw.model.UserStatus;
import ar.edu.itba.paw.persistence.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.text.html.Option;
import java.util.Locale;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserAuthDao userAuthDao;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user = new User("salvaCasta@gmail.com", "12345678", 7, 58, Locale.forLanguageTag("en-US"));
    private UserAuth userAuth = new UserAuth(user.getId(), "salvaCasta", "castaSalva", 0);


    //TODO:SALVA por algun motivo no devuelve true userAuthDao.changePassword
    @Test
    public void testSuccessCodeChangePasswordTest()  {
        user.setUserAuth(userAuth);
        when(userAuthDao.getUserAuthByUsername(anyString())).thenReturn(Optional.of(userAuth));
        when(userAuthDao.changePassword(anyString(), anyString())).thenReturn(true);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        boolean changed = userService.changePassword(userAuth.getUsername(), userAuth.getCode(), "newPassword");

        Assert.assertTrue(changed);
    }

    @Test
    public void testErroneousCodeChangePasswordTest()  {
        user.setUserAuth(userAuth);
        when(userAuthDao.getUserAuthByUsername(anyString())).thenReturn(Optional.of(userAuth));

        boolean changed = userService.changePassword(userAuth.getUsername(), userAuth.getCode()+1, "newPassword");

        Assert.assertFalse(changed);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCodeChangePassword()  {

        userService.changePassword(userAuth.getUsername(), -1, "newPassword");

    }

    @Test(expected = NoSuchUserException.class)
    public void testInvalidAnonymouslChangePassword() {
        userAuth.setUserStatus(UserStatus.UNVERIFIED);
        when(userAuthDao.getUserAuthByEmail(anyString())).thenReturn(Optional.of(userAuth));

        userService.changePasswordAnonymously(user.getEmail());
    }

}

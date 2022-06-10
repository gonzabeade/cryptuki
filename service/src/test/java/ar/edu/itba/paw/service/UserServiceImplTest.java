package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.UserStatus;
import ar.edu.itba.paw.persistence.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {


    @Mock
    private UserAuthDao userAuthDao;
    @Mock
    private UserDao userdao;

    @Mock
    private MessageSenderFacade messageSenderFacade;

    @Mock
    private PasswordEncoder passwordEncoder;

    private static String username="shadad";
    private static String password ="pass" ;
    private static Integer code = 355;


//    @InjectMocks
//    private UserService userService = new UserServiceImpl(userdao,userAuthDao,passwordEncoder,messageSenderFacade);


    @Test
    public void BadCodechangePasswordTest()  {
//        UtilsClass.UserAuthPublic userAuthPublic = (UtilsClass.UserAuthPublic) new UtilsClass.UserAuthPublic(username,password).withCode(code).withId(0).withRole("seller").withUserStatus(UserStatus.UNVERIFIED);
//        when(userAuthDao.getUserAuthByUsername(username)).thenReturn(
//                Optional.of(userAuthPublic.build())
//        );

//        boolean answer = userService.changePassword(username,code+1,password);

//        Assert.assertEquals(false,answer);
    }

    @Test
    public void SuccessCodechangePasswordTest()  {
//        UtilsClass.UserAuthPublic userAuthPublic = (UtilsClass.UserAuthPublic) new UtilsClass.UserAuthPublic(username,password).withCode(code).withId(0).withRole("seller").withUserStatus(UserStatus.UNVERIFIED);
//        when(userAuthDao.getUserAuthByUsername(username)).thenReturn(
//                Optional.of(userAuthPublic.build())
//        );
//        when(passwordEncoder.encode(password)).thenReturn(password);
//        when(userAuthDao.changePassword(username,password)).thenReturn(true);
//
//        boolean answer = false; // userService.changePassword(username,code,password);
//
//        Assert.assertEquals(true,answer);
    }





}

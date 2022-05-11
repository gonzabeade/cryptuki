package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.*;
import ar.edu.itba.paw.service.mailing.MailMessage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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
    private ContactService<MailMessage> contactService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private static String username="shadad";
    private static String password ="pass" ;
    private static Integer code = 355;


    @InjectMocks
    private UserService userService = new UserServiceImpl(userdao,userAuthDao,passwordEncoder,contactService);

    private class UserAuthPublic extends UserAuth.Builder{


        public UserAuthPublic(String username, String password) {
            super(username, password);
        }

        @Override
        protected UserAuth build() {
            return super.build();
        }
    }


    private class UserPublic extends User.Builder{
        public UserPublic(String email) {
            super(email);
        }

        @Override
        protected User build() {
            return super.build();
        }
    }

    private class OfferPublic extends Offer.Builder{
        public OfferPublic(int id, User seller, Cryptocurrency crypto, float askingPrice) {
            super(id, seller, crypto, askingPrice);
        }

        @Override
        protected Offer build() {
            return super.build();
        }
    }

    private class complainPublic extends Complain.Builder{
        public complainPublic(String complainerUsername) {
            super(complainerUsername);
        }

        @Override
        protected Complain build() {
            return super.build();
        }
    }

    private class tradePublic extends Trade.Builder{
        public tradePublic(int offerId, String buyerUsername) {
            super(offerId, buyerUsername);
        }
        @Override
        protected Trade build() {
            return super.build();
        }
    }
    @Test
    public void BadCodechangePasswordTest()  {
        UserAuthPublic userAuthPublic = (UserAuthPublic) new UserAuthPublic(username,password).withCode(code).withId(0).withRole("seller").withUserStatus(UserStatus.UNVERIFIED);
        when(userAuthDao.getUserAuthByUsername(username)).thenReturn(
                Optional.of(userAuthPublic.build())
        );

        boolean answer = userService.changePassword(username,code+1,password);

        Assert.assertEquals(false,answer);
    }

    @Test
    public void SuccessCodechangePasswordTest()  {
        UserAuthPublic userAuthPublic = (UserAuthPublic) new UserAuthPublic(username,password).withCode(code).withId(0).withRole("seller").withUserStatus(UserStatus.UNVERIFIED);
        when(userAuthDao.getUserAuthByUsername(username)).thenReturn(
                Optional.of(userAuthPublic.build())
        );
        when(passwordEncoder.encode(password)).thenReturn(password);
        when(userAuthDao.changePassword(username,password)).thenReturn(true);

        boolean answer = userService.changePassword(username,code,password);

        Assert.assertEquals(true,answer);
    }





}

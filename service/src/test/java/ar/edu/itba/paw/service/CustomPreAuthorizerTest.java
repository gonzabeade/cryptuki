package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.parameterObject.KycInformationPO;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.KycDao;
import ar.edu.itba.paw.persistence.OfferDao;
import ar.edu.itba.paw.persistence.TradeDao;
import ar.edu.itba.paw.persistence.UserDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomPreAuthorizerTest {
    @Mock
    private UserDao userDao;
    @Mock
    private OfferDao offerDao;
    @Mock
    private TradeDao tradeDao;
    @Mock
    private UserDetails userDetails;

    @InjectMocks
    CustomPreAuthorizer customPreAuthorizer;

    private User user = new User("salvaCasta@gmail.com", "12345678", 7, 58, Locale.forLanguageTag("en-US"));
    private Offer offer = new Offer.Builder(10, 50,100).withSeller(user).build();
    private Trade trade = new Trade(offer, user, 20);
    private UserAuth userAuth = new UserAuth(user.getId(), "salvaCasta", "castaSalva", 0);
    private UserAuth otherAuth = new UserAuth(user.getId()+1, "gbeade", "beadeg", 1);
    private KycInformationPO kycPO = new KycInformationPO("salvaCasta", "Salvador", "Castagnino");
    private KycInformation kyc = new KycInformation(kycPO, user);
    private OfferFilter restrictedToIdFilter = new OfferFilter().restrictedToId(0);

    @Test
    public void testValidUserUploadOffer(){
        userAuth.setUserStatus(UserStatus.VERIFIED);
        kyc.setStatus(KycStatus.APR);
        user.setKyc(kyc);
        user.setUserAuth(userAuth);
        when(userDao.getUserByUsername(anyString())).thenReturn(Optional.of(user));
        when(userDetails.getUsername()).thenReturn(userAuth.getUsername());

        boolean uploaded = customPreAuthorizer.canUserUploadOffer(userDetails);

        Assert.assertTrue(uploaded);
    }

    @Test
    public void testUnverifiedUserUploadOffer(){
        userAuth.setUserStatus(UserStatus.UNVERIFIED);
        kyc.setStatus(KycStatus.APR);
        user.setKyc(kyc);
        user.setUserAuth(userAuth);
        when(userDao.getUserByUsername(anyString())).thenReturn(Optional.of(user));
        when(userDetails.getUsername()).thenReturn(userAuth.getUsername());

        boolean uploaded = customPreAuthorizer.canUserUploadOffer(userDetails);

        Assert.assertFalse(uploaded);
    }

    @Test
    public void testUnapprovedKycUserUploadOffer(){
        userAuth.setUserStatus(UserStatus.VERIFIED);
        kyc.setStatus(KycStatus.PEN);
        user.setKyc(kyc);
        user.setUserAuth(userAuth);
        when(userDao.getUserByUsername(anyString())).thenReturn(Optional.of(user));
        when(userDetails.getUsername()).thenReturn(userAuth.getUsername());

        boolean uploaded = customPreAuthorizer.canUserUploadOffer(userDetails);

        Assert.assertFalse(uploaded);
    }

    @Test
    public void testValidUserAlterOffer(){
        kyc.setStatus(KycStatus.APR);
        ArrayList<Offer> singleOfferArray = new ArrayList<>();
        singleOfferArray.add(offer);
        user.setKyc(kyc);
        user.setUserAuth(userAuth);

        when(offerDao.getOffersBy(Mockito.any(OfferFilter.class))).thenReturn(singleOfferArray);
        when(userDetails.getUsername()).thenReturn(userAuth.getUsername());

        boolean canAlter = customPreAuthorizer.canUserAlterOffer(userDetails, 0);

        Assert.assertTrue(canAlter);
    }

    @Test
    public void testNonSellerUserAlterOffer(){
        kyc.setStatus(KycStatus.APR);
        ArrayList<Offer> singleOfferArray = new ArrayList<>();
        singleOfferArray.add(offer);
        user.setKyc(kyc);
        user.setUserAuth(otherAuth);

        when(offerDao.getOffersBy(Mockito.any(OfferFilter.class))).thenReturn(singleOfferArray);
        when(userDetails.getUsername()).thenReturn(userAuth.getUsername());

        boolean canAlter = customPreAuthorizer.canUserAlterOffer(userDetails, 0);

        Assert.assertFalse(canAlter);
    }

    @Test
    public void testNonApprovedUserAlterOffer(){
        kyc.setStatus(KycStatus.PEN);
        ArrayList<Offer> singleOfferArray = new ArrayList<>();
        singleOfferArray.add(offer);
        user.setKyc(kyc);
        user.setUserAuth(userAuth);

        when(offerDao.getOffersBy(Mockito.any(OfferFilter.class))).thenReturn(singleOfferArray);
        when(userDetails.getUsername()).thenReturn(userAuth.getUsername());

        boolean canAlter = customPreAuthorizer.canUserAlterOffer(userDetails, 0);

        Assert.assertFalse(canAlter);
    }

    @Test
    public void testUserIsOwnerOfTrade(){
        user.setUserAuth(userAuth);
        when(tradeDao.getTradeById(anyInt())).thenReturn(Optional.of(trade));
        when(userDetails.getUsername()).thenReturn(userAuth.getUsername());

        boolean isOwner = customPreAuthorizer.isUserOwnerOfTrade(userDetails, 0);

        Assert.assertTrue(isOwner);
    }

    @Test
    public void testUserIsNotOwnerOfTrade(){
        user.setUserAuth(otherAuth);
        when(tradeDao.getTradeById(anyInt())).thenReturn(Optional.of(trade));
        when(userDetails.getUsername()).thenReturn(userAuth.getUsername());

        boolean isOwner = customPreAuthorizer.isUserOwnerOfTrade(userDetails, 0);

        Assert.assertFalse(isOwner);
    }

    @Test
    public void testUserIsPartOfTrade(){
        when(tradeDao.getTradeById(anyInt())).thenReturn(Optional.of(trade));
        when(userDetails.getUsername()).thenReturn(userAuth.getUsername());
        user.setUserAuth(userAuth);

        boolean isPartOfTrade = customPreAuthorizer.isUserPartOfTrade(userDetails, 0);

        Assert.assertTrue(isPartOfTrade);
    }

    @Test
    public void testUserIsNotPartOfTrade(){
        when(tradeDao.getTradeById(anyInt())).thenReturn(Optional.of(trade));
        when(userDetails.getUsername()).thenReturn(userAuth.getUsername());
        user.setUserAuth(otherAuth);

        boolean isPartOfTrade = customPreAuthorizer.isUserPartOfTrade(userDetails, 0);

        Assert.assertFalse(isPartOfTrade);
    }

}

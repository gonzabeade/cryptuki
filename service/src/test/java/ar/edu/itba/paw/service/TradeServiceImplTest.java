package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.NoSuchOfferException;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.Offer;
import ar.edu.itba.paw.model.Trade;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserAuth;
import ar.edu.itba.paw.persistence.TradeDao;
import ar.edu.itba.paw.persistence.UserAuthDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Locale;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TradeServiceImplTest {


    @Mock
    private  OfferService offerService;
    @Mock
    private  TradeDao tradeDao;
    @Mock
    private  UserAuthDao userAuthDao;
    @Mock
    private  MessageSenderFacade messageSenderFacade;
    @Mock
    private UserService userService;

    private User buyer = new User("salvaCasta@gmail.com", "12345678", 7, 58, Locale.forLanguageTag("en-US"));
    private UserAuth buyerAuth = new UserAuth(0, "salvaCasta", "castaSalva", 0);

    private User seller = new User("ahadad@gmail.com", "87654321", 8, 60, Locale.forLanguageTag("en-US"));
    private UserAuth sellerAuth = new UserAuth(1, "shadad", "hadads", 1);

    private Offer offer = new Offer.Builder(10, 50,100).withSeller(seller).build();
    private Trade trade = new Trade(offer, buyer, 20);


    @InjectMocks
    private TradeServiceImpl tradeService;

    @Test
    public void testRateCounterpartAsBuyer(){
        buyer.setUserAuth(buyerAuth);
        seller.setUserAuth(sellerAuth);
        when(tradeDao.getTradeById(anyInt())).thenReturn(Optional.of(trade));
        doNothing().when(userService).updateRatingBy(anyString(), anyInt());

        tradeService.rateCounterPartUserRegardingTrade(buyerAuth.getUsername(), 8, 0);

        Assert.assertTrue(trade.isSellerRated());
    }

    @Test
    public void testRateCounterpartAsSeller(){
        buyer.setUserAuth(buyerAuth);
        seller.setUserAuth(sellerAuth);
        when(tradeDao.getTradeById(anyInt())).thenReturn(Optional.of(trade));
        doNothing().when(userService).updateRatingBy(anyString(), anyInt());

        tradeService.rateCounterPartUserRegardingTrade(sellerAuth.getUsername(), 8, 0);

        Assert.assertTrue(trade.isBuyerRated());
    }

    //TODO:SALVA tests viejos de salta supongo
//    @Test(expected = NoSuchOfferException.class)
//    public void makeTradeWithInvalidOffer(){
////        Trade.Builder trade = new Trade.Builder(0,"shadad");
////        when(offerService.getOfferById(0)).thenReturn(Optional.empty());
////        tradeService.makeTrade(trade);
//    }
//
//    @Test(expected = NoSuchUserException.class)
//    public void makeTradeWithInvalidUserAuth(){
////        Trade.Builder trade = new Trade.Builder(0,"shadad");
////        UtilsClass.UserPublic user = (UtilsClass.UserPublic)new UtilsClass.UserPublic("shadad@itba.edu.ar").withId(5);
////        UtilsClass.OfferPublic offer =(UtilsClass.OfferPublic) new UtilsClass.OfferPublic(0,user.build(),null,18.5f).withMinQuantity(0).withMaxQuantity(5);
////
////        when(offerService.getOfferById(0)).thenReturn(Optional.of(offer.build()));
////        when(userAuthDao.getUserAuthByEmail("shadad@itba.edu.ar")).thenReturn(Optional.empty());
////        tradeService.makeTrade(trade);
//    }


}

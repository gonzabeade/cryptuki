package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.persistence.OfferDao;
import ar.edu.itba.paw.persistence.TradeDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Locale;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TradeServiceImplTest {

    private static final float DELTA = 0.0000000000001f;

    @Mock
    private  TradeDao tradeDao;
    @Mock
    private OfferDao offerDao;
    @Mock
    private UserService userService;

    private User buyer = new User("salvaCasta@gmail.com", "12345678", 7, 58, Locale.forLanguageTag("en-US"));
    private UserAuth buyerAuth = new UserAuth(0, "salvaCasta", "castaSalva", 0);

    private User seller = new User("ahadad@gmail.com", "87654321", 8, 60, Locale.forLanguageTag("en-US"));
    private UserAuth sellerAuth = new UserAuth(1, "shadad", "hadads", 1);

    private Offer offer = new Offer.Builder(10, 50,100).withSeller(seller).build();
    private Trade greaterTrade = new Trade(offer, buyer, 3000);

    private Trade smallerTrade = new Trade(offer, buyer, 10);


    @InjectMocks
    private TradeServiceImpl tradeService;

    @Test
    public void testSellOfferWithGreaterNewMax(){
        Trade acceptedTrade = greaterTrade;
        acceptedTrade.setStatus(TradeStatus.ACCEPTED);
        when(tradeDao.getTradeById(anyInt())).thenReturn(Optional.of(acceptedTrade));
        when(tradeDao.changeTradeStatus(anyInt(), Mockito.any(TradeStatus.class))).thenReturn(acceptedTrade);
        when(offerDao.modifyOffer(Mockito.any(Offer.class))).thenReturn(offer);

        Trade testedTrade = tradeService.sellTrade(0);

        Assert.assertEquals(0, offer.getMaxInCrypto(), DELTA);
        Assert.assertEquals(0, offer.getMaxInCrypto(), DELTA);
    }

    @Test
    public void testSellOfferWithSmallerNewMax(){
        Trade acceptedTrade = smallerTrade;
        acceptedTrade.setStatus(TradeStatus.ACCEPTED);
        double oldMax = offer.getMaxInCrypto();
        when(tradeDao.getTradeById(anyInt())).thenReturn(Optional.of(acceptedTrade));
        when(tradeDao.changeTradeStatus(anyInt(), Mockito.any(TradeStatus.class))).thenReturn(acceptedTrade);
        when(offerDao.modifyOffer(Mockito.any(Offer.class))).thenReturn(offer);

        Trade testedTrade = tradeService.sellTrade(0);

        Assert.assertEquals(oldMax - acceptedTrade.getQuantity()/ offer.getUnitPrice(), offer.getMaxInCrypto(), DELTA);
    }

    @Test
    public void testRateCounterpartAsBuyer(){
        Trade soldTrade = greaterTrade;
        soldTrade.setStatus(TradeStatus.SOLD);
        buyer.setUserAuth(buyerAuth);
        seller.setUserAuth(sellerAuth);
        when(tradeDao.getTradeById(anyInt())).thenReturn(Optional.of(soldTrade));
        doNothing().when(userService).updateRatingBy(anyString(), anyInt());

        tradeService.rateCounterPartUserRegardingTrade(buyerAuth.getUsername(), 8, 0);

        Assert.assertTrue(greaterTrade.isSellerRated());
    }

    @Test
    public void testRateCounterpartAsSeller(){
        Trade soldTrade = greaterTrade;
        soldTrade.setStatus(TradeStatus.SOLD);
        buyer.setUserAuth(buyerAuth);
        seller.setUserAuth(sellerAuth);
        when(tradeDao.getTradeById(anyInt())).thenReturn(Optional.of(soldTrade));
        doNothing().when(userService).updateRatingBy(anyString(), anyInt());

        tradeService.rateCounterPartUserRegardingTrade(sellerAuth.getUsername(), 8, 0);

        Assert.assertTrue(greaterTrade.isBuyerRated());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidRateCounterpart(){
        buyer.setUserAuth(buyerAuth);
        seller.setUserAuth(sellerAuth);

        tradeService.rateCounterPartUserRegardingTrade(sellerAuth.getUsername(), 12, 0);
    }

}
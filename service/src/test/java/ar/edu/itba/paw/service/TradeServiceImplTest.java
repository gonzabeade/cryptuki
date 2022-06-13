package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.NoSuchOfferException;
import ar.edu.itba.paw.exception.NoSuchUserException;
import ar.edu.itba.paw.model.Trade;
import ar.edu.itba.paw.persistence.TradeDao;
import ar.edu.itba.paw.persistence.UserAuthDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TradeServiceImplTest {
//
//    @Mock
//    private  OfferService offerService;
//    @Mock
//    private  TradeDao tradeDao;
//    @Mock
//    private  UserAuthDao userAuthDao;
//    @Mock
//    private  MessageSenderFacade messageSenderFacade;
//
//    @InjectMocks
////    private TradeServiceImpl tradeService = new TradeServiceImpl(offerService,tradeDao,userAuthDao,messageSenderFacade);
//
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

//package ar.edu.itba.paw.service;
//
//import ar.edu.itba.paw.exception.PersistenceException;
//import ar.edu.itba.paw.exception.ServiceDataAccessException;
//import ar.edu.itba.paw.exception.UncategorizedPersistenceException;
//import ar.edu.itba.paw.persistence.OfferDao;
//import ar.edu.itba.paw.persistence.OfferStatusDao;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.dao.DataAccessException;
//
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.when;
//@RunWith(MockitoJUnitRunner.class)
//
//public class OfferServiceImplTest {
//
//    @Mock
//    private OfferDao offerDao;
//
//    @Mock
//    private  MessageSenderFacade messageSenderFacade;
//
//    @Mock
//    private OfferStatusDao offerStatusDao;
//
//    @Mock
//    private TradeServiceImpl tradeServiceImpl;
//
//    @InjectMocks
//    private OfferServiceImpl offerService = new OfferServiceImpl(offerDao,messageSenderFacade,offerStatusDao,tradeServiceImpl);
//
////    @Test(expected = ServiceDataAccessException.class)
////    public void testDecrementWithPause(){
////        String message="Testing exception";
////        DataAccessException dae = new DataAccessException(message) {
////            @Override
////            public String getMessage() {
////                return super.getMessage();
////            }
////        };
////        doThrow(new UncategorizedPersistenceException(dae)).when(offerDao).pauseOffer(5);
////        float unitPrice = 15.5f;
////        int min = 1,max=3;
////        UtilsClass.UserPublic user = (UtilsClass.UserPublic)new UtilsClass.UserPublic("shadad@itba.edu.ar").withId(5);
////        UtilsClass.OfferPublic offer =(UtilsClass.OfferPublic) new UtilsClass.OfferPublic(5,user.build(),null,unitPrice).withMinQuantity(min).withMaxQuantity(max);
////
////        offerService.soldOffer(offer.build(), unitPrice*max);
////    }
//
////    @Test(expected = ServiceDataAccessException.class)
////    public void testDecrementSetMaxQuantity(){
////        String message="Testing exception";
////        DataAccessException dae = new DataAccessException(message) {
////            @Override
////            public String getMessage() {
////                return super.getMessage();
////            }
////        };
////        float unitPrice = 15.5f;
////        int min = 1,max=3;
////        UtilsClass.UserPublic user = (UtilsClass.UserPublic)new UtilsClass.UserPublic("shadad@itba.edu.ar").withId(5);
////        UtilsClass.OfferPublic offer =(UtilsClass.OfferPublic) new UtilsClass.OfferPublic(5,user.build(),null,unitPrice).withMinQuantity(min).withMaxQuantity(max);
////        doThrow(new UncategorizedPersistenceException(dae)).when(offerDao).setMaxQuantity(5,offer.getMaxQuantity() - (unitPrice*min/offer.getunitPrice()));
////
////        offerService.decrementOfferMaxQuantity(offer.build(), unitPrice*min);
////    }
//
//
//
//}

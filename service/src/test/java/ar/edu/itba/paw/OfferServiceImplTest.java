package ar.edu.itba.paw;

import ar.edu.itba.paw.persistence.OfferDao;
import ar.edu.itba.paw.service.OfferServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class OfferServiceImplTest {

    @InjectMocks
    private OfferServiceImpl offerService = new OfferServiceImpl();

    @Mock
    private OfferDao offerDao;

    @Test
    public void testmakeOffer(){

//        Offer offer = new Offer.Builder()
//                .id(1)
//                .seller(2)
//                .date(new Date())
//                .coin("arg")
//                .price(15)
//                .amount(15)
//                .build();
//
//        Mockito.when(offerDao.makeOffer(offer.getSellerId(),offer.getDate(),offer.getCoin_id(),offer.getAskingPrice(),offer.getCoinAmount()))
//                .thenReturn(offer);
//
//        Offer serviceOffer = offerService.makeOffer(offer.getSellerId(),offer.getDate(),offer.getCoin_id(),offer.getAskingPrice(),offer.getCoinAmount());
//
//        Assert.assertNotNull(serviceOffer);
//        Assert.assertEquals(offer,serviceOffer);

    }




}

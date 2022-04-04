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
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class OfferServiceImplTest {

    @InjectMocks
    private OfferServiceImpl offerService = new OfferServiceImpl();

    @Mock
    private OfferDao offerDao;

    @Test
    public void testmakeOffer(){
        Offer offer = new Offer(1,2,new Date(),"arg",15,15);
        Mockito.when(offerDao.makeOffer(offer.getSeller_id(),offer.getOffer_date(),offer.getCoin_id(),offer.getAsking_price(),offer.getCoin_amount()))
                .thenReturn(offer);

        Offer serviceOffer = offerService.makeOffer(offer.getSeller_id(),offer.getOffer_date(),offer.getCoin_id(),offer.getAsking_price(),offer.getCoin_amount());

        Assert.assertNotNull(serviceOffer);
        Assert.assertEquals(offer,serviceOffer);

    }




}

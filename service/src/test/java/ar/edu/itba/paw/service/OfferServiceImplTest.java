package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.PersistenceException;
import ar.edu.itba.paw.exception.ServiceDataAccessException;
import ar.edu.itba.paw.exception.UncategorizedPersistenceException;
import ar.edu.itba.paw.persistence.OfferDao;
import ar.edu.itba.paw.persistence.OfferStatusDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)

public class OfferServiceImplTest {

    @Mock
    private OfferDao offerDao;

    @Mock
    private  MessageSenderFacade messageSenderFacade;

    @Mock
    private TradeServiceImpl tradeServiceImpl;

    @InjectMocks
    private OfferServiceImpl offerService;

    @Test
    public void testGetOfferByInvalidId(){

    }

    @Test
    public void testGetOfferByInvalidId(){

    }

}

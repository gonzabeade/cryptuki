package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Offer;
import ar.edu.itba.paw.model.Trade;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.KycDao;
import ar.edu.itba.paw.persistence.OfferDao;
import ar.edu.itba.paw.persistence.TradeDao;
import ar.edu.itba.paw.persistence.UserDao;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Locale;

@RunWith(MockitoJUnitRunner.class)
public class CustomPreAuthorizerTest {
    @Mock
    private UserDao userDao;
    @Mock
    private OfferDao offerDao;
    @Mock
    private TradeDao tradeDao;
    @Mock
    private KycDao kycDao;

    @InjectMocks
    CustomPreAuthorizer customPreAuthorizer;

    Offer auxOffer = new Offer.Builder(10, 50,100).build();
    User auxUser = new User("auxUser@gmail.com", "12345678", 7, 58, Locale.forLanguageTag("en-US"));
    Trade auxTrade = new Trade(auxOffer, auxUser, 20);


}

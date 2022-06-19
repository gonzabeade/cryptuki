package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Offer;
import ar.edu.itba.paw.model.Trade;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.MessageDao;
import ar.edu.itba.paw.persistence.TradeDao;
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
public class ChatServiceImplTest {

    @Mock
    private MessageDao messageDao;
    @Mock
    private TradeDao tradeDao;

    @InjectMocks
    private ChatServiceImpl chatService;

    Offer auxOffer = new Offer.Builder(10, 50,100).build();
    User auxUser = new User("auxUser@gmail.com", "12345678", 7, 58, Locale.forLanguageTag("en-US"));
    Trade trade = new Trade(auxOffer, auxUser, 20);

    @Test
    public void testSendMessageFromBuyer(){
        int unseenMessages = trade.getqUnseenMessagesSeller();
        Optional<Trade> tradeOptional = Optional.of(trade);
        when(tradeDao.getTradeById(anyInt())).thenReturn(tradeOptional);

        chatService.sendMessage(auxUser.getId(), 0, "testMessage");

        Assert.assertEquals(unseenMessages + 1, trade.getqUnseenMessagesSeller());
    }

    @Test
    public void testSendMessageFromSeller(){
        int unseenMessages = trade.getqUnseenMessagesBuyer();
        Optional<Trade> tradeOptional = Optional.of(trade);
        when(tradeDao.getTradeById(anyInt())).thenReturn(tradeOptional);
        doNothing().when(messageDao).sendMessage(anyInt(), anyInt(), anyString());

        chatService.sendMessage(auxUser.getId()+1, 0, "testMessage");

        Assert.assertEquals(unseenMessages + 1, trade.getqUnseenMessagesBuyer());
    }

    @Test
    public void testMarkBuyerMessagesAsSeen(){
        Optional<Trade> tradeOptional = Optional.of(trade);
        when(tradeDao.getTradeById(anyInt())).thenReturn(tradeOptional);
        when(tradeDao.modifyTrade(trade)).thenReturn(trade);

        chatService.markBuyerMessagesAsSeen(0);

        Assert.assertEquals(0, trade.getqUnseenMessagesBuyer());
    }

    @Test
    public void testMarkSellerMessagesAsSeen(){
        Optional<Trade> tradeOptional = Optional.of(trade);
        when(tradeDao.getTradeById(anyInt())).thenReturn(tradeOptional);
        when(tradeDao.modifyTrade(trade)).thenReturn(trade);

        chatService.markSellerMessagesAsSeen(0);

        Assert.assertEquals(0, trade.getqUnseenMessagesBuyer());
    }
}

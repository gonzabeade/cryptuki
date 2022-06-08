package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.NoSuchTradeException;
import ar.edu.itba.paw.persistence.Message;
import ar.edu.itba.paw.persistence.MessageDao;
import ar.edu.itba.paw.persistence.Trade;
import ar.edu.itba.paw.persistence.TradeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService{

    private final MessageDao messageDao;
    private final TradeDao tradeDao;

    @Autowired
    public MessageServiceImpl(MessageDao messageDao, TradeDao tradeDao) {
        this.messageDao = messageDao;
        this.tradeDao = tradeDao;
    }

    @Override
    @Transactional
    public void sendMessage(Message.Builder builder) {
        Trade trade = tradeDao.getTradeById(builder.getTradeId()).orElseThrow(()->new NoSuchTradeException(builder.getTradeId()));
        messageDao.sendMessage(builder);
        if (builder.getUserId() == trade.getUser().getId())
            tradeDao.setSellerUnseenMessageCount(builder.getTradeId(), trade.getqUnseenMessagesSeller()+1);
        else
            tradeDao.setBuyerUnseenMessageCount(builder.getTradeId(), trade.getqUnseenMessagesBuyer()+1);
    }
}

package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.NoSuchTradeException;
import ar.edu.itba.paw.persistence.MessageDao;
import ar.edu.itba.paw.persistence.Trade;
import ar.edu.itba.paw.persistence.TradeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void sendMessage(Integer senderId, Integer tradeId, String message) {
        Trade trade = tradeDao.getTradeById(tradeId).orElseThrow(()->new NoSuchTradeException(tradeId));
        messageDao.sendMessage(senderId, tradeId, message);
        if (senderId == trade.getUser().getId())
            tradeDao.setSellerUnseenMessageCount(tradeId, trade.getqUnseenMessagesSeller()+1);
        else
            tradeDao.setBuyerUnseenMessageCount(tradeId, trade.getqUnseenMessagesBuyer()+1);
    }
}

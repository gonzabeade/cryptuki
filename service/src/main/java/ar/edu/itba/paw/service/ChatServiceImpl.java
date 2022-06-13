package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.NoSuchTradeException;
import ar.edu.itba.paw.persistence.MessageDao;
import ar.edu.itba.paw.model.Trade;
import ar.edu.itba.paw.persistence.TradeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatServiceImpl implements ChatService {

    private final MessageDao messageDao;
    private final TradeDao tradeDao;

    @Autowired
    public ChatServiceImpl(MessageDao messageDao, TradeDao tradeDao) {
        this.messageDao = messageDao;
        this.tradeDao = tradeDao;
    }

    @Override
    @Transactional
    public void sendMessage(Integer senderId, Integer tradeId, String message) {
        Trade trade = tradeDao.getTradeById(tradeId).orElseThrow(()->new NoSuchTradeException(tradeId));
        messageDao.sendMessage(senderId, tradeId, message);

        // TODO: if time allows, send email notifying about new messages
        if (senderId == trade.getBuyer().getId())
            trade.setqUnseenMessagesSeller(trade.getqUnseenMessagesSeller()+1);
        else
            trade.setqUnseenMessagesBuyer(trade.getqUnseenMessagesBuyer()+1);
    }

    @Override
    @Transactional
    public void markBuyerMessagesAsSeen(int tradeId) {
        Trade trade = tradeDao.getTradeById(tradeId).orElseThrow(()->new NoSuchTradeException(tradeId));
        trade.setqUnseenMessagesBuyer(0);
        tradeDao.modifyTrade(trade);
    }

    @Override
    @Transactional
    public void markSellerMessagesAsSeen(int tradeId) {
        Trade trade = tradeDao.getTradeById(tradeId).orElseThrow(()->new NoSuchTradeException(tradeId));
        trade.setqUnseenMessagesSeller(0);
        tradeDao.modifyTrade(trade);
    }
}

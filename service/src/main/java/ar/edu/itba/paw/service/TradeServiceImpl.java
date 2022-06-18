package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.*;
import ar.edu.itba.paw.model.Trade;
import ar.edu.itba.paw.model.TradeStatus;
import ar.edu.itba.paw.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class TradeServiceImpl implements TradeService {

    private final TradeDao tradeDao;
    private final UserService userService;
    private final MessageSenderFacade messageSenderFacade;
    private final ChatService chatService;

    @Autowired
    public TradeServiceImpl(TradeDao tradeDao, MessageSenderFacade messageSenderFacade, UserService userService, ChatService chatService) {
        this.tradeDao = tradeDao;
        this.messageSenderFacade = messageSenderFacade;
        this.userService = userService;
        this.chatService = chatService;
    }

    @Override
    @Secured("ROLE_USER")
    @Transactional
    public Trade makeTrade(int offerId, int buyerId, double quantity) {
        Trade newTrade =  tradeDao.makeTrade(offerId, buyerId, quantity);
        String firstChat = newTrade.getOffer().getComments();
        if (firstChat != null && !firstChat.equals(""))
            chatService.sendMessage(newTrade.getOffer().getSeller().getId(), newTrade.getTradeId(), firstChat);
        messageSenderFacade.sendNewTradeNotification(newTrade);
        return newTrade;
    }

    @Override
    @Transactional
    public Trade rejectTrade(int tradeId) {
        return tradeDao.changeTradeStatus(tradeId, TradeStatus.REJECTED);
    }

    @Override
    @Transactional
    public Trade sellTrade(int tradeId) {
        return tradeDao.changeTradeStatus(tradeId, TradeStatus.SOLD);
    }

    @Override
    @Transactional
    public Trade acceptTrade(int tradeId) {
        return tradeDao.changeTradeStatus(tradeId, TradeStatus.ACCEPTED);
    }

    @Override
    @Transactional
    public void deleteTrade(int tradeId) {
        tradeDao.deleteTrade(tradeId);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN') or @customPreAuthorizer.isUserPartOfTrade(#tradeId, authentication.principal)")
    public Optional<Trade> getTradeById(int tradeId) {
        return tradeDao.getTradeById(tradeId);
    }

    @Override
    public Collection<Trade> getMostRecentTradesAsSeller(String username, int quantity) {
        return tradeDao.getMostRecentTradesAsSeller(username, quantity);
    }


    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN') or #username == authentication.principal.username")
    public Collection<Trade> getTradesAsSeller(String username, int page, int pageSize, Set<TradeStatus> status, int offerId) {
        return tradeDao.getTradesAsSeller(username, page, pageSize, status, offerId);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN') or #username == authentication.principal.username")
    public long getTradesAsSellerCount(String username, Set<TradeStatus> status, int offerId) {
        return tradeDao.getTradesAsSellerCount(username, status, offerId);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN') or #username == authentication.principal.username")
    public Collection<Trade> getTradesAsBuyer(String username, int page, int pageSize, TradeStatus status) {
        return tradeDao.getTradesAsBuyer(username, page, pageSize, status);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN') or #username == authentication.principal.username")
    public long getTradesAsBuyerCount(String username, TradeStatus status) {
        return tradeDao.getTradesAsBuyerCount(username, status);
    }

    @Override
    @Transactional
    @PreAuthorize("@customPreAuthorizer.isUserPartOfTrade(#tradeId, authentication.principal)")
    public void rateCounterPartUserRegardingTrade(String username, int rating, int tradeId) {

        // TODO: if time allows, send email
        if (rating < 1 || rating > 10)
            throw new IllegalArgumentException("Rating is out of bounds.");

        Trade trade = tradeDao.getTradeById(tradeId).orElseThrow(()->new NoSuchTradeException(tradeId));

        String buyerUsername = trade.getBuyer().getUserAuth().getUsername();

        if (username.equals(buyerUsername)){

            userService.updateRatingBy(trade.getOffer().getSeller().getUserAuth().getUsername(), rating);
            trade.markSellerAsRated();

        } else {

            userService.updateRatingBy(buyerUsername, rating);
            trade.markBuyerAsRated();

        }

    }

}

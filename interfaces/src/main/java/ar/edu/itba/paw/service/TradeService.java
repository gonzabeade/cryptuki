package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Trade;
import ar.edu.itba.paw.model.TradeStatus;

import java.util.Collection;
import java.util.Optional;

public interface TradeService {

    /** Trade creation */
    Trade makeTrade(int offerId, int buyerId, float quantity);

    /** Trade state manipulation */
    Trade rejectTrade(int tradeId);
    Trade sellTrade(int tradeId);
    Trade acceptTrade(int tradeId);

    void deleteTrade(int tradeId);

    /** Simple getters */
    Optional<Trade> getTradeById(int tradeId);

    /** Returns a collection and count of Trades in which the user is the seller */
    Collection<Trade> getTradesAsSeller(String username, int page, int pageSize, TradeStatus status);
    long getTradesAsSellerCount(String username, TradeStatus status);

    /** Returns a collection and count of Trades in which the user is the buyer */
    Collection<Trade> getTradesAsBuyer(String username, int page, int pageSize, TradeStatus status);
    long getTradesAsBuyerCount(String username, TradeStatus status);

    /** Returns the total count of Trades in a certain status for a username */
    long getTotalTradesCount(String username, TradeStatus status);
    long getTradesFromOfferCount(String username, int offerId);

    void rateCounterPartUserRegardingTrade(String username, int rating, int tradeId);

}

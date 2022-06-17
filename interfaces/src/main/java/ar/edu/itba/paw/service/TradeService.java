package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Trade;
import ar.edu.itba.paw.model.TradeStatus;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

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
    Collection<Trade> getMostRecentTradesAsSeller(String username, int quantity);
    Collection<Trade> getTradesAsSeller(String username, int page, int pageSize, Set<TradeStatus> status, int offerId);
    long getTradesAsSellerCount(String username, Set<TradeStatus> status, int offerId);

    /** Returns a collection and count of Trades in which the user is the buyer */
    Collection<Trade> getTradesAsBuyer(String username, int page, int pageSize, TradeStatus status);
    long getTradesAsBuyerCount(String username, TradeStatus status);

    /** Given a user , it rates the counterPart of a trade **/
    void rateCounterPartUserRegardingTrade(String username, int rating, int tradeId);

}

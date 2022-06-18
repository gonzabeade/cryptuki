package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Trade;
import ar.edu.itba.paw.model.TradeStatus;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface TradeDao {

    /** Trade creation */
    Trade makeTrade(int offerId, int buyerId, double quantity);

    /** Trade manipulation */
    Trade changeTradeStatus(int tradeId, TradeStatus status);
    Trade modifyTrade(Trade trade);
    void deleteTrade(int tradeId);

    /** Simple getters */
    Optional<Trade> getTradeById(int tradeId);

    /** Returns a collection and count of Trades in which the user is the seller */
    Collection<Trade> getTradesAsSeller(String username, int page, int pageSize, Set<TradeStatus> status, int offerId);
    long getTradesAsSellerCount(String username, Set<TradeStatus> status, int offerId);
    Collection<Trade> getMostRecentTradesAsSeller(String username, int quantity);

    /** Returns a collection and count of Trades in which the user is the buyer */
    Collection<Trade> getTradesAsBuyer(String username, int page, int pageSize, TradeStatus status);
    long getTradesAsBuyerCount(String username, TradeStatus status);



}

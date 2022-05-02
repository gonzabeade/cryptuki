package ar.edu.itba.paw.persistence;

import java.util.Collection;
import java.util.Optional;

public interface TradeDao {

    void makeTrade(int offerId, String buyerUsername, float quantity, TradeStatus status);

    void makeTrade(Trade.Builder builder);


    void updateStatus(int tradeId, TradeStatus status);

    Optional<Trade> getTradeById(int tradeId);
    Collection<Trade> getSellingTradesByUsername(String username, int page, int pageSize);
    Collection<Trade> getBuyingTradesByUsername(String username, int page, int pageSize);

}

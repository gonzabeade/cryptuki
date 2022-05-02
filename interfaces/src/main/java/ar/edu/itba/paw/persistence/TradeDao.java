package ar.edu.itba.paw.persistence;

import java.util.Collection;
import java.util.Optional;

public interface TradeDao {

    void makeTrade(Trade.Builder trade);
    void updateStatus(int tradeId, TradeStatus status);

    Optional<Trade> getTradeById(int tradeId);
    Collection<Trade> getSellingTradesByUsername(String username, int page, int pageSize);
    Collection<Trade> getBuyingTradesByUsername(String username, int page, int pageSize);

}

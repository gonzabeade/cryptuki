package ar.edu.itba.paw.persistence;

import java.util.Collection;
import java.util.Optional;

public interface TradeDao {

    void makeTrade(int offerId, String buyerUsername, float quantity, TradeStatus status);

    void makeTrade(Trade.Builder builder);


    void updateStatus(int tradeId, TradeStatus status);

    Optional<Trade> getTradeById(int tradeId);
    @Deprecated
    Collection<Trade> getSellingTradesByUsername(String username, int page, int pageSize);
    @Deprecated
    int getSellingTradesByUsernameCount(String username);
    @Deprecated
    Collection<Trade> getBuyingTradesByUsername(String username, int page, int pageSize);
    @Deprecated
    int getBuyingTradesByUsername(String username);


    Collection<Trade> getTradesByUsername(String username, int page, int pageSize);

    int getTradesByUsernameCount(String username);


}

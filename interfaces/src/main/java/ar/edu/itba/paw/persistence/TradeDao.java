package ar.edu.itba.paw.persistence;

import java.util.Collection;
import java.util.Optional;

public interface TradeDao {

    int makeTrade(int offerId, String buyerUsername, float quantity, TradeStatus status);

    int makeTrade(Trade.Builder builder);


    void updateStatus(int tradeId, TradeStatus status);

    Optional<Trade> getTradeById(int tradeId);

    Collection<Trade> getSellingTradesByUsername(String username, int page, int pageSize);
    int getSellingTradesByUsernameCount(String username);

    Collection<Trade> getBuyingTradesByUsername(String username, int page, int pageSize);
    int getBuyingTradesByUsername(String username);

    Collection<Trade> getSellingTradesByUsername(String username, int page, int pageSize,TradeStatus status);
    int getSellingTradesByUsernameCount(String username,TradeStatus status);

    Collection<Trade> getBuyingTradesByUsername(String username, int page, int pageSize,TradeStatus status);
    int getBuyingTradesByUsername(String username,TradeStatus status);


    Collection<Trade> getTradesByUsername(String username, int page, int pageSize);
    int getTradesByUsernameCount(String username);
    void rateSeller(int tradeId);
    void rateBuyer(int tradeId);

    void deleteTrade(int tradeId);

    void setBuyerUnseenMessageCount(int tradeId, int value);
    void setSellerUnseenMessageCount(int tradeId, int value);

}

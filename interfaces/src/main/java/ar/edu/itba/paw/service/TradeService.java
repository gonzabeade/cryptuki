package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Trade;
import ar.edu.itba.paw.persistence.TradeStatus;
import ar.edu.itba.paw.service.digests.BuyDigest;

import java.util.Collection;
import java.util.Optional;

public interface TradeService {

    int makeTrade(Trade.Builder trade);
    void updateStatus(int tradeId, TradeStatus status);

    Optional<Trade> getTradeById(int tradeId);

    Collection<Trade> getSellingTradesByUsername(String username, int page, int pageSize);
    int getSellingTradesByUsernameCount(String username);


    Collection<Trade> getBuyingTradesByUsername(String username, int page, int pageSize);
    int getBuyingTradesByUsernameCount(String username);


    Collection<Trade> getTradesByUsername(String username, int page, int pageSize);
    int getTradesByUsernameCount(String username);

}

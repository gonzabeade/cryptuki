package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Trade;
import ar.edu.itba.paw.persistence.TradeStatus;
import ar.edu.itba.paw.service.digests.BuyDigest;
import ar.edu.itba.paw.service.digests.SellDigest;

import java.util.Collection;
import java.util.Optional;

public interface TradeService {

    void executeTrade(BuyDigest buyDigest);
    void executeTrade(SellDigest sellHelper);



    void makeTrade(Trade.Builder trade);
    void updateStatus(int tradeId, TradeStatus status);

    Optional<Trade> getTradeById(int tradeId);

    @Deprecated
    Collection<Trade> getSellingTradesByUsername(String username, int page, int pageSize);
    @Deprecated
    int getSellingTradesByUsernameCount(String username);

    @Deprecated
    Collection<Trade> getBuyingTradesByUsername(String username, int page, int pageSize);

    @Deprecated
    int getBuyingTradesByUsernameCount(String username);


    Collection<Trade> getTradesByUsername(String username, int page, int pageSize);

    int getTradesByUsernameCount(String username);




}

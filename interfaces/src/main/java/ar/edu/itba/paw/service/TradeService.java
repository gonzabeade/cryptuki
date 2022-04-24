package ar.edu.itba.paw.service;

import ar.edu.itba.paw.service.digests.BuyDigest;
import ar.edu.itba.paw.service.digests.SellDigest;

public interface TradeService {

    void executeTrade(BuyDigest buyDigest);
    void executeTrade(SellDigest sellHelper);


}

package ar.edu.itba.paw.service;

import ar.edu.itba.paw.persistence.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RatingServiceimpl implements RatingService{

    private final TradeService tradeService;
    private final UserService userService;

    @Autowired
    public RatingServiceimpl(TradeService tradeService, UserService userService) {
        this.tradeService = tradeService;
        this.userService = userService;
    }

    @Override
    public void rate(int tradeId, String username, int rating) {

        Optional<Trade> trade = tradeService.getTradeById(tradeId);
        if(!trade.isPresent()){
            throw new IllegalArgumentException("Non-existant trade");
        }
        if(username.equals(trade.get().getBuyerUsername())){
            userService.incrementUserRating(trade.get().getSellerUsername(), rating);
            tradeService.updatedRatedBuyer(tradeId);
            return;
        }
        else if(username.equals(trade.get().getSellerUsername())){
            userService.incrementUserRating(trade.get().getBuyerUsername(), rating);
            tradeService.updateRatedSeller(tradeId);
            return;
        }
        throw new IllegalArgumentException("Wrong username");
    }
}

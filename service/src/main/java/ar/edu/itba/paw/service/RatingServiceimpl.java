package ar.edu.itba.paw.service;

import ar.edu.itba.paw.exception.NoSuchTradeException;
import ar.edu.itba.paw.exception.PersistenceException;
import ar.edu.itba.paw.exception.ServiceDataAccessException;
import ar.edu.itba.paw.model.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("@customPreAuthorizer.isUserPartOfTrade(#tradeId, authentication.principal)")
    public void rate(int tradeId, String username, int rating) {

        if (rating < 1 || rating > 10)
            throw new IllegalArgumentException("Rating is out of bounds.");

        Optional<Trade> trade = tradeService.getTradeById(tradeId);
        if (!trade.isPresent())
            throw new NoSuchTradeException(tradeId);

        try {
            if (username.equals(trade.get().getBuyerUsername())) {
                userService.incrementUserRating(trade.get().getSellerUsername(), rating);
                tradeService.updatedRatedBuyer(tradeId);
            } else if (username.equals(trade.get().getSellerUsername())) {
                userService.incrementUserRating(trade.get().getBuyerUsername(), rating);
                tradeService.updateRatedSeller(tradeId);
            }
        } catch (PersistenceException pe) {
            throw new ServiceDataAccessException(pe);
        }
    }
}

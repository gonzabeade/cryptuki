package ar.edu.itba.paw.service;

public interface RatingService {
    void rate(int tradeId, String username, int rating);
}

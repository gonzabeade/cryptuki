package ar.edu.itba.paw.service;

import org.springframework.stereotype.Service;

@Service
public class JwtService implements TokenService{
    @Override
    public String generateRefreshToken(String username, String passwordHas) {
        return "refreshToken";
    }

    @Override
    public String generateAccessToken(String username, String refreshToken) {
        return "accessToken";
    }

    @Override
    public boolean accessTokenIsValid(String accessToken) {
        return true;
    }

    @Override
    public boolean refreshTokenIsValid(String refreshToken) {
        return true;
    }
}

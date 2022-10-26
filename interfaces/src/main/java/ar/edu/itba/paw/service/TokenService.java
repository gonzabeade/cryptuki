package ar.edu.itba.paw.service;

public interface TokenService {

    //La idea de los generadores es que si existe un token valido devuelvan ese y si no generen uno
    //Al no tener concurrencia el tener varios clientes que usan la misma cuenta no seria un problema ya que se usaran los mismos tokens y listo
    //TODO: pensar si el generador de refresh token deberia generar tambien el access para evitar accesos a db innecesarios
    String generateRefreshToken(String username, String passwordHas);

    String generateAccessToken(String username, String refreshToken);

    boolean accessTokenIsValid(String accessToken);

    boolean refreshTokenIsValid(String refreshToken);
}

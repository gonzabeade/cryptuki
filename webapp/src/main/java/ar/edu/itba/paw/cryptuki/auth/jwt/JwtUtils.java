package ar.edu.itba.paw.cryptuki.auth.jwt;

import ar.edu.itba.paw.model.UserAuth;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@PropertySource("classpath:application.properties")
public class JwtUtils { // Component that implements serializable?

    private static int JWT_TOKEN_VALIDITY = 24 * 60 * 60; // A day
    @Value("${jwt.accessSecret}")
    private static String accessSecret= "PAPAYAREPLACETHIS";

    @Value("${jwt.refreshSecret}")
    private static String refreshSecret= "MAMAYAREPLACETHIS";

    private JwtUtils() {
    }

    public static Claims getAllClaimsFromAccessToken(String token) {
        return Jwts.parser().setSigningKey(accessSecret).parseClaimsJws(token).getBody();
    }

    public static Claims getAllClaimsFromRefreshToken(String token) {
        return Jwts.parser().setSigningKey(refreshSecret).parseClaimsJws(token).getBody();
    }

    public static <T> T getClaimFromAccessToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromAccessToken(token);
        return claimsResolver.apply(claims);
    }

    public static <T> T getClaimFromRefreshToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromRefreshToken(token);
        return claimsResolver.apply(claims);
    }

    public static String getUsernameFromRefreshToken(String token) {
        return getClaimFromRefreshToken(token, Claims::getSubject);
    }

    public static String getUsernameFromAccessToken(String token) {
        return getClaimFromAccessToken(token, Claims::getSubject);
    }

    public static LocalDate getIssuedAtDateFromAccessToken(String token) {
        return LocalDate.from((getClaimFromAccessToken(token, Claims::getIssuedAt).toInstant()));
    }

    public static LocalDate getIssuedAtDateFromRefreshToken(String token) {
        return LocalDate.from((getClaimFromRefreshToken(token, Claims::getIssuedAt).toInstant()));
    }

    public static LocalDate getExpirationDateFromAccessToken(String token) {
        return LocalDate.from((getClaimFromAccessToken(token, Claims::getExpiration).toInstant()));
    }

    public static LocalDate getExpirationDateFromRefreshToken(String token) {
        return LocalDate.from((getClaimFromRefreshToken(token, Claims::getExpiration).toInstant()));
    }

    /////////////////

    private static boolean isAccessTokenExpired(String token) {
        final LocalDate date = getExpirationDateFromAccessToken(token);
        return date.isBefore(LocalDate.now());
    }

    private static boolean isRefreshTokenExpired(String token) {
        final LocalDate date = getExpirationDateFromRefreshToken(token);
        return date.isBefore(LocalDate.now());
    }

    public static String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername(), accessSecret);
    }

    public static String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername(), refreshSecret);
    }

    private static String doGenerateToken(Map<String, Object> claims, String subject, String secret) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() * JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public static boolean doValidateAccessToken(String token, UserDetails userDetails, String refreshSecret) {
        final String username = getUsernameFromAccessToken(token);
        return username.equals(userDetails.getUsername());  // && !isTokenExpired(token); TODO: CHECK!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }

    public static boolean doValidateRefreshToken(String token, UserDetails userDetails, String refreshSecret) {
        final String username = getUsernameFromRefreshToken(token);
        return username.equals(userDetails.getUsername());  // && !isTokenExpired(token); TODO: CHECK!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }

    public static boolean validateRefreshToken(String token, UserDetails userDetails){
        return doValidateRefreshToken(token, userDetails, refreshSecret);
    }

    public static boolean validateAccessToken(String token, UserDetails userDetails){
        return doValidateAccessToken(token, userDetails, accessSecret);
    }
}

package ar.edu.itba.paw.cryptuki.auth.jwt;

import ar.edu.itba.paw.model.UserAuth;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
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

    private static int ACCESS_TOKEN_VALIDITY = 60 * 60; // An hour
    private static int REFRESH_TOKEN_VALIDITY = 24 * 60 * 60; // A day

    @Value("${jwt.accessSecret}")
    private static String accessSecret= "PAPAYAREPLACETHIS";
    @Value("${jwt.refreshSecret}")
    private static String refreshSecret= "MAMAYAREPLACETHIS";

    private static JwtParser accessParser = Jwts.parser().setSigningKey(accessSecret);
    private static JwtParser refreshParser = Jwts.parser().setSigningKey(refreshSecret);

    private JwtUtils() {
    }

    public static Claims getAllClaimsFromAccessToken(String token) {
        if(!accessParser.isSigned(token))
            throw new RuntimeException("Malformed token");
        return Jwts.parser().setSigningKey(accessSecret).parseClaimsJws(token).getBody();
    }

    public static Claims getAllClaimsFromRefreshToken(String token) {
        if(!refreshParser.isSigned(token))
            throw new RuntimeException("Malformed token");
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

    public static boolean isAccessTokenExpired(String token) {
        final LocalDate date = getExpirationDateFromAccessToken(token);
        return date.isBefore(LocalDate.now());
    }

    public static boolean isRefreshTokenExpired(String token) {
        final LocalDate date = getExpirationDateFromRefreshToken(token);
        return date.isBefore(LocalDate.now());
    }

    public static String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername(), accessSecret, ACCESS_TOKEN_VALIDITY);
    }

    public static String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername(), refreshSecret, REFRESH_TOKEN_VALIDITY);
    }

    private static String doGenerateToken(Map<String, Object> claims, String subject, String secret, int validityTimeInSecs) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() * 1000 + validityTimeInSecs))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}

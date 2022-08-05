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
    @Value("${jwt.secret}")
    private static String secret = "PAPAYAREPLACETHIS";

    private JwtUtils() {
    }

    public static Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public static String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public static LocalDate getIssuedAtDateFromToken(String token) {
        return LocalDate.from((getClaimFromToken(token, Claims::getIssuedAt).toInstant()));
    }

    public static LocalDate getExpirationDateFromToken(String token) {
        return LocalDate.from((getClaimFromToken(token, Claims::getExpiration).toInstant()));
    }

    private static boolean isTokenExpired(String token) {
        final LocalDate date = getExpirationDateFromToken(token);
        return date.isBefore(LocalDate.now());
    }

    public static String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private static String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() * JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public static boolean canTokenBeRefreshed(String token) {
        return !isTokenExpired(token);
    }

    public static boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername());  // && !isTokenExpired(token); TODO: CHECK!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }
}

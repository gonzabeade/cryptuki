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
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@PropertySource("classpath:application.properties")
public class JwtUtils { // Component that implements serializable?

    private static int ACCESS_TOKEN_VALIDITY = 60 * 60; // An hour
    private static int REFRESH_TOKEN_VALIDITY = 24 * 60 * 60; // A day

    @Value("${jwt.accessSecret}")
    private static String secret= "PAPAYAREPLACETHIS";

    private static JwtParser parser = Jwts.parser().setSigningKey(secret);

    private JwtUtils() {
    }

    public static boolean isTokenValid(String token){
        return parser.isSigned(token);
    }

    public static Claims getAllClaimsFromToken(String token) {
        return parser.parseClaimsJws(token).getBody();
    }
    public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    public static String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    public static Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }
    public static Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    public static String getTypeFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("type").toString());
    }
    public static boolean isTokenExpired(String token) {
        final Date date = getExpirationDateFromToken(token);
        return date.compareTo(new Date()) < 0;
    }

    public static String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "access");
        return doGenerateToken(claims, userDetails.getUsername(), ACCESS_TOKEN_VALIDITY);
    }

    public static String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh");
        return doGenerateToken(claims, userDetails.getUsername(), REFRESH_TOKEN_VALIDITY);
    }

    private static String doGenerateToken(Map<String, Object> claims, String subject, int validityTimeInSecs) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validityTimeInSecs * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}

package ar.edu.itba.paw.cryptuki.config.auth.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;

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



    private static final JwtParser parser = Jwts.parser().setSigningKey(secret);

    private JwtUtils() {
    }

    public static boolean isTokenValid(String token){
        try {
            parser.parseClaimsJws(token);
        } catch (MalformedJwtException | SignatureException | UnsupportedJwtException | ExpiredJwtException e) {
            return false;
        }
        return true;
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
        try {
            parser.parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            return true;
        }
        return false;
    }

    public static String generateAccessToken(UserDetails userDetails, boolean hasKyc) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "access");
        if(userDetails.getAuthorities().stream().findFirst().isPresent())
            claims.put("role",userDetails.getAuthorities().stream().findFirst().get().getAuthority());
        if(hasKyc) claims.put("kyc",true); else claims.put("kyc",false);
        return doGenerateToken(claims, userDetails.getUsername(), ACCESS_TOKEN_VALIDITY);
    }

    public static String generateRefreshToken(UserDetails userDetails, boolean hasKyc) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh");
        if(userDetails.getAuthorities().stream().findFirst().isPresent())
            claims.put("role",userDetails.getAuthorities().stream().findFirst().get().getAuthority());
        if(hasKyc) claims.put("kyc",true); else claims.put("kyc",false);
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

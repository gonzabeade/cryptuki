package ar.edu.itba.paw.cryptuki.auth;

import ar.edu.itba.paw.cryptuki.auth.jwt.JwtUtils;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.Optional;

//curl -v -d'{"sellerId":24, "minInCrypto":1, "maxInCrypto":2, "location":"CABALLITO", "unitPrice":250, "cryptoCode":"DAI"}' -H'Content-Type: application/json' 'http://localhost:8080/webapp/offers' --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNjU5NzM2MDQ0fQ.S3_VHCLOQtk7A6gxO43hyi6N9F3F0yS8l54oQV6PtEGSN39bkfk1nEDa0wboeLYL07M2-F17zRqy9FxNx9kPeA'
//curl -v -d'{"sellerId":24, "minInCrypto":1, "maxInCrypto":2, "location":"CABALLITO", "unitPrice":250, "cryptoCode":"DAI"}' -H'Content-Type: application/json' 'http://localhost:8080/webapp/api/offers' -H 'Authorization: Bearer gonzabeade'
//curl -v 'http://localhost:8080/webapp/api/offers' -H 'Authorization: Bearer gonzabeade'

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // JSON Web Tokens
    // The authentication is going to be made through the Authorization Header
    // Authorization: Basic base64(username:password)
    // Authorization: Digest base64(username:hash(password))
    // Authorization: Bearer base64(jwt_json)
        // We will use the token in every request from the authorization on.
        // Storage Local or Memory for storing the token. Local Storage of the browser is OK.
        // Refresh token & Another token every request
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        // browse request -> 403 / 401
        // request X + Authorization: Basic base64(username:password)
        // server -> logs the user -> generates JWT tokens -> lets the request pass -> adds headers in the response with the tokens
        // browser -> stores the tokens (refresh and session) in local storage. From now on, browser makes requests
            // Authorization: Bearer JWT (pass all important information for the user to display, like roles or url)
            // Authorization: Bearer RefreshToken

        // Get authorization header and validate
        final String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        //TODO: mirar como se maneja el Digest
        //TODO: mirar que pasa con los tokens generados si las credenciales son invalidas
        if ( header == null ) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        else if ( header.startsWith("Basic ") ) {
            final String[] credentials = header.split(" ")[1].trim().split(":");

            if(credentials.length != 2)
                filterChain.doFilter(httpServletRequest, httpServletResponse);

            String username = credentials[0].trim();
            String password = credentials[1].trim();

            UserDetails userDetails = userDetailsService.loadUserByUsername(username); // TODO what happens when throws exception? AKA No user?

            filterChain.doFilter(httpServletRequest, httpServletResponse);
            httpServletResponse.addHeader("refresh_token", JwtUtils.generateRefreshToken(userDetails)); //TODO: Que pasa con este header si falla el logueo

            return;
        }
        else if ( !header.startsWith("Bearer ") ){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        // Get jwt token
        final String token = header.split(" ")[1].trim();

        //Validate that token was signed by server
        if( !JwtUtils.isTokenValid(token) )
            throw new RuntimeException("Token is invalid"); //TODO: mirar como devolver el error apropiado

        if( JwtUtils.isTokenExpired(token) )
            throw new RuntimeException("Token has expired");

        final String username = JwtUtils.getUsernameFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if( JwtUtils.getTypeFromToken(token).equals("access") ) {
            //Make the login and try to complete the request

        }
        else if( JwtUtils.getTypeFromToken(token).equals("refresh") ) {
            //Make the login, try to complete the request and generate an access token
            httpServletResponse.addHeader("jwt_token", JwtUtils.generateAccessToken(userDetails));
        }

//
//        if ( !JwtUtils.validateToken(token, userDetails)) { // TODO: Le clave un ! acá
//            filterChain.doFilter(httpServletRequest, httpServletResponse);
//            return;
//        }

//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                userDetails,
//                userDetails.getPassword(),
//                userDetails.getAuthorities()
//        );

        // https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/AuthenticationManager.html
        // TODO: AUTHENTICATION MANAGER
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        // TODO: Investigate that does this do
//        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

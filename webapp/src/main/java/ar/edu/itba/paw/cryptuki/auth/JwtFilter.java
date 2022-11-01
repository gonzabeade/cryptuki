package ar.edu.itba.paw.cryptuki.auth;

import ar.edu.itba.paw.cryptuki.auth.jwt.JwtUtils;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.parameterObject.UserPO;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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
import java.rmi.RemoteException;
import java.util.Optional;

//curl -v -d'{"sellerId":24, "minInCrypto":1, "maxInCrypto":2, "location":"CABALLITO", "unitPrice":250, "cryptoCode":"DAI"}' -H'Content-Type: application/json' 'http://localhost:8080/webapp/offers' --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNjU5NzM2MDQ0fQ.S3_VHCLOQtk7A6gxO43hyi6N9F3F0yS8l54oQV6PtEGSN39bkfk1nEDa0wboeLYL07M2-F17zRqy9FxNx9kPeA'
//curl -v -d'{"sellerId":24, "minInCrypto":1, "maxInCrypto":2, "location":"CABALLITO", "unitPrice":250, "cryptoCode":"DAI"}' -H'Content-Type: application/json' 'http://localhost:8080/webapp/api/offers' -H 'Authorization: Bearer gonzabeade'
//curl -v 'http://localhost:8080/webapp/api/offers' -H 'Authorization: Bearer gonzabeade'

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Autowired
    public JwtFilter(UserDetailsService userDetailsService, AuthenticationManager authenticationManager, UserService userService) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
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
        String username, password;
        UserDetails userDetails;

        //TODO: mirar como se maneja el Digest, para mi no tiene sentindo recibir un hash si va por https
        if ( header == null ) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        else if ( header.startsWith("Digest ") ) {
            final String[] credentials = header.split(" ")[1].trim().split(":");

            if(credentials.length != 2)
                filterChain.doFilter(httpServletRequest, httpServletResponse);

            username = credentials[0].trim();
            password = credentials[1].trim();

            //TODO: mirar como tratar de registrar al usuario
            //try {
            //    userService.registerUser(new UserPO(username, password, "scastagnino@itba.edu.ar", "12345678"));
            //}
            //catch (RuntimeException e) {

            //}

            //TODO: mirar que hacer con los permisos, osea el tercer parametro
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    username,
                    password
            );

            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            userDetails = userDetailsService.loadUserByUsername(username); // TODO what happens when throws exception? AKA No user?

            httpServletResponse.addHeader("refresh_token", JwtUtils.generateRefreshToken(userDetails)); //TODO: Que pasa con este header si falla el logueo
            httpServletResponse.addHeader("jwt_token", JwtUtils.generateAccessToken(userDetails));

            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        else if ( header.startsWith("Bearer ") ){

            // Get jwt token
            final String token = header.split(" ")[1].trim();

            //Validate that token was signed by server
            if( !JwtUtils.isTokenValid(token) )
                throw new RuntimeException("Token is invalid"); //TODO: mirar como devolver el error apropiado

            if( JwtUtils.isTokenExpired(token) )
                throw new RuntimeException("Token has expired");

            username = JwtUtils.getUsernameFromToken(token);
            userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            if( JwtUtils.getTypeFromToken(token).equals("refresh") ) {
                //Make the login, try to complete the request and generate an access token
                httpServletResponse.addHeader("jwt_token", JwtUtils.generateAccessToken(userDetails));
            }

            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        throw new RemoteException("Lacking authorization");

        // TODO: Investigate that does this do
        // authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

    }
}

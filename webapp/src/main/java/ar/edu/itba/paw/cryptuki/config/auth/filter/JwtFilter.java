package ar.edu.itba.paw.cryptuki.config.auth.filter;

import ar.edu.itba.paw.cryptuki.config.auth.jwt.JwtUtils;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.Base64;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public JwtFilter(@Qualifier("passwordUserDetailsService") UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        final String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        String username, password;
        UserDetails userDetails;


        // Continue ...
        if (header == null){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }


        if ( header.startsWith("Basic ") ) {
            final String[] credentials;
            final byte[] base64credentials;

            try {
                base64credentials = Base64.getDecoder().decode(header.split(" ")[1]);
                credentials = new String(base64credentials).trim().split(":");
            } catch (Exception e) { // Which Exception is it
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }

            if (credentials.length != 2) {
                return;
            }

            username = credentials[0].trim();
            password = credentials[1].trim();

            try {
                userDetails = userDetailsService.loadUserByUsername(username);
            } catch (UsernameNotFoundException e) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(),
                    password,
                    userDetails.getAuthorities()
            );

            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


            httpServletResponse.setHeader("X-Refresh-Token", JwtUtils.generateRefreshToken(userDetails));
            httpServletResponse.setHeader("X-Access-Token", JwtUtils.generateAccessToken(userDetails));

        } else if (header.startsWith("Bearer ")){

            // Get jwt token
            final String token = header.split(" ")[1].trim();

            //Validate that token was signed by server and that is hasn't expired
            if (!JwtUtils.isTokenValid(token)) {
                httpServletResponse.setHeader("WWW-Authenticate", "Bearer error=\"invalid_token\"");
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }

            username = JwtUtils.getUsernameFromToken(token);
            userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(),
                    null,
                    userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            if( JwtUtils.getTypeFromToken(token).equals("refresh") ) {
                httpServletResponse.setHeader("X-Access-Token", JwtUtils.generateAccessToken(userDetails));
            }

        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

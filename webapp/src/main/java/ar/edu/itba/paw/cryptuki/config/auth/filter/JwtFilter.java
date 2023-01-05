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
            final byte[] base64credentials = Base64.getDecoder().decode(header.split(" ")[1]);
            final String[] credentials = new String(base64credentials).trim().split(":");

            if(credentials.length != 2) {
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST); // TODO: Check -- No se si es correcto esto
                return;
            }

            username = credentials[0].trim();
            password = credentials[1].trim();

            userDetails = userDetailsService.loadUserByUsername(username); //TODO: mirar que pasa con la excepcion que se tira si no existe el user

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    password,
                    userDetails.getAuthorities()
            );

            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);


            httpServletResponse.addHeader("x-refresh-token", JwtUtils.generateRefreshToken(userDetails));
            httpServletResponse.addHeader("x-access-token", JwtUtils.generateAccessToken(userDetails));

        } else if (header.startsWith("Bearer ")){

            // Get jwt token
            final String token = header.split(" ")[1].trim();

            //Validate that token was signed by server and that is hasn't expired
            if (!JwtUtils.isTokenValid(token)) {
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            username = JwtUtils.getUsernameFromToken(token);
            userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            if( JwtUtils.getTypeFromToken(token).equals("refresh") ) {
                httpServletResponse.addHeader("x-access-token", JwtUtils.generateAccessToken(userDetails));
            }

        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        return;

    }
}

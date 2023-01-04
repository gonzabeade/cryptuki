package ar.edu.itba.paw.cryptuki.config.auth.filter;

import org.springframework.beans.factory.annotation.Autowired;
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

public class NonceBasicFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    public NonceBasicFilter(UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("TRAKA-1");

        final String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        String username, password;
        UserDetails userDetails;

        if (header == null || !header.startsWith("Basic ")) {
//            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        final String token = header.split(" ")[1];
        final String[] credentials = token.trim().split(":");

        if(credentials.length != 2) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        username = credentials[0];
        password = credentials[1];

        userDetails = userDetailsService.loadUserByUsername(username); //TODO: mirar que pasa con la excepcion que se tira si no existe el user

        //TODO: mirar que hacer con los permisos, osea el tercer parametro
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                password,
                userDetails.getAuthorities()
        );

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

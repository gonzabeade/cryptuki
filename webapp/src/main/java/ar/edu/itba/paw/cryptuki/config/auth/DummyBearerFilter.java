package ar.edu.itba.paw.cryptuki.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
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

//curl -v -d'{"minInCrypto":1, "maxInCrypto":2, "location":"CABALLITO", "unitPrice":250, "cryptoCode":"DAI"}' -H'Content-Type: application/json' 'http://localhost:8080/webapp/offers' --header 'Authorization: Basic soutjava:soutjava'

// The following class acts as a dummy bearer filter for educational purposes
// The user  identifies itself with their username only
// Use header 'Authorization: Bearer <uname>'
@Component
public class DummyBearerFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    @Autowired
    public DummyBearerFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("TRAKA-2");

        final String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if ( header == null || !header.startsWith("Bearer ")) {
//            filterChain.doFilter(httpServletRequest, httpServletResponse);
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // Get uname token
        final String username = header.split(" ")[1].trim();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);


        // https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/AuthenticationManager.html
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

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

//URLs para curl
//curl -v -d'{"sellerId":24, "minInCrypto":1, "maxInCrypto":2, "location":"CABALLITO", "unitPrice":250, "cryptoCode":"DAI"}' -H'Content-Type: application/json' 'http://localhost:8080/webapp/offers' --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNjU5NzM2MDQ0fQ.S3_VHCLOQtk7A6gxO43hyi6N9F3F0yS8l54oQV6PtEGSN39bkfk1nEDa0wboeLYL07M2-F17zRqy9FxNx9kPeA'
//curl -v -d'{"sellerId":24, "minInCrypto":1, "maxInCrypto":2, "location":"CABALLITO", "unitPrice":250, "cryptoCode":"DAI"}' -H'Content-Type: application/json' 'http://localhost:8080/webapp/api/offers' -H 'Authorization: Bearer gonzabeade'
//curl -v -X PUT -F isBuyer=false -F picture=@Pictures/pic.jpeg 'http://localhost:8080/webapp/api/users/gonzabeadea/picture'
//curl -v 'http://localhost:8080/webapp/api/offers' -H 'Authorization: Bearer gonzabeade'

//Cosas para ver del proyecto
//-Resolver complains sin poner un mensaje o querer banear a un usuario (Se puede pedir que el smg sea obligatorio, lo del ban no se)

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


        if (header == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        } else if ( header.startsWith("Basic ") ) {
            final byte[] base64credentials = Base64.getDecoder().decode(header.split(" ")[1]);
            final String[] credentials = new String(base64credentials).trim().split(":");

            if(credentials.length != 2) {
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST); // TODO: Check -- No se si es correcto esto
                return;
            }

            username = credentials[0].trim();
            password = credentials[1].trim();

            userDetails = userDetailsService.loadUserByUsername(username); //TODO: mirar que pasa con la excepcion que se tira si no existe el user

            //TODO: mirar que hacer con los permisos, osea el tercer parametro
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    password,
                    userDetails.getAuthorities()
            );

            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);


            httpServletResponse.addHeader("x-refresh-token", JwtUtils.generateRefreshToken(userDetails));
            httpServletResponse.addHeader("x-access-token", JwtUtils.generateAccessToken(userDetails));

            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        } else if (header.startsWith("Bearer ")){

            // Get jwt token
            final String token = header.split(" ")[1].trim();

            //TODO: mirar como devolver los mensajes apropiados
            //Validate that token was signed by server and that is hasn't expired
            if( !JwtUtils.isTokenValid(token) ) {
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

            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        // TODO: Investigate that does this do
        // authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

    }
}

package ar.edu.itba.paw.cryptuki.config.auth.autenticationEntryPoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final static String BODY = "{\"message\": \"Unauthorized\"}";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.addHeader("WWW-Authenticate", "Basic realm=\"Endpoints belonging to the API\"\n");
        response.addHeader("WWW-Authenticate", "Basic realm=\"Only for PUT to /users/{username}/password\"\n");
        response.setHeader("WWW-Authenticate", "Bearer realm=\"Endpoints belonging to the API\"\n");
        response.getWriter().write(BODY);
    }
}

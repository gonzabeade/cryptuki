package ar.edu.itba.paw.cryptuki.auth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        UrlPathHelper helper = new UrlPathHelper();
        String contextPath = helper.getContextPath(httpServletRequest);
        httpServletResponse.sendRedirect(contextPath + "/login?error=error");
    }
}

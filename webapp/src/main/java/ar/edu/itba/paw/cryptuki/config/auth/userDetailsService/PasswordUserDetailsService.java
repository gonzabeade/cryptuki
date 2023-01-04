package ar.edu.itba.paw.cryptuki.config.auth.userDetailsService;

import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Component;

@Component("passwordUserDetailsService")
public class PasswordUserDetailsService extends DatabaseTokenUserDetailsService {
    @Override
    public String getTokenFromUser(User user) {
        return user.getUserAuth().getPassword();
    }
}

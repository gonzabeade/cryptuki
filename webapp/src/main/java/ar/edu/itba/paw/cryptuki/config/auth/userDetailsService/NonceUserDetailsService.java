package ar.edu.itba.paw.cryptuki.config.auth.userDetailsService;

import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Component;
@Component("nonceUserDetailsService")
public class NonceUserDetailsService extends DatabaseTokenUserDetailsService {
    @Override
    public String getTokenFromUser(User user) {
        Integer code = user.getUserAuth().getCode(); 
        return code == null ? null : String.valueOf(code);
    }
}

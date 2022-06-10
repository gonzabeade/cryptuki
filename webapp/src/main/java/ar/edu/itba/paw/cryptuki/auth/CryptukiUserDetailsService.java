package ar.edu.itba.paw.cryptuki.auth;

import ar.edu.itba.paw.persistence.UserAuth;
import ar.edu.itba.paw.model.UserStatus;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
public class CryptukiUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    private static class UserDetailsImpl extends org.springframework.security.core.userdetails.User{
        private boolean enabled;
        public UserDetailsImpl(String username, String password, Collection<? extends GrantedAuthority> authorities, boolean enabled) {
            super(username, password, authorities);
            this.enabled = enabled;
        }
        @Override
        public boolean isEnabled(){
            return this.enabled;
        }
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final UserAuth userAuth = userService.getUserByUsername(username).orElseThrow( ()-> new UsernameNotFoundException(""));

        final Collection<? extends GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(userAuth.getRole().name()));// get roles of username
        //        if(userAuth.getUserStatus().equals(UserStatus.UNVERIFIED)){
//            throw new RuntimeException("Username not verified");
//        }
        return new UserDetailsImpl(username, userAuth.getPassword(), authorities, userAuth.getUserStatus().equals(UserStatus.VERIFIED));
    }
}

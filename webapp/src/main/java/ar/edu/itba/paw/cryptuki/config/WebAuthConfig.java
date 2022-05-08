package ar.edu.itba.paw.cryptuki.config;

import ar.edu.itba.paw.cryptuki.auth.CryptukiUserDetailsService;
import ar.edu.itba.paw.cryptuki.auth.CustomAuthenticationSuccessHandler;
import ar.edu.itba.paw.cryptuki.auth.CustomFailureHandler;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.paw.cryptuki.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CryptukiUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new CustomFailureHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement()
                //.invalidSessionUrl("/")
                .and().authorizeRequests()
               // .antMatchers("/", "/buy/**","/support","/login","/register","/verify","/verifyManual","/passwordRecovery").anonymous()
                //.antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/login", "/register", "/verify**").anonymous()
                .antMatchers("/", "/contact","/coins").permitAll()
                .antMatchers("/admin**").hasRole("ADMIN")
                .antMatchers("/**").authenticated()
                .and().formLogin()
                .failureHandler(failureHandler())
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .successHandler(successHandler())
                .loginPage("/login")
                .and().rememberMe()
                .rememberMeParameter("j_rememberme")
                .userDetailsService(userDetailsService)
                .key(FileCopyUtils.copyToString(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("key"))))
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and().exceptionHandling()
//                .accessDeniedPage("/errors")
                .and().csrf().disable();
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/public/css/**", "/public/js/**", "/public/images/**","/public/styles/**", "/favicon.ico", "/errors");
    }
}

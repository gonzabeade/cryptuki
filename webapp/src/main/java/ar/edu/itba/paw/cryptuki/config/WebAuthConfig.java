package ar.edu.itba.paw.cryptuki.config;

import ar.edu.itba.paw.cryptuki.auth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
@ComponentScan("ar.edu.itba.paw.cryptuki.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService; // Lo toque, puede que se rompa

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private DummyBearerFilter dummyBearerFilter;


    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new CustomFailureHandler();
    }


    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().headers().cacheControl().disable()
                .and().authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/api/offers").authenticated()
                    .antMatchers(HttpMethod.PUT, "/api/offers/**").authenticated()
                    .antMatchers(HttpMethod.GET, "/api/users/**/information").authenticated()
                    .antMatchers(HttpMethod.GET, "/api/complains/**").authenticated()
                    .antMatchers(HttpMethod.POST, "/api/complains").authenticated()
                    .antMatchers(HttpMethod.POST, "/api/complains/**/resolution").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/api/offers/**/trades/**").authenticated()
                    .antMatchers(HttpMethod.POST, "/api/offers/**/trades/**").authenticated()
                    .antMatchers(HttpMethod.PUT, "/api/offers/**/trades/**").authenticated()
                    .antMatchers(HttpMethod.GET, "/api/trades").authenticated()
                    .antMatchers(HttpMethod.GET, "/api/trades/**").authenticated()
                .and()
                    .addFilterBefore(dummyBearerFilter, FilterSecurityInterceptor.class) // JwtFilter homework
                .cors()
                    .and()
                .csrf()
                    .disable();
    }

    @Override
    public void configure(final WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers("/public/css/**", "/public/js/**", "/public/images/**","/public/styles/**", "/favicon.ico", "/errors");
    }

//    @Bean
//    public CorsConfiguration corsConfiguration() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedHeader("http://localhost:3000");
//        return corsConfiguration;
//
//
//    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-access-token", "authorization", "x-refresh-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

package ar.edu.itba.paw.cryptuki.config;
import ar.edu.itba.paw.cryptuki.config.auth.filter.DummyBearerFilter;
import ar.edu.itba.paw.cryptuki.config.auth.filter.JwtFilter;
import ar.edu.itba.paw.cryptuki.config.auth.filter.NonceBasicFilter;
import ar.edu.itba.paw.cryptuki.config.auth.handler.CustomAuthenticationSuccessHandler;
import ar.edu.itba.paw.cryptuki.config.auth.userDetailsService.NonceUserDetailsService;
import ar.edu.itba.paw.cryptuki.config.auth.userDetailsService.PasswordUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class WebAuthConfig {

    /** Bean declarations for authentication requirements**/
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

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


    /** WebSecurityConfigurerAdapter implementations that co-exist and are triggered on different endpoints **/
    @Order(0)
    @Configuration
    @ComponentScan
    public static class NonceConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        private NonceUserDetailsService nonceUserDetailsService;

        @Autowired
        private PasswordUserDetailsService passwordUserDetailsService;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and().headers().cacheControl().disable()
                    .and().antMatcher("/api/users/**/password")
                    .csrf().disable()
                    .cors()
                    .and().authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .addFilterBefore(new NonceBasicFilter(passwordUserDetailsService, authenticationManagerBean()), FilterSecurityInterceptor.class)
                    .addFilterBefore(jwtFilter(), NonceBasicFilter.class);
        }

        @Override
        protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
            DaoAuthenticationProvider ap1 = new DaoAuthenticationProvider();
            DaoAuthenticationProvider ap2 = new DaoAuthenticationProvider();

            ap1.setUserDetailsService(nonceUserDetailsService);
            ap2.setUserDetailsService(passwordUserDetailsService);
            ap2.setPasswordEncoder(passwordEncoder);

            authenticationManagerBuilder
                    .authenticationProvider(ap1)
                    .authenticationProvider(ap2);
        }

        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        public JwtFilter jwtFilter() throws Exception {
            return new JwtFilter(passwordUserDetailsService, authenticationManagerBean());
        }
    }

    @Order(1)
    @Configuration
    @ComponentScan
    @EnableGlobalMethodSecurity(
            prePostEnabled = true,
            securedEnabled = true,
            jsr250Enabled = true
    )
    public static class RestConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        @Qualifier("passwordUserDetailsService")
        private UserDetailsService userDetailsService;

        @Autowired
        private DummyBearerFilter dummyBearerFilter;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Override
        public void configure(final WebSecurity webSecurity) {
            webSecurity.ignoring().antMatchers(
                    "/public/css/**",
                    "/public/js/**",
                    "/public/images/**",
                    "/public/styles/**",
                    "/favicon.ico",
                    "/errors"
            );
        }

        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http
                    .antMatcher("/api/**")
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and().headers().cacheControl().disable()
                    .and().authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/api/users/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/offers").authenticated()
                    .antMatchers(HttpMethod.POST, "/api/users**").anonymous()
                    .antMatchers(HttpMethod.PUT, "/api/offers/**").authenticated()
                    .antMatchers(HttpMethod.GET, "/api/offers/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/offers**").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/users/**/secrets").authenticated()
                    .antMatchers(HttpMethod.GET,"/api/complaints").hasRole("ADMIN") /*todo: check get method on complaints is only allowed for admins */
                    .antMatchers(HttpMethod.POST, "/api/complaints").authenticated()
                    .antMatchers(HttpMethod.GET, "/api/complaints/**").authenticated()
                    .antMatchers(HttpMethod.POST, "/api/complaints/**/resolution").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/api/offers/**/trades/**").authenticated()
                    .antMatchers(HttpMethod.POST, "/api/offers/**/trades/**").authenticated()
                    .antMatchers(HttpMethod.PUT, "/api/offers/**/trades/**").authenticated()
                    .antMatchers(HttpMethod.GET, "/api/trades").authenticated()
                    .antMatchers(HttpMethod.GET, "/api/trades/**").authenticated()
                    .and()
                    .addFilterBefore(jwtFilter(), FilterSecurityInterceptor.class) // JwtFilter homework
                    .cors()
                    .and()
                    .csrf().disable();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
            authenticationManagerBuilder
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(passwordEncoder);
        }

        public JwtFilter jwtFilter() throws Exception {
            return new JwtFilter(userDetailsService, authenticationManagerBean());
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
    }


}

package com.appstream.configuration;


import com.appstream.configuration.customerauthclasses.UserAuthenticationManager;
import com.appstream.filters.UserAuthenticationFilter;
import com.appstream.filters.UserAuthorizationFilter;
import com.appstream.service.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


    @Autowired
    private UserAuthenticationManager manager;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }

//    @Bean
//    public SecurityFilterChain authManager(HttpSecurity http) throws Exception{
//
//
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

         UserAuthenticationFilter authenticationFilter = new UserAuthenticationFilter(manager);
         authenticationFilter.setFilterProcessesUrl("/api/security/test");

        http.csrf().disable()
                .authorizeHttpRequests(
                        (request) -> request
                                .antMatchers("/api/security/main/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                                .and()
                                .addFilter(authenticationFilter)
                                .addFilterBefore(new UserAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                )

        ;

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new ApplicationUserService();
    }



}

package com.appstream.configuration;


import com.appstream.configuration.customerauthclasses.UserAuthenticationManager;
import com.appstream.filters.UserAuthenticationFilter;
import com.appstream.filters.UserAuthorizationFilter;
import com.appstream.service.ApplicationUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfiguration {


    @Autowired
    private UserAuthenticationManager manager;

    @Value("${authKey}")
    private String key;

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

         UserAuthenticationFilter authenticationFilter = new UserAuthenticationFilter(manager, key);
         authenticationFilter.setFilterProcessesUrl("/api/login");

        http.csrf().disable()
                .authorizeHttpRequests(
                        (request) -> request
                                .antMatchers("/api/login", "/api/public/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                                .and()
                                .addFilter(authenticationFilter)
                                .addFilterBefore(new UserAuthorizationFilter(key), UsernamePasswordAuthenticationFilter.class)
                )

        ;



        return http.build();
    }




    @Bean
    public UserDetailsService userDetailsService() {
        return new ApplicationUserService();
    }



}

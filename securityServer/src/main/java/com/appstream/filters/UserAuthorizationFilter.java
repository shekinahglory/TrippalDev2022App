package com.appstream.filters;

import ch.qos.logback.core.joran.action.IADataForComplexProperty;
import com.appstream.configuration.ConfigProperties;
import com.appstream.configuration.helper.BeanUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Slf4j
public class UserAuthorizationFilter extends OncePerRequestFilter {

    private String key;

    public UserAuthorizationFilter(String key) {
          this.key = key;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().contains("/api/login") || request.getServletPath().contains("/api/public")){
               filterChain.doFilter(request, response);
        } else {
            log.info("The key is : {}", key);
            String authorizationToken = request.getHeader("AUTHORIZATION");
            if (authorizationToken != null && authorizationToken.startsWith("SecurityBearer ")){
                try{
                    Algorithm algorithm = Algorithm.HMAC512(key.getBytes());
                    String token = authorizationToken.substring("SecurityBearer ".length());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String username = decodedJWT.getSubject();
                    var roles = decodedJWT.getClaim("roles").asArray(String.class);
                    var authorities = new ArrayList<SimpleGrantedAuthority>();
                    Arrays.stream(roles).forEach( role  -> authorities.add(new SimpleGrantedAuthority(role)));

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null , authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    log.info("This is where the request was going all this time ");
                    filterChain.doFilter(request, response);
                } catch (Exception e){
                    log.info("En error has happened {}", e.getMessage());
                    Map<String, String> error = new HashMap<>();
                    error.put("Error message", e.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }

            } else {

                Map<String, String> badLoginInfo = new HashMap<>();
                badLoginInfo.put("WRONG CREDENTIALS", "PLEASE CHECK YOUR LOGIN INFO");
                response.setContentType(APPLICATION_JSON_VALUE);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                new ObjectMapper().writeValue(response.getOutputStream(), badLoginInfo);

            }


        }

    }

}

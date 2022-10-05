package com.appstream.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
public class UserAuthorizationFilter extends OncePerRequestFilter {

    private final String key = "asdfkj123498asdfadfakjsdfkaj";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (request.getServletPath().equals("/api/security/test") ||
                request.getServletPath().contains("/api/security/main")){
            log.info("filter working now");
               filterChain.doFilter(request, response);
        } else {


            Algorithm algorithm = Algorithm.HMAC512(key.getBytes());
            String token = JWT.create()
                    .sign(algorithm)
                    ;
            log.info("This is where the request was going all this time ");
        }


    }
}

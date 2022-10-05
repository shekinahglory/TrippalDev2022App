package com.appstream.configuration.customerauthclasses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


@Component
public class UserAuthenticationManager implements AuthenticationManager {

    @Autowired
    @Lazy
    private UserAuthenticationProvider userAuthenticationProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return userAuthenticationProvider.authenticate(authentication);
    }
}

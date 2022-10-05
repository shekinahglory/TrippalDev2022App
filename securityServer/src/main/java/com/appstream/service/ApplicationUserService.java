package com.appstream.service;


import com.appstream.domain.AppUser;
import com.appstream.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationUserService implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<AppUser> result = appUserRepository.findByEmail(username);


        if (!result.isEmpty()){
            AppUser appUser = result.get();
            var authorities = appUser.getRoles().stream()
                     .map( r -> new SimpleGrantedAuthority(r.getName())).toList();
            return new User(appUser.getEmail(), appUser.getPassword(), authorities);
        } else {
            throw new UsernameNotFoundException("User not found");
        }

    }
}

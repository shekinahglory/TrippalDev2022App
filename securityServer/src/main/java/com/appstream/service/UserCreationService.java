package com.appstream.service;


import com.appstream.domain.AppUser;
import com.appstream.domain.Role;
import com.appstream.repository.AppUserRepository;
import com.appstream.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCreationService {


    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserCreationService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public String createUser(AppUser appUser){
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        var ids = List.of(2);
        var roles = roleRepository.findAllById(ids);
        appUser.setRoles(roles);
        appUserRepository.save(appUser);
        return "User created";
    }

    public String createAdmin(AppUser appUser){
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        var ids = List.of(1,2,3,4);
        var roles = roleRepository.findAllById(ids);
        appUser.setRoles(roles);
        appUserRepository.save(appUser);
        return "Admin User created";

    }

    public String createHelper(AppUser helper){
        helper.setPassword(passwordEncoder.encode(helper.getPassword()));
        var ids = List.of(4);
        var roles = roleRepository.findAllById(ids);
        helper.setRoles(roles);
        appUserRepository.save(helper);
        return "Helper user created";
    }

}

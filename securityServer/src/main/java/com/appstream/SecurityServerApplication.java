package com.appstream;


import com.appstream.domain.Role;
import com.appstream.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Slf4j
public class SecurityServerApplication {

    @Autowired
    private RoleRepository roleRepository;
    public static void main(String[] args) {
        SpringApplication.run(SecurityServerApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner (){

        return args -> {
            List ids = List.of(1,2,3,4);
            List roles = roleRepository.findAllById(ids);

            if (roles.isEmpty()){
                List<Role> roles1 = new ArrayList<>();
                roles1.add(new Role(1, "ADMIN"));
                roles1.add(new Role(2, "USER"));
                roles1.add(new Role(3, "SUPER_USER"));
                roles1.add(new Role(4, "HELPER"));
                roles.add(roles1);
                roleRepository.saveAll(roles1);
            } else {
                log.info("Role already saved");
            }
        };
    }
}
package com.appstream.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/public/test")
@EnableScheduling
@Slf4j
public class MainTestController {



    @GetMapping
    public ResponseEntity<String> testController(){
        return  ResponseEntity.ok("Test controller is working");
    }

    @GetMapping("second")
    public void checkingAuthCredentials(Authentication authentication){


        System.out.println("Principal : " +  authentication);
    }
}

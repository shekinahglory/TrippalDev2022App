package com.appstream.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/security/test")
public class MainTestController {



    @GetMapping
    public ResponseEntity<String> testController(){
        return  ResponseEntity.ok("Test controller is working");
    }
}

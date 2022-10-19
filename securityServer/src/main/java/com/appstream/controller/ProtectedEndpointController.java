package com.appstream.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/protected/main")
public class ProtectedEndpointController {



    @GetMapping
    public ResponseEntity<String> testOne(){

        return ResponseEntity.ok("Reached to the protected end point");
    }
}

package com.appstream.controller;


import com.appstream.domain.AppUser;
import com.appstream.service.UserCreationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/security/main")
public record MainSecurityController(UserCreationService userCreationService) {



    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody AppUser user){

        String result = userCreationService.createUser(user);
        return  ResponseEntity.ok(result);

    }

    @PostMapping("/admin")
    public ResponseEntity<String> createAdmin(@RequestBody AppUser admin){
        String result = userCreationService.createAdmin(admin);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/helper")
    public ResponseEntity<String> createHelper(@RequestBody AppUser helper){
        String result = userCreationService().createHelper(helper);
        return  ResponseEntity.ok(result);
    }

    @GetMapping("/test")
    public ResponseEntity<?> testController(){

        return ResponseEntity.ok("Endpoint is working");
    }



}

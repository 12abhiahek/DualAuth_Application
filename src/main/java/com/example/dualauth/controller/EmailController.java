package com.example.dualauth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/email")
public class EmailController {

//    @GetMapping("/profile")
//    public Map<String, Object> profile(Authentication auth) {
//        return Map.of(
//                "email", auth.getName(),
//                "info", "This endpoint is accessible only to EMAIL tokens"
//        );
//    }



    @GetMapping("/profile")
    public ResponseEntity<?> profile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Unauthorized or invalid token"));
        }
        return ResponseEntity.ok(Map.of("email", auth.getName()));
    }
}

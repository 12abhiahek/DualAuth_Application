package com.example.dualauth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @GetMapping("/profile")
    public Map<String, Object> profile(Authentication auth) {
        return Map.of(
                "email", auth.getName(),
                "info", "This endpoint is accessible only to EMAIL tokens"
        );
    }
}

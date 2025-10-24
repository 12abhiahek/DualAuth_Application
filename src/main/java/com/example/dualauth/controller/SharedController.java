package com.example.dualauth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/shared")
public class SharedController {
    @GetMapping("/info")
    public Map<String, Object> info(Authentication auth) {
        return Map.of(
                "subject", auth.getName(),
                "authorities", auth.getAuthorities(),
                "info", "Accessible to both token types"
        );
    }
}

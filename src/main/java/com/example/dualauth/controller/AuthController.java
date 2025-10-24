package com.example.dualauth.controller;

import com.example.dualauth.dto.*;
import com.example.dualauth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/email/register")
    public ResponseEntity<?> registerEmail(@RequestBody EmailRegisterRequest req) {
        authService.registerEmailUser(req.getEmail(), req.getPassword(), req.getRoles());
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/email/login")
    public ResponseEntity<AuthResponse> loginEmail(@RequestBody EmailLoginRequest req) {
        String token = authService.loginEmailUser(req.getEmail(), req.getPassword());
        return ResponseEntity.ok(new AuthResponse(token, "EMAIL"));
    }

    @PostMapping("/mobile/request-otp")
    public ResponseEntity<?> requestOtp(@RequestBody MobileRequestOtp req) {
        String otp = authService.requestOtp(req.getMobile());
        // For tests/demo, return OTP
        return ResponseEntity.ok().body(Map.of("otp", otp, "otpSent", true));
    }

    @PostMapping("/mobile/verify-otp")
    public ResponseEntity<AuthResponse> verifyOtp(@RequestBody MobileVerifyOtp req) {
        String token = authService.verifyOtp(req.getMobile(), req.getOtp(), req.getName());
        return ResponseEntity.ok(new AuthResponse(token, "MOBILE"));
    }
}

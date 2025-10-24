package com.example.dualauth.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "otp_entry")
@Getter
@Setter
@NoArgsConstructor
public class OtpEntry {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String mobile;

    @Column(nullable = false)
    private String otpHash; // for test you may store plain OTP

    @Column(nullable = false)
    private Instant expiresAt;

    @Column(nullable = false)
    private boolean used = false;
}

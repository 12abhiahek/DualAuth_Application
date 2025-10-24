package com.example.dualauth.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "mobile_user")
@Getter
@Setter
@NoArgsConstructor
public class MobileUser {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @Column(nullable = false, unique = true)
    private String mobile; // E.164 recommended

    private boolean mobileVerified = false;

    private Instant createdAt = Instant.now();
}

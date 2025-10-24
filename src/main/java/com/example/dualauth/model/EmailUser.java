package com.example.dualauth.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "email_user")
@Getter
@Setter
@NoArgsConstructor
public class EmailUser {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column
    private String roles; // comma-separated roles like ROLE_ADMIN,ROLE_STAFF

    @Column
    private boolean enabled = true;

    @Column
    private Instant createdAt = Instant.now();
}

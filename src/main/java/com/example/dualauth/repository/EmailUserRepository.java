package com.example.dualauth.repository;

import com.example.dualauth.model.EmailUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmailUserRepository extends JpaRepository<EmailUser, UUID> {
    Optional<EmailUser> findByEmail(String email);
}

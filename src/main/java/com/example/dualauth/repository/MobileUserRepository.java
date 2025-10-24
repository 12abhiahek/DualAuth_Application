package com.example.dualauth.repository;

import com.example.dualauth.model.MobileUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MobileUserRepository extends JpaRepository<MobileUser, UUID> {
    Optional<MobileUser> findByMobile(String mobile);
}

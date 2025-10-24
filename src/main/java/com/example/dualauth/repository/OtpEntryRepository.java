package com.example.dualauth.repository;

import com.example.dualauth.model.OtpEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OtpEntryRepository extends JpaRepository<OtpEntry, UUID> {
    Optional<OtpEntry> findFirstByMobileAndUsedFalseOrderByExpiresAtDesc(String mobile);
}

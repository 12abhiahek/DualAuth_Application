package com.example.dualauth.service;

import com.example.dualauth.model.EmailUser;
import com.example.dualauth.model.MobileUser;
import com.example.dualauth.model.OtpEntry;
import com.example.dualauth.repository.EmailUserRepository;
import com.example.dualauth.repository.MobileUserRepository;
import com.example.dualauth.repository.OtpEntryRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class AuthService {

    private final EmailUserRepository emailUserRepository;
    private final MobileUserRepository mobileUserRepository;
    private final OtpEntryRepository otpEntryRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProviderService jwtProviderService;

    public AuthService(EmailUserRepository emailUserRepository,
                       MobileUserRepository mobileUserRepository,
                       OtpEntryRepository otpEntryRepository,
                       PasswordEncoder passwordEncoder,
                       JwtProviderService jwtProviderService) {
        this.emailUserRepository = emailUserRepository;
        this.mobileUserRepository = mobileUserRepository;
        this.otpEntryRepository = otpEntryRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProviderService = jwtProviderService;
    }

    // Email register
    public void registerEmailUser(String email, String password, String roles) {
        if (emailUserRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }
        EmailUser u = new EmailUser();
        u.setEmail(email);
        u.setPasswordHash(passwordEncoder.encode(password));
        u.setRoles(roles);
        u.setEnabled(true);
        u.setCreatedAt(Instant.now());
        emailUserRepository.save(u);
    }

    // Email login
    public String loginEmailUser(String email, String password) {
        EmailUser user = emailUserRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        Map<String, Object> extra = new HashMap<>();
        extra.put("roles", user.getRoles());
        extra.put("uid", user.getId().toString());
        return jwtProviderService.generateEmailToken(user.getEmail(), extra);
    }

    // Request OTP (returns OTP for testing)
    public String requestOtp(String mobile) {
        // basic mobile validation
        if (mobile == null || mobile.length() < 7) throw new IllegalArgumentException("Invalid mobile");
        String otp = generate6DigitOtp();
        OtpEntry entry = new OtpEntry();
        entry.setMobile(mobile);
        entry.setOtpHash(otp); // in prod hash this
        entry.setExpiresAt(Instant.now().plusSeconds(300)); // 5 minutes
        entry.setUsed(false);
        otpEntryRepository.save(entry);
        // In production, send via SMS provider. For testing return OTP
        return otp;
    }

    // Verify OTP (returns token)
    public String verifyOtp(String mobile, String otp, String nameIfNew) {
        OtpEntry entry = otpEntryRepository.findFirstByMobileAndUsedFalseOrderByExpiresAtDesc(mobile)
                .orElseThrow(() -> new IllegalArgumentException("OTP not found"));
        if (entry.isUsed() || entry.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalArgumentException("OTP expired or used");
        }
        // in prod, compare hash
        if (!Objects.equals(entry.getOtpHash(), otp)) {
            throw new IllegalArgumentException("Invalid OTP");
        }
        entry.setUsed(true);
        otpEntryRepository.save(entry);

        MobileUser mu = mobileUserRepository.findByMobile(mobile).orElseGet(() -> {
            MobileUser newUser = new MobileUser();
            newUser.setMobile(mobile);
            newUser.setName(nameIfNew);
            newUser.setMobileVerified(true);
            newUser.setCreatedAt(Instant.now());
            return mobileUserRepository.save(newUser);
        });

        // ensure verified flag
        if (!mu.isMobileVerified()) {
            mu.setMobileVerified(true);
            mobileUserRepository.save(mu);
        }

        Map<String, Object> extra = new HashMap<>();
        extra.put("name", mu.getName());
        extra.put("uid", mu.getId().toString());

        return jwtProviderService.generateMobileToken(mu.getMobile(), extra);
    }

    private String generate6DigitOtp() {
        int v = new Random().nextInt(900000) + 100000;
        return String.valueOf(v);
    }

}

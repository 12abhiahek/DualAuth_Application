package com.example.dualauth;

import com.example.dualauth.dto.EmailLoginRequest;
import com.example.dualauth.dto.EmailRegisterRequest;
import com.example.dualauth.dto.MobileRequestOtp;
import com.example.dualauth.dto.MobileVerifyOtp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthFlowIntegrationTest {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void emailRegisterLoginFlow() {
        // register
        EmailRegisterRequest r = new EmailRegisterRequest();
        r.setEmail("test@example.com");
        r.setPassword("P@ssw0rd");
        r.setRoles("ROLE_STAFF");
        ResponseEntity<Void> regResp = rest.postForEntity("/api/auth/email/register", r, Void.class);
        assertThat(regResp.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // login
        EmailLoginRequest login = new EmailLoginRequest();
        login.setEmail("test@example.com");
        login.setPassword("P@ssw0rd");
        ResponseEntity<String> loginResp = rest.postForEntity("/api/auth/email/login", login, String.class);
        assertThat(loginResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(loginResp.getBody()).contains("token");
    }

    @Test
    void mobileOtpFlow() {
        // request OTP
        MobileRequestOtp req = new MobileRequestOtp();
        req.setMobile("+911234567890");
        ResponseEntity<String> otpResp = rest.postForEntity("/api/auth/mobile/request-otp", req, String.class);
        assertThat(otpResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(otpResp.getBody()).contains("otp");

        // parse OTP from response (quick-and-dirty)
        String body = otpResp.getBody();
        String otp = body.replaceAll(".*\"otp\"\s*:\s*\"?(\\d{6})\"?.*", "$1");

        // verify
        MobileVerifyOtp verify = new MobileVerifyOtp();
        verify.setMobile("+911234567890");
        verify.setOtp(otp);
        ResponseEntity<String> verifyResp = rest.postForEntity("/api/auth/mobile/verify-otp", verify, String.class);
        assertThat(verifyResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(verifyResp.getBody()).contains("token");
    }
}

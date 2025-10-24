package com.example.dualauth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MobileVerifyOtp {
    private String mobile;
    private String otp;
    private String name; // optional name
}

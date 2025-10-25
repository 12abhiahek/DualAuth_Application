package com.example.dualauth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRegisterRequest {
    private String email;
    private String password;
    private String roles; // optional  roles
}

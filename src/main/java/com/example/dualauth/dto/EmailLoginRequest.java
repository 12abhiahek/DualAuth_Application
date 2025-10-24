package com.example.dualauth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailLoginRequest {
    private String email;
    private String password;
}

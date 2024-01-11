package com.example.bookinghotel.model.request;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String email;
    private String password;
}
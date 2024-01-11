package com.example.bookinghotel.rest;

import com.example.bookinghotel.model.request.LoginRequest;
import com.example.bookinghotel.model.request.RegisterRequest;
import com.example.bookinghotel.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthResource {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }
    // http://localhost:8080/api/v1/auth/confirm?token=abf57be0-fec1-4025-9dc2-4ada4d562bcd
    @PostMapping("/confirm")
    public ResponseEntity<?> register(@RequestParam String token) {
        return ResponseEntity.ok(authService.confirm(token));
    }
}

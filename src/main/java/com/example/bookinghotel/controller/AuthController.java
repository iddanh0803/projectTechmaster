package com.example.bookinghotel.controller;

import com.example.bookinghotel.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AuthController {
    @Autowired
    private AuthService authService;
    @GetMapping()
    public String login(){
        return "admin/auth/login";
    }
    @GetMapping("/register")
    public String register(){
        return "admin/auth/register";
    }
    @GetMapping("/confirm")
    public String getConfirm(@RequestParam String token, Model model) {
        model.addAttribute("data", authService.confirm(token));
        return "admin/auth/confirm";
    }
}

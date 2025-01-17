package com.example.nbe341team02.admin.controller;

import com.example.nbe341team02.admin.dto.LoginRequest;
import com.example.nbe341team02.admin.dto.TokenResponse;
import com.example.nbe341team02.admin.entity.Admin;
import com.example.nbe341team02.admin.service.AdminService;
import com.example.nbe341team02.config.jwt.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/login")
    public String showLoginPage() {
        return "admin-login";
    }

    @GetMapping("/dashboard")
    public String loadDashboard() {
        return "dashboard";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Admin admin = adminService.login(loginRequest.getAdmin_username(), loginRequest.getAdmin_password());
        String token = jwtTokenProvider.createToken(admin.getAdminUsername(), admin.getAdminRole());
        
        return ResponseEntity.ok(new TokenResponse(token));
    }
}

package com.example.nbe341team02.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nbe341team02.admin.dto.LoginRequest;
import com.example.nbe341team02.admin.dto.TokenResponse;
import com.example.nbe341team02.admin.entity.Admin;
import com.example.nbe341team02.admin.service.AdminService;
import com.example.nbe341team02.config.jwt.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
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
    @ResponseBody
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Admin admin = adminService.login(loginRequest.getAdmin_username(), loginRequest.getAdmin_password());
        String token = jwtTokenProvider.createToken(admin.getAdminUsername(), admin.getAdminRole());

        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        if (token != null) {
            // 블랙리스트에 토큰 추가
            jwtTokenProvider.invalidateToken(token);
            return ResponseEntity.ok("로그아웃 되었습니다.");
        }
        return ResponseEntity.badRequest().body("유효하지 않은 토큰입니다.");
    }
}

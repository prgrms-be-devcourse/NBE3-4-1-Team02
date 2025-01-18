package com.example.nbe341team02.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.nbe341team02.admin.dto.LoginRequest;
import com.example.nbe341team02.admin.dto.TokenResponse;
import com.example.nbe341team02.admin.entity.Admin;
import com.example.nbe341team02.admin.service.AdminService;
import com.example.nbe341team02.config.jwt.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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
    public String loadDashboard(@RequestParam(required = false) String token, HttpServletRequest request) {
        if (token != null) {
            if (jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                if (auth != null && auth.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                    return "dashboard";
                }
            }
        } else {
            String headerToken = jwtTokenProvider.resolveToken(request);
            if (headerToken != null && jwtTokenProvider.validateToken(headerToken)) {
                Authentication auth = jwtTokenProvider.getAuthentication(headerToken);
                if (auth != null && auth.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                    return "dashboard";
                }
            }
        }
        return "redirect:/admin/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<TokenResponse> processLogin(@RequestBody @Valid LoginRequest loginRequest) {
        Admin admin = adminService.login(loginRequest.getAdmin_username(), loginRequest.getAdmin_password()); // 사용자 인증
        String token = jwtTokenProvider.createToken(admin.getAdminUsername(), admin.getAdminRole()); // 토큰 생성
        return ResponseEntity.ok(new TokenResponse(token)); // 토큰 반환return ResponseEntity.ok(new TokenResponse(token)); // 토큰 반환
    }

    @PostMapping("/logout")
    @ResponseBody
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        if (token != null) {
            jwtTokenProvider.invalidateToken(token);
            return ResponseEntity.ok("로그아웃 되었습니다.");
        }
        return ResponseEntity.badRequest().body("유효하지 않은 토큰입니다.");
    }
}

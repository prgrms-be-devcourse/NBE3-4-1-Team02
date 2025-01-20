package com.example.nbe341team02.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping("/dashboard")
    public String loadDashboard(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request); // 헤더에서 토큰을 가져오도록 변경
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            if (auth != null && auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                return "dashboard";
            }
        }
        return "redirect:/admin/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<TokenResponse> processLogin(@RequestBody @Valid LoginRequest loginRequest) {
        Admin admin = adminService.login(loginRequest.getAdmin_username(), loginRequest.getAdmin_password()); // 사용자 인증
        String token = jwtTokenProvider.createToken(admin.getAdminUsername(), admin.getAdminRole()); // 토큰 생성
        return ResponseEntity.ok(new TokenResponse(token)); // 토큰 반환
    }

    @PostMapping("/logout")
    @ResponseBody
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        if (token != null) {
            jwtTokenProvider.invalidateToken(token);
        }
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}

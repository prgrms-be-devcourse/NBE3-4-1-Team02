package com.example.nbe341team02.admin.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.nbe341team02.admin.entity.Admin;
import com.example.nbe341team02.admin.repository.AdminRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByAdminUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        log.debug("Loading user: {}, role: {}", admin.getAdminUsername(), admin.getAdminRole());
        
        return User.builder()
                .username(admin.getAdminUsername())
                .password(admin.getAdminPassword())
                .authorities(admin.getAdminRole())
                .build();
    }

    public Admin login(String username, String password) {
        log.info("Login attempt - username: {}", username);
        Admin admin = adminRepository.findByAdminUsername(username)
                .orElseThrow(() -> {
                    log.error("User not found: {}", username);
                    return new IllegalArgumentException("가입되지 않은 사용자입니다.");
                });

        if (!passwordEncoder.matches(password, admin.getAdminPassword())) {
            log.error("Invalid password for user: {}", username);
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        
        log.info("Login successful for user: {}", username);
        return admin;
    }

    @PostConstruct
    public void init() {
        try {
            // 기본 관리자 계정이 없는 경우에만 생성
            if (adminRepository.findByAdminUsername("admin").isEmpty()) {
                Admin admin = new Admin();
                admin.setAdminUsername("admin");
                admin.setAdminPassword(passwordEncoder.encode("admin123"));
                admin.setAdminRole("ROLE_ADMIN");
                adminRepository.save(admin);
                log.info("Default admin account created");
            }

            if (adminRepository.findByAdminUsername("user").isEmpty()) {
                Admin admin = new Admin();
                admin.setAdminUsername("user");
                admin.setAdminPassword(passwordEncoder.encode("user123"));
                admin.setAdminRole("ROLE_USER");
                adminRepository.save(admin);
                log.info("Default user account created");
            }
        } catch (Exception e) {
            log.error("Failed to create default admin account", e);
        }
    }
}

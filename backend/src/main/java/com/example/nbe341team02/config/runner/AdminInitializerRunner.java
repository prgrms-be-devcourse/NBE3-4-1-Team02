package com.example.nbe341team02.config.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.nbe341team02.admin.entity.Admin;
import com.example.nbe341team02.admin.repository.AdminRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminInitializerRunner implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String defaultUsername = "admin";
        String defaultPassword = "admin123";
        
        if (!adminRepository.existsByAdminUsername(defaultUsername)) {
            Admin admin = new Admin();
            admin.setAdminUsername(defaultUsername);
            admin.setAdminPassword(passwordEncoder.encode(defaultPassword));
            admin.setAdminRole("ROLE_ADMIN");

            adminRepository.save(admin);
            log.info("기본 관리자 계정이 생성되었습니다. username: {}", defaultUsername);
        } else {
            log.info("관리자 계정이 이미 존재합니다.");
        }
    }
}

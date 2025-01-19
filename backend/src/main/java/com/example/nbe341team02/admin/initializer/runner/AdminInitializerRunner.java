package com.example.nbe341team02.admin.initializer.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.nbe341team02.admin.initializer.entity.AdminInitializer;
import com.example.nbe341team02.admin.initializer.repository.AdminInitializerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminInitializerRunner implements CommandLineRunner {

    private final AdminInitializerRepository adminInitializerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        String defaultUsername = "admin";
        String defaultPassword = "admin123"; // 기본 비밀번호 설정
        
        if (!adminInitializerRepository.existsByAdminUsername(defaultUsername)) {
            AdminInitializer admin = AdminInitializer.builder()
                    .adminUsername(defaultUsername)
                    .adminPassword(passwordEncoder.encode(defaultPassword))
                    .build();
                    
            adminInitializerRepository.save(admin);
            log.info("기본 관리자 계정이 생성되었습니다. username: {}", defaultUsername);
        } else {
            log.info("관리자 계정이 이미 존재합니다.");
        }
    }
}

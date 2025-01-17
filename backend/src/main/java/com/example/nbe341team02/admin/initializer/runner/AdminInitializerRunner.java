package com.example.nbe341team02.admin.initializer.runner;

import com.example.nbe341team02.admin.initializer.entity.AdminInitializer;
import com.example.nbe341team02.admin.initializer.entity.AdminInitializerRole;
import com.example.nbe341team02.admin.initializer.repository.AdminInitializerRepository;
import com.example.nbe341team02.admin.initializer.repository.AdminInitializerRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminInitializerRunner implements CommandLineRunner {

    private final AdminInitializerRepository adminInitializerRepository;
    private final AdminInitializerRoleRepository adminInitializerRoleRepository;

    @Override
    public void run(String... args) {
        String defaultUsername = "admin";
        
        if (!adminInitializerRepository.existsByAdminUsername(defaultUsername)) {
            // AdminInitializerRole 생성 또는 조회
            AdminInitializerRole adminRole = adminInitializerRoleRepository
                .findByRoleType(AdminInitializerRole.RoleType.ROLE_ADMIN)
                .orElseGet(() -> {
                    AdminInitializerRole role = AdminInitializerRole.builder()
                            .roleType(AdminInitializerRole.RoleType.ROLE_ADMIN)
                            .build();
                    return adminInitializerRoleRepository.save(role);
                });

            // Admin 계정 생성
            AdminInitializer admin = AdminInitializer.builder()
                    .adminUsername(defaultUsername)
                    .adminRole(adminRole)
                    .build();
                    
            adminInitializerRepository.save(admin);
            log.info("기본 관리자 계정이 생성되었습니다. username: {}", defaultUsername);
        } else {
            log.info("관리자 계정이 이미 존재합니다.");
        }
    }
}

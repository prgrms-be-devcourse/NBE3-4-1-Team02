package com.example.nbe341team02.admin.initializer.runner;

import com.example.nbe341team02.admin.initializer.entity.AdminInitializer;
import com.example.nbe341team02.admin.initializer.entity.AdminInitializerRole;
import com.example.nbe341team02.admin.initializer.repository.AdminInitializerRepository;
import com.example.nbe341team02.admin.initializer.repository.AdminInitializerRoleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AdminInitializerRunnerTest {

    private static final String DEFAULT_USERNAME = "admin";
    private static final AdminInitializerRole.RoleType DEFAULT_ROLE_TYPE = AdminInitializerRole.RoleType.ROLE_ADMIN;

    @Autowired
    private AdminInitializerRunner adminInitializerRunner;
    @Autowired
    private AdminInitializerRepository adminInitializerRepository;
    @Autowired
    private AdminInitializerRoleRepository adminInitializerRoleRepository;

    @Test
    @DisplayName("관리자 계정이 없을 때 새로운 관리자 계정이 생성된다")
    void createNewAdminWhenNotExists() throws Exception {
        adminInitializerRunner.run();

        AdminInitializer admin = adminInitializerRepository.findByAdminUsername(DEFAULT_USERNAME)
             .orElseThrow(() -> new IllegalStateException("관리자 계정이 생성되지 않았습니다."));

        assertThat(admin.getAdminUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(admin.getAdminRole().getRoleType()).isEqualTo(DEFAULT_ROLE_TYPE);
    }

    @Test
    @DisplayName("관리자 계정이 이미 존재할 때 새로운 계정이 생성되지 않는다")
    void doNotCreateAdminWhenExists() throws Exception {
        // given: 초기 관리자 생성
        adminInitializerRunner.run();
        long initialCount = adminInitializerRepository.count();

        // when: 다시 초기화 실행
        adminInitializerRunner.run();

        // then: 계정 수가 변하지 않았는지 확인
        assertThat(adminInitializerRepository.count()).isEqualTo(initialCount);
    }
} 
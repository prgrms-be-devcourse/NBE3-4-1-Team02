package com.example.nbe341team02.admin.setup.repository;

import com.example.nbe341team02.admin.setup.entity.AdminSetup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminSetupRepository extends JpaRepository<AdminSetup, Long> {
    Optional<AdminSetup> findByAdminUsername(String adminUsername);
    boolean existsByAdminUsername(String adminUsername);
}
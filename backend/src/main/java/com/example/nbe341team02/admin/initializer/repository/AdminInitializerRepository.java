package com.example.nbe341team02.admin.initializer.repository;

import com.example.nbe341team02.admin.initializer.entity.AdminInitializer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminInitializerRepository extends JpaRepository<AdminInitializer, Long> {
    Optional<AdminInitializer> findByAdminUsername(String admin_username);
    boolean existsByAdminUsername(String admin_username);
}

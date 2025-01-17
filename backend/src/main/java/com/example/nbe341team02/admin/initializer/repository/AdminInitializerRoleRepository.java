package com.example.nbe341team02.admin.initializer.repository;

import com.example.nbe341team02.admin.initializer.entity.AdminInitializerRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminInitializerRoleRepository extends JpaRepository<AdminInitializerRole, Long> {
    Optional<AdminInitializerRole> findByRoleType(AdminInitializerRole.RoleType role_type);
}

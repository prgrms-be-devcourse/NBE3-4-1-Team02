package com.example.nbe341team02.admin.initializer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nbe341team02.admin.initializer.entity.AdminInitializerRole;

public interface AdminInitializerRoleRepository extends JpaRepository<AdminInitializerRole, Long> {
    Optional<AdminInitializerRole> findByRoleType(AdminInitializerRole.RoleType roleType);
}

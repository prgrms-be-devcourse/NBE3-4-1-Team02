package com.example.nbe341team02.admin.initializer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminInitializerRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long admin_role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleType role_type;

    public static enum RoleType {
        ROLE_ADMIN,
        ROLE_USER
    }

    @Builder
    private AdminInitializerRole(RoleType role_type) {
        this.role_type = role_type;
    }
}

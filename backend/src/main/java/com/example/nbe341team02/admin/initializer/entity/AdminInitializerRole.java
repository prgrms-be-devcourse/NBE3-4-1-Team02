package com.example.nbe341team02.admin.initializer.entity;

import jakarta.persistence.*;
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

    public enum RoleType {
        ROLE_ADMIN,
        ROLE_USER
    }

    @Builder
    private AdminInitializerRole(RoleType role_type) {
        this.role_type = role_type;
    }
}

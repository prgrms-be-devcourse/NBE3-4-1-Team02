package com.example.nbe341team02.admin.initializer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "admin")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminInitializer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long admin_id;

    @Column(length = 20, nullable = false, unique = true)
    private String admin_username;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private AdminInitializerRole admin_role;

    @Builder
    private AdminInitializer(String admin_username, AdminInitializerRole admin_role) {
        this.admin_username = admin_username;
        this.admin_role = admin_role;
    }
}
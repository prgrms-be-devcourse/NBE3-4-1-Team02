package com.example.nbe341team02.admin.initializer.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity(name = "admin")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminInitializer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long admin_id;

    @Column(length = 20, nullable = false, unique = true)
    private String admin_username;

    @Column(nullable = false)
    private String admin_password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private AdminInitializerRole admin_role;

    @Builder
    private AdminInitializer(String admin_username, String admin_password, AdminInitializerRole admin_role) {
        this.admin_username = admin_username;
        this.admin_password = admin_password;
        this.admin_role = admin_role;
    }
}
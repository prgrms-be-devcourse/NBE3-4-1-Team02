package com.example.nbe341team02.setup.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "admin")
public class AdminSetup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long admin_id;

    @Column(length = 20, nullable = false, unique = true)
    private String admin_username;

    @Column(nullable = false)
    private String admin_password;

    @Builder
    private AdminSetup(String admin_username, String admin_password) {
        this.admin_username = admin_username;
        this.admin_password = admin_password;
    }
}
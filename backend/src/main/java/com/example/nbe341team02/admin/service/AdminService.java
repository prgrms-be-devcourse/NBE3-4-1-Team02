package com.example.nbe341team02.admin.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.nbe341team02.admin.entity.Admin;
import com.example.nbe341team02.admin.repository.AdminRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByAdminUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return User.builder()
                .username(admin.getAdminUsername())
                .password(admin.getAdminPassword())
                .roles(admin.getAdminRole().replace("ROLE_", ""))
                .build();
    }

    public Admin login(String username, String password) {
        Admin admin = adminRepository.findByAdminUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 사용자입니다."));

        if (!passwordEncoder.matches(password, admin.getAdminPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return admin;
    }
}

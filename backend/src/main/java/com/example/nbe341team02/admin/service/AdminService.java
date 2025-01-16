package com.example.nbe341team02.admin.service;

import com.example.nbe341team02.admin.entity.Admin;
import com.example.nbe341team02.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByAdminUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return User.builder()
                .username(admin.getAdminUsername())
                .password(admin.getAdminPassword())
                .roles(admin.getAdminRole())
                .build();
    }
}

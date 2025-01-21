package com.example.nbe341team02.admin.service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nbe341team02.admin.entity.Admin;
import com.example.nbe341team02.admin.repository.AdminRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AdminService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByAdminUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        log.debug("사용자 로딩 중: {}, 권한: {}", admin.getAdminUsername(), admin.getAdminRole());
        
        return User.builder()
                .username(admin.getAdminUsername())
                .password(admin.getAdminPassword())
                .authorities(admin.getAdminRole())
                .build();
    }

    public Admin login(String username, String password) {
        Admin admin = adminRepository.findByAdminUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
            
        if (!passwordEncoder.matches(password, admin.getAdminPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
        
        // 관리자 권한 체크 추가
        if (!"ROLE_ADMIN".equals(admin.getAdminRole())) {
            throw new AccessDeniedException("관리자 권한이 없습니다.");
        }
        
        return admin;
    }
}

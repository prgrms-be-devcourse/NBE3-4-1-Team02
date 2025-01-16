package com.example.nbe341team02.admin.service;

import com.example.nbe341team02.admin.entity.Admin;
import com.example.nbe341team02.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;


    public boolean authenticate(String username, String password) {
        Admin admin = adminRepository.findByAdminUsername(username).orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));
        
        return admin != null && admin.getAdminPassword().equals(password);
    }
}

package com.example.nbe341team02.admin.service;

import org.springframework.stereotype.Service;

import com.example.nbe341team02.admin.entity.Admin;
import com.example.nbe341team02.admin.repository.AdminRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;


    public boolean authenticate(String username, String password) {
        Admin admin = adminRepository.findByUsername(username);
        System.out.println("Found admin: " + admin);
        System.out.println("Input username: " + username);
        System.out.println("Input password: " + password);
        
        return admin != null && admin.getPassword().equals(password);
    }
}

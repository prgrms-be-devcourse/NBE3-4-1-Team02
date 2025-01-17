package com.example.nbe341team02.admin.setup.service;

import com.example.nbe341team02.admin.setup.dto.request.AdminSetupRequest;
import com.example.nbe341team02.admin.setup.dto.response.AdminSetupResponse;
import com.example.nbe341team02.admin.setup.entity.AdminSetup;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminSetupService {

    private final AdminSetupRepository adminSetupRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AdminSetupResponse setupAdmin(AdminSetupRequest request) {
        AdminSetup admin = AdminSetup.builder()
                                   .admin_username(request.getAdmin_username())
                                   .admin_password(passwordEncoder.encode(request.getAdmin_password()))
                                   .build();

        AdminSetup savedAdmin = adminSetupRepository.save(admin);
        return AdminSetupResponse.from(savedAdmin);
    }
}

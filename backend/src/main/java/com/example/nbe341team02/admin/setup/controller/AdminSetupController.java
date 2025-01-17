package com.example.nbe341team02.admin.setup.controller;

import com.example.nbe341team02.admin.setup.dto.request.AdminSetupRequest;
import com.example.nbe341team02.admin.setup.dto.response.AdminSetupResponse;
import com.example.nbe341team02.admin.setup.service.AdminSetupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminSetupController {

    private final AdminSetupService adminSetupService;

    @PostMapping("/setup")
    public ResponseEntity<AdminSetupResponse> setupAdmin(
            @Valid @RequestBody AdminSetupRequest request) {
        AdminSetupResponse response = adminSetupService.setupAdmin(request);
        return ResponseEntity.ok(response);
    }
}


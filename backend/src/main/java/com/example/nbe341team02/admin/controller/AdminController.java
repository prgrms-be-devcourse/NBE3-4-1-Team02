package com.example.nbe341team02.admin.controller;

import com.example.nbe341team02.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "admin-login";
    }

    @GetMapping("/dashboard")
    public String loadDashboard() {
        return "dashboard";
    }
}

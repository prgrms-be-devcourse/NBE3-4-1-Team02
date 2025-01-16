package com.example.nbe341team02.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nbe341team02.admin.service.AdminService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "admin-login";
    }

    @PostMapping("/login")
    public String login(@RequestParam(name = "username") String username,
                        @RequestParam(name = "password") String password,
                        HttpServletRequest request) {
        if (adminService.authenticate(username, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            return "redirect:/admin/dashboard";
        } else {
            return "redirect:/admin/login?error=true";
        }
    }

    @GetMapping("/dashboard")
    public String loadDashboard() {
        return "dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/admin/login";
    }
}

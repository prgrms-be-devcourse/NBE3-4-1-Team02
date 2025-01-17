package com.example.nbe341team02.admin.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class LoginRequest {
    
    @NotNull(message = "사용자 이름은 필수입니다.")
    @Size(min = 3, max = 20, message = "사용자 이름은 3자 이상 20자 이하이어야 합니다.")
    private String admin_username;

    @NotNull(message = "비밀번호는 필수입니다.")
    @Size(min = 6, message = "비밀번호는 6자 이상이어야 합니다.")
    private String admin_password;
} 
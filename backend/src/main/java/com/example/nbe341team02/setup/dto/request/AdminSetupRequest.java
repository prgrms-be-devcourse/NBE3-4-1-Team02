package com.example.nbe341team02.setup.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class AdminSetupRequest {

    @NotBlank(message = "사용자 이름은 필수입니다")
    @Size(min = 4, max = 20, message = "사용자 이름은 4-20자 사이여야 합니다")
    private String admin_username;

    @NotBlank(message = "비밀번호는 필수입니다")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "비밀번호는 최소 8자, 영문자와 숫자를 포함해야 합니다")
    private String admin_password;
}


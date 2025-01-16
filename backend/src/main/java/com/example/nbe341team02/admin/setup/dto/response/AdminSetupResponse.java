package com.example.nbe341team02.admin.setup.dto.response;

import com.example.nbe341team02.admin.setup.entity.AdminSetup;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminSetupResponse {
    private Long admin_id;
    private String admin_username;

    public static AdminSetupResponse from(AdminSetup admin) {
        return AdminSetupResponse.builder()
                       .admin_id(admin.getAdmin_id())
                       .admin_username(admin.getAdmin_username())
                       .build();
    }
}

package com.example.nbe341team02.delivery;

import com.example.nbe341team02.domain.delivery.controller.DeliveryController;
import com.example.nbe341team02.domain.delivery.scheduler.DeliveryScheduler;
import com.example.nbe341team02.global.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeliveryController.class)
@Import(SecurityConfig.class)
public class DeliveryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DeliveryScheduler deliveryScheduler;

    @Test
    void contextLoads() throws Exception {
        mockMvc.perform(post("/admin/policies/delivery-time")
                        .content(""))
                .andExpect(status().isBadRequest());
    }
}

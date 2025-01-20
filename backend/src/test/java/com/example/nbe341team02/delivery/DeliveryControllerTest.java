package com.example.nbe341team02.delivery;

import com.example.nbe341team02.domain.delivery.controller.DeliveryController;
import com.example.nbe341team02.domain.delivery.dto.DeliveryTimePolicyRegisterDto;
import com.example.nbe341team02.domain.delivery.scheduler.DeliveryScheduler;
import com.example.nbe341team02.global.config.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeliveryController.class)
@Import(SecurityConfig.class)
public class DeliveryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DeliveryScheduler deliveryScheduler;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(roles = "ADMIN")
    void contextLoads() throws Exception {
        DeliveryTimePolicyRegisterDto registerDto = DeliveryTimePolicyRegisterDto
                .builder()
                .hour(15)
                .build();
        String json = objectMapper.writeValueAsString(registerDto);

        mockMvc.perform(post("/admin/policies/delivery-time")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(deliveryScheduler, times(1))
                .setDeliveryTime(any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void contextLoads2() throws Exception {
        String json = objectMapper.writeValueAsString(null);

        mockMvc.perform(post("/admin/policies/delivery-time")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(deliveryScheduler, times(0))
                .setDeliveryTime(any());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void contextLoads3() throws Exception {
        DeliveryTimePolicyRegisterDto registerDto = DeliveryTimePolicyRegisterDto
                .builder()
                .hour(25)
                .build();
        String json = objectMapper.writeValueAsString(registerDto);

        mockMvc.perform(post("/admin/policies/delivery-time")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(deliveryScheduler, times(0))
                .setDeliveryTime(any());
    }
}

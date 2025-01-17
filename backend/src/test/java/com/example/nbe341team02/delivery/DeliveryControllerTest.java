package com.example.nbe341team02.delivery;

import com.example.nbe341team02.domain.delivery.controller.DeliveryController;
import com.example.nbe341team02.domain.delivery.scheduler.DeliveryScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DeliveryController.class)
public class DeliveryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DeliveryScheduler deliveryScheduler;

    @Test
    void contextLoads() {}
}

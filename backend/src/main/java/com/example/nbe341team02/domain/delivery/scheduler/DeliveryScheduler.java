package com.example.nbe341team02.domain.delivery.scheduler;

import com.example.nbe341team02.domain.delivery.dto.DeliveryTimePolicyRegisterDto;
import com.example.nbe341team02.domain.delivery.service.DeliveryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Objects;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryScheduler implements CommandLineRunner {
    private final DeliveryService deliveryService;

    private final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    private ScheduledFuture<?> scheduledTask;
    private Runnable deliveryTask;

    @Value("${default.delivery.hour: 14}")
    private int deliveryTimeHour;

    @Value("${default.delivery.minute: 0}")
    private int deliveryTimeMinute;

    @Value("${default.delivery.second: 0}")
    private int deliveryTimeSecond;

    @Override
    public void run(String... args) throws Exception {
        scheduler.initialize();
        deliveryTask = deliveryService::startDelivery;
        start();
    }

    private void start(){
        LocalTime deliveryTime = Objects.requireNonNullElse(deliveryService.getLatestDeliveryTime(),
                LocalTime.of(deliveryTimeHour, deliveryTimeMinute, deliveryTimeSecond));
        String cronExpression = String.format("%d %d %d * * ?", deliveryTime.getSecond(), deliveryTime.getMinute(), deliveryTime.getHour());
        scheduledTask = scheduler.schedule(deliveryTask, new CronTrigger(cronExpression));
    }

    @Transactional
    public void setDeliveryTime(DeliveryTimePolicyRegisterDto registerDto) {
        if (scheduledTask != null) {
            scheduledTask.cancel(false);
        }
        log.info("set called with {}", registerDto);
        deliveryService.registerNewDeliveryTimePolicy(registerDto);
        start();
    }
}

package com.example.nbe341team02.domain.delivery.scheduler;

import com.example.nbe341team02.domain.delivery.dto.DeliveryTimePolicyRegisterDto;
import com.example.nbe341team02.domain.delivery.service.DeliveryService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Component
@RequiredArgsConstructor
@DependsOn("deliveryTimePolicyInitData")
public class DeliveryScheduler {
    private final DeliveryService deliveryService;

    private final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    private ScheduledFuture<?> scheduledTask;
    private Runnable deliveryTask;

    @PostConstruct
    public void init() {
        scheduler.initialize();
        deliveryTask = deliveryService::startDelivery;
        start();
    }

    private void start(){
        LocalTime deliveryTime = deliveryService.getLatestDeliveryTime();
        String cronExpression = String.format("%d %d %d * * ?", deliveryTime.getSecond(), deliveryTime.getMinute(), deliveryTime.getHour());
        scheduledTask = scheduler.schedule(deliveryTask, new CronTrigger(cronExpression));
    }

    @Transactional
    public void setDeliveryTime(DeliveryTimePolicyRegisterDto registerDto) {
        if (scheduledTask != null) {
            scheduledTask.cancel(false);
        }
        deliveryService.registerNewDeliveryTimePolicy(registerDto);
        start();
    }
}

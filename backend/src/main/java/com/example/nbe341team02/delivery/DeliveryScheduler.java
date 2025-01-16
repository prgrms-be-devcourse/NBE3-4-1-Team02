package com.example.nbe341team02.delivery;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.concurrent.ScheduledFuture;

@Component
@RequiredArgsConstructor
public class DeliveryScheduler {
    private final DeliveryService deliveryService;

    private final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    private ScheduledFuture<?> scheduledTask;
    private final Runnable deliveryTask = deliveryService::startDelivery;

    @PostConstruct
    public void init() {
        scheduler.initialize();
        start();
    }

    public void start(){
        LocalTime deliveryTime = deliveryService.getLatestDeliveryTime();
        String cronExpression = String.format("0 %d %d * * ?", deliveryTime.getMinute(), deliveryTime.getHour());
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

package com.example.nbe341team02.delivery;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.concurrent.ScheduledFuture;

@Component
@RequiredArgsConstructor
public class DeliveryScheduler {
    private final DeliveryService deliveryService;

    private final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    private ScheduledFuture<?> scheduledTask;

    private static final int DEFAULT_DELIVERY_HOUR = 14;

    @PostConstruct
    public void init() {
        scheduler.initialize();
        setDeliveryTime(DEFAULT_DELIVERY_HOUR);
    }

    public void setDeliveryTime(int hour, int minute) {
        if (scheduledTask != null) {
            scheduledTask.cancel(false);
        }

        Runnable deliveryTask = () -> {
            LocalDate today = LocalDate.now();
            deliveryService.startDelivery(
                    today.minusDays(1).atTime(hour, minute),
                    today.atTime(hour, minute));
        };

        String cronExpression = String.format("0 %d %d * * ?", minute, hour);
        scheduledTask = scheduler.schedule(deliveryTask, new CronTrigger(cronExpression));
    }

    public void setDeliveryTime(int hour) {
        setDeliveryTime(hour, 0);
    }
}

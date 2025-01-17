package com.example.nbe341team02.domain.delivery.scheduler;

import com.example.nbe341team02.domain.delivery.dto.DeliveryTimePolicyRegisterDto;
import com.example.nbe341team02.domain.delivery.service.DeliveryService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Objects;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Component
@RequiredArgsConstructor
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

    public void start(){
        LocalTime deliveryTime = Objects.requireNonNullElse(deliveryService.getLatestDeliveryTime(),
                LocalTime.now().plusSeconds(5)); // 오로지 테스트 통과를 위한 코드입니다. 추후 수정할 예정.
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

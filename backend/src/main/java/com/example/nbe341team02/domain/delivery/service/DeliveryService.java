package com.example.nbe341team02.domain.delivery.service;

import com.example.nbe341team02.domain.delivery.dto.DeliveryTimePolicyRegisterDto;
import com.example.nbe341team02.domain.delivery.entity.Delivery;
import com.example.nbe341team02.domain.delivery.entity.DeliveryCompany;
import com.example.nbe341team02.domain.delivery.entity.DeliveryTimePolicy;
import com.example.nbe341team02.domain.delivery.repository.DeliveryCompanyRepository;
import com.example.nbe341team02.domain.delivery.repository.DeliveryRepository;
import com.example.nbe341team02.domain.delivery.repository.DeliveryTimePolicyRepository;
import com.example.nbe341team02.domain.orders.entity.Order;
import com.example.nbe341team02.domain.orders.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryService {
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryCompanyRepository deliveryCompanyRepository;
    private final DeliveryTimePolicyRepository deliveryTimePolicyRepository;
    private static final LocalDateTime DEFAULT_START = LocalDateTime.of(2000, 1, 1, 0, 0);

    public void startDelivery(LocalDateTime start, LocalDateTime end){
        Set<Set<Order>> getOrdersToBeDelivered = getOrdersToBeDelivered(start, end);
        Set<DeliveryCompany> activeDeliveryCompanies = deliveryCompanyRepository.findByActive(true);
        for (Set<Order> orders : getOrdersToBeDelivered) {
            saveDelivery(orders, activeDeliveryCompanies);
        }
    }

    private Set<Set<Order>> getOrdersToBeDelivered(LocalDateTime start, LocalDateTime end){
        List<Order> ordersToBeDelivered = orderRepository.findByDeliveryIsNullAndCreatedAtIsBetween(start, end);
        Map<String, Set<Order>> groupedOrders = ordersToBeDelivered.stream()
                .collect(Collectors.groupingBy(order -> order.getEmail() + order.getAddress(), Collectors.toSet()));
        return new HashSet<>(groupedOrders.values());
    }

    private void saveDelivery(Set<Order> ordersToBeDelivered, Set<DeliveryCompany> activeDeliveryCompanies){
        deliveryRepository.save(Delivery.builder()
                .deliveryCompany(activeDeliveryCompanies.stream().findAny().orElseThrow())
                .orders(ordersToBeDelivered)
                .build());
    }

    public void startDelivery(){
        LocalDate today = LocalDate.now();
        LocalTime now = getLatestDeliveryTime();
        LocalDateTime end = LocalDateTime.of(today, now);
        startDelivery(DEFAULT_START, end);
    }

    public LocalTime getLatestDeliveryTime(){
        DeliveryTimePolicy deliveryTimePolicy =  deliveryTimePolicyRepository.findTopByOrderByCreatedAtDesc()
                .orElse(null);
        return deliveryTimePolicy == null ? null : deliveryTimePolicy.getDeliveryTime();
    }

    public void registerNewDeliveryTimePolicy(DeliveryTimePolicyRegisterDto registerDto){
        int minute = Objects.requireNonNullElse(registerDto.minute(), 0);
        int second = Objects.requireNonNullElse(registerDto.second(), 0);
        LocalTime newDeliveryTime = LocalTime.of(registerDto.hour(), minute, second);
        deliveryTimePolicyRepository.save(DeliveryTimePolicy
                .builder()
                .deliveryTime(newDeliveryTime)
                .build());
    }

    //Rest Template 나 Feign Client 를 통해 택배사로부터 송장번호를 받아온다는 전제를 둔 매서드. (실제 택배사로부터 송장번호 받아오는 건 일단 구현하지 않았습니다.)
    public void setDeliveryTrackingNumber(Long deliveryId, String trackingNumber){
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow();
        delivery.setDeliveryTrackingNumber(trackingNumber);
    }
}

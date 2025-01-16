package com.example.nbe341team02.delivery;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryService {
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryCompanyRepository deliveryCompanyRepository;
    private final DeliveryTimePolicyRepository deliveryTimePolicyRepository;
    private static final LocalDateTime DEFAULT_START = LocalDateTime.of(1900, 1, 1, 0, 0);

    public void startDelivery(LocalDateTime start, LocalDateTime end){
        Set<Set<Order>> getOrdersToBeDelivered = getOrdersToBeDelivered(start, end);
        Set<DeliveryCompany> activeDeliveryCompanies = deliveryCompanyRepository.findByActiveIs(true);
        for (Set<Order> orders : getOrdersToBeDelivered) {
            saveDelivery(orders, activeDeliveryCompanies);
        }
    }

    private Set<Set<Order>> getOrdersToBeDelivered(LocalDateTime start, LocalDateTime end){
        List<Order> ordersToBeDelivered = orderRepository.findByDeliveryIsNullAndOrderTimeisBetween(start, end);
        Map<String, Set<Order>> groupedOrders = ordersToBeDelivered.stream()
                .collect(Collectors.groupingBy(order -> order.getUsername() + order.getAddress()));
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
        return deliveryTimePolicyRepository.findTopByOrderByCreatedAtDesc()
                .orElseThrow().getDeliveryTime();
    }

    public void registerNewDeliveryTimePolicy(DeliveryTimePolicyRegisterDto registerDto){
        int minute = Objects.requireNonNullElse(registerDto.minute(), 0);
        LocalTime newDeliveryTime = LocalTime.of(registerDto.hour(), minute);
        deliveryTimePolicyRepository.save(DeliveryTimePolicy
                .builder()
                .deliveryTime(newDeliveryTime)
                .build());
    }

    public void setDeliveryTrackingNumber(Long deliveryId, String trackingNumber){
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow();
        delivery.setDeliveryTrackingNumber(trackingNumber);
    }
}

package com.example.nbe341team02.delivery;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryService {
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryCompanyRepository deliveryCompanyRepository;
    private final DeliveryTimePolicyRepository dailyTimePolicyRepository;

    public void startDelivery(LocalDateTime start, LocalDateTime end){
        List<Set<Order>> getOrdersToBeDelivered = getOrdersToBeDelivered(start, end);
        Set<DeliveryCompany> activeDeliveryCompanies = deliveryCompanyRepository.findByActiveIs(true);
        for (Set<Order> orders : getOrdersToBeDelivered) {
            saveDelivery(orders, activeDeliveryCompanies);
        }
    }

    public void setDeliveryTrackingNumber(Long deliveryId, String trackingNumber){
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow();
        delivery.setDeliveryTrackingNumber(trackingNumber);
    }

    private void saveDelivery(Set<Order> ordersToBeDelivered, Set<DeliveryCompany> activeDeliveryCompanies){
        deliveryRepository.save(Delivery.builder()
                .deliveryCompany(activeDeliveryCompanies.stream().findAny().orElseThrow())
                .orders(ordersToBeDelivered)
                .build());
    }

    private Set<Set<Order>> getOrdersToBeDelivered(LocalDateTime start, LocalDateTime end){
        List<Order> ordersToBeDelivered = orderRepository.findByOrderTimeBetween(start, end);
        Map<String, Set<Order>> groupedOrders = ordersToBeDelivered.stream()
                .collect(Collectors.groupingBy(order -> order.getUsername() + order.getAddress()));
        return new HashSet<>(groupedOrders.values());
    }
}

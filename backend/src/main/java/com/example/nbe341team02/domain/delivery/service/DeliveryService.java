package com.example.nbe341team02.domain.delivery.service;

import com.example.nbe341team02.domain.delivery.dto.DeliveryTimePolicyRegisterDto;
import com.example.nbe341team02.domain.delivery.dto.DeliveryTrackingDetailViewDto;
import com.example.nbe341team02.domain.delivery.dto.DeliveryTrackingThumbnailViewDto;
import com.example.nbe341team02.domain.delivery.entity.Delivery;
import com.example.nbe341team02.domain.delivery.entity.DeliveryCompany;
import com.example.nbe341team02.domain.delivery.entity.DeliveryTimePolicy;
import com.example.nbe341team02.domain.delivery.repository.DeliveryCompanyRepository;
import com.example.nbe341team02.domain.delivery.repository.DeliveryRepository;
import com.example.nbe341team02.domain.delivery.repository.DeliveryTimePolicyRepository;
import com.example.nbe341team02.domain.delivery.repository.DeliveryTrackingRepository;
import com.example.nbe341team02.domain.orders.entity.Order;
import com.example.nbe341team02.domain.orders.enums.OrderStatus;
import com.example.nbe341team02.domain.orders.repository.OrderRepository;
import com.example.nbe341team02.global.page.PageRequestDto;
import com.example.nbe341team02.global.page.PageableUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final DeliveryTrackingRepository deliveryTrackingRepository;
    private static final LocalDateTime DEFAULT_START = LocalDateTime.of(2000, 1, 1, 0, 0);

    public void startDelivery(LocalDateTime start, LocalDateTime end){
        Set<Set<Order>> getOrdersToBeDelivered = getOrdersToBeDelivered(start, end);
        Set<DeliveryCompany> activeDeliveryCompanies = deliveryCompanyRepository.findByActive(true);
        for (Set<Order> orders : getOrdersToBeDelivered) {
            saveDelivery(orders, activeDeliveryCompanies);
        }
    }

    private Set<Set<Order>> getOrdersToBeDelivered(LocalDateTime start, LocalDateTime end){
        List<Order> ordersToBeDelivered = orderRepository.findByDeliveryIsNullAndCreatedAtIsBetweenAndStatus(start, end, OrderStatus.COMPLETED);
        Map<String, Set<Order>> groupedOrders = ordersToBeDelivered.stream()
                .collect(Collectors.groupingBy(order -> order.getEmail() + order.getAddress(), Collectors.toSet()));
        return new HashSet<>(groupedOrders.values());
    }

    private void saveDelivery(Set<Order> ordersToBeDelivered, Set<DeliveryCompany> activeDeliveryCompanies){
        deliveryRepository.saveAndFlush(Delivery.builder()
                .deliveryCompany(activeDeliveryCompanies.stream().findAny().orElseThrow())
                .orders(ordersToBeDelivered)
                .build());
    }

    public void startDelivery(){
        LocalDate today = LocalDate.now();
        LocalTime deliveryTimePerDay = getLatestDeliveryTime();
        LocalDateTime deliveryTime = LocalDateTime.of(today, deliveryTimePerDay);
        startDelivery(DEFAULT_START, deliveryTime);
    }

    public LocalTime getLatestDeliveryTime(){
        DeliveryTimePolicy deliveryTimePolicy =  deliveryTimePolicyRepository.findTopByOrderByCreatedAtDesc()
                .orElse(null);
        return deliveryTimePolicy == null ? null : deliveryTimePolicy.getDeliveryTime();
    }

    public void registerNewDeliveryTimePolicy(DeliveryTimePolicyRegisterDto registerDto){
        LocalTime newDeliveryTime = LocalTime.of(registerDto.hour(), registerDto.minute(), registerDto.second());
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


    public Page<DeliveryTrackingThumbnailViewDto> getDeliveryTrackingPage(PageRequestDto pageRequest, String email){
        Pageable pageable = PageableUtils.createPageable(pageRequest, 5);
        return deliveryTrackingRepository.getDeliveryTrackingPage(pageable, email);
    }

    public DeliveryTrackingDetailViewDto getDeliveryTrackingDetail(Long orderId){
        return deliveryTrackingRepository.getDeliveryTrackingDetail(orderId);
    }

    public Page<DeliveryTimePolicy> getDeliveryTimePolicyPage(PageRequestDto pageRequest){
        Pageable pageable = PageableUtils.createPageable(pageRequest, 10);
        return deliveryTimePolicyRepository.findAllByOrderByCreatedAtDesc(pageable);
    }
}

package com.example.nbe341team02.domain.delivery.repository;

import com.example.nbe341team02.domain.delivery.dto.DeliveryTrackingDetailViewDto;
import com.example.nbe341team02.domain.delivery.dto.DeliveryTrackingThumbnailViewDto;
import com.example.nbe341team02.domain.delivery.entity.Delivery;
import com.example.nbe341team02.domain.delivery.entity.QDelivery;
import com.example.nbe341team02.domain.delivery.entity.QDeliveryCompany;
import com.example.nbe341team02.domain.orderProduct.entity.OrderProduct;
import com.example.nbe341team02.domain.orderProduct.entity.QOrderProduct;
import com.example.nbe341team02.domain.orders.entity.QOrder;
import com.example.nbe341team02.domain.product.entity.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
@Transactional
public class DeliveryTrackingRepository extends QuerydslRepositorySupport {

    private final QDelivery d = QDelivery.delivery;
    private final QDeliveryCompany dc = QDeliveryCompany.deliveryCompany;
    private final QProduct p = QProduct.product;
    private final QOrder o = QOrder.order;
    private final QOrderProduct op = QOrderProduct.orderProduct;

    public DeliveryTrackingRepository() {
        super(Delivery.class);
    }

    public Page<DeliveryTrackingThumbnailViewDto> getDeliveryTrackingPage (Pageable pageable, String email) {
        BooleanBuilder builder = emailFilter(email);
        long totalOrderCount = from(o)
                .where(builder)
                .fetchCount();
        List<DeliveryTrackingThumbnailViewDto> content = getDeliveryTrackingList(pageable, email);
        return new PageImpl<>(content, pageable, totalOrderCount);
    }

    private BooleanBuilder emailFilter(String email){
        BooleanBuilder builder = new BooleanBuilder();
        if (email != null) {
            builder.and(o.email.eq(email));
        }
        return builder;
    }


    private List<DeliveryTrackingThumbnailViewDto> getDeliveryTrackingList(Pageable pageable, String email) {
        BooleanBuilder builder = emailFilter(email);

        return from(o)
                .select(Projections.constructor(DeliveryTrackingThumbnailViewDto.class,
                        o.id,
                        o.email,
                        o.address,
                        o.postalCode,
                        from(op)
                                .innerJoin(p)
                                .on(op.product.eq(p))
                                .select(op.product.name.as("productName"))
                                .limit(1),
                        op.count(),
                        dc.companyName,
                        dc.trackingURLTemplate,
                        d.deliveryTrackingNumber
                ))
                .groupBy(o.id)
                .distinct()
                .innerJoin(op)
                .on(op.order.eq(o))
                .leftJoin(o.delivery, d)
                .innerJoin(d.deliveryCompany, dc)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(builder)
                .fetch();
    }

    public DeliveryTrackingDetailViewDto getDeliveryTrackingDetail(Long orderId) {
        DeliveryTrackingDetailViewDto detailViewDto = from(o)
                .select(Projections.constructor(DeliveryTrackingDetailViewDto.class,
                        o.id,
                        o.email,
                        o.address,
                        o.postalCode,
                        dc.companyName,
                        dc.trackingURLTemplate,
                        d.deliveryTrackingNumber
                ))
                .groupBy(o.id)
                .distinct()
                .innerJoin(op)
                .on(op.order.eq(o))
                .leftJoin(o.delivery, d)
                .innerJoin(d.deliveryCompany, dc)
                .fetchOne();

        if (detailViewDto == null) {
            return null;
        }

        Set<OrderProduct> orderProducts = getOrderProductSet(orderId);
        detailViewDto.setOrderProducts(orderProducts);
        long totalPrice = 0L;
        for (OrderProduct orderProduct : orderProducts) {
            totalPrice += orderProduct.getPrice() * orderProduct.getQuantity();
        }
        detailViewDto.setTotalPrice(totalPrice);
        return detailViewDto;
    }

    private Set<OrderProduct> getOrderProductSet(Long orderId){
        return Set.copyOf(from(op)
                .innerJoin(o)
                .on(op.order.eq(o))
                .where(o.id.eq(orderId))
                .fetch());
    }
}

package com.example.nbe341team02.domain.delivery.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDeliveryTimePolicy is a Querydsl query type for DeliveryTimePolicy
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeliveryTimePolicy extends EntityPathBase<DeliveryTimePolicy> {

    private static final long serialVersionUID = -1159371889L;

    public static final QDeliveryTimePolicy deliveryTimePolicy = new QDeliveryTimePolicy("deliveryTimePolicy");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final TimePath<java.time.LocalTime> deliveryTime = createTime("deliveryTime", java.time.LocalTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QDeliveryTimePolicy(String variable) {
        super(DeliveryTimePolicy.class, forVariable(variable));
    }

    public QDeliveryTimePolicy(Path<? extends DeliveryTimePolicy> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDeliveryTimePolicy(PathMetadata metadata) {
        super(DeliveryTimePolicy.class, metadata);
    }

}


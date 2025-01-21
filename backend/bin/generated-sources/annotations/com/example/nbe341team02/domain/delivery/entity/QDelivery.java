package com.example.nbe341team02.domain.delivery.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDelivery is a Querydsl query type for Delivery
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDelivery extends EntityPathBase<Delivery> {

    private static final long serialVersionUID = 966378768L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDelivery delivery = new QDelivery("delivery");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final QDeliveryCompany deliveryCompany;

    public final StringPath deliveryTrackingNumber = createString("deliveryTrackingNumber");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final SetPath<com.example.nbe341team02.domain.orders.entity.Order, com.example.nbe341team02.domain.orders.entity.QOrder> orders = this.<com.example.nbe341team02.domain.orders.entity.Order, com.example.nbe341team02.domain.orders.entity.QOrder>createSet("orders", com.example.nbe341team02.domain.orders.entity.Order.class, com.example.nbe341team02.domain.orders.entity.QOrder.class, PathInits.DIRECT2);

    public QDelivery(String variable) {
        this(Delivery.class, forVariable(variable), INITS);
    }

    public QDelivery(Path<? extends Delivery> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDelivery(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDelivery(PathMetadata metadata, PathInits inits) {
        this(Delivery.class, metadata, inits);
    }

    public QDelivery(Class<? extends Delivery> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.deliveryCompany = inits.isInitialized("deliveryCompany") ? new QDeliveryCompany(forProperty("deliveryCompany")) : null;
    }

}


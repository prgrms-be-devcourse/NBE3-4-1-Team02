package com.example.nbe341team02.domain.delivery.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDeliveryCompany is a Querydsl query type for DeliveryCompany
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeliveryCompany extends EntityPathBase<DeliveryCompany> {

    private static final long serialVersionUID = -641647283L;

    public static final QDeliveryCompany deliveryCompany = new QDeliveryCompany("deliveryCompany");

    public final BooleanPath active = createBoolean("active");

    public final StringPath companyName = createString("companyName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath trackingURLTemplate = createString("trackingURLTemplate");

    public QDeliveryCompany(String variable) {
        super(DeliveryCompany.class, forVariable(variable));
    }

    public QDeliveryCompany(Path<? extends DeliveryCompany> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDeliveryCompany(PathMetadata metadata) {
        super(DeliveryCompany.class, metadata);
    }

}


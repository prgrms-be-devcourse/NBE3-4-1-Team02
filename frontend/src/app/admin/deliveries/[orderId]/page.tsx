"use client";

import React, { useEffect, useState } from "react";
import {useParams, useRouter} from "next/navigation";
import Link from "next/link";

type Product = {
    id: number;
    name: string;
    price: number;
    stock: number;
    status: boolean;
    description: string;
    imageUrl: string;
}

type OrderProduct = {
    id : number;
    product: Product;
    quantity: number;
    price: number;
}

type DeliveryTrackingDetailViewDto = {
    orderId: number;
    orderTime: string;
    email: string;
    address: string;
    postalCode: string;
    totalPrice: number;
    orderProducts:Set<OrderProduct> ;
    deliveryCompanyName: string;
    trackingURLTemplate: string;
    deliveryTrackingNumber: string;
}


export default function DeliveryDetailsPage() {
    const [deliveryData, setDeliveryData] = useState<DeliveryTrackingDetailViewDto | null>(null);
    const { orderId } = useParams();
    const router = useRouter();

    useEffect(() => {
        const fetchDeliveryDetails = async () => {
            if (!orderId) return;

            try {
                const response = await fetch(`http://localhost:8080/api/v1/admin/deliveries/${orderId}`);

                if (!response.ok) {
                    if (response.status === 404){
                        alert("주문이 존재하지 않습니다.");
                        router.push("/admin/deliveries");
                    }
                    throw new Error();
                }
                const data: DeliveryTrackingDetailViewDto = await response.json();
                setDeliveryData(data);
            } catch (error) {
                console.error(error);
            }
        };

        fetchDeliveryDetails();
    }, [orderId]);

    const formatOrderTime = (orderTime: string) => {
        const date = new Date(orderTime);
        return new Intl.DateTimeFormat('ko-KR', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit',
        }).format(date);
    };

    const formImageUrl = (imageUrl: string) => {
        return `http://localhost:8080${imageUrl.replace('/static', '/api/v1')}`;
    }

    return (
        <div className="min-h-screen bg-gray-100">
            <div className="max-w-4xl mx-auto p-8">
                <h1 className="text-3xl font-bold text-center text-gray-800 mb-8">주문 상세</h1>

                {deliveryData ? (
                    <div className="bg-white p-20 rounded-lg shadow-md">
                        <div className="space-y-8">
                            <div className="flex justify-between">
                                <span className="font-semibold text-gray-600">Email:</span>
                                <span className="text-gray-800">{deliveryData.email}</span>
                            </div>
                            <div className="flex justify-between">
                                <span className="font-semibold text-gray-600">Email:</span>
                                <span className="text-gray-800">{formatOrderTime(deliveryData.orderTime)}</span>
                            </div>
                            <div className="flex justify-between">
                                <span className="font-semibold text-gray-600">주문 주소:</span>
                                <span className="text-gray-800">{deliveryData.address}</span>
                            </div>
                            <div className="flex justify-between">
                                <span className="font-semibold text-gray-600">우편 번호:</span>
                                <span className="text-gray-800">{deliveryData.postalCode}</span>
                            </div>
                            <div className="flex justify-between">
                                <span className="font-semibold text-gray-600">총 주문 금액:</span>
                                <span className="text-gray-800">{deliveryData.totalPrice.toLocaleString()} 원</span>
                            </div>

                            {deliveryData.orderProducts && (
                                <div className="mt-6">
                                    <h2 className="text-lg font-semibold text-gray-800 mb-4">주문 상품</h2>
                                    <div className="space-y-4">
                                        {Array.from(deliveryData.orderProducts).map((orderProduct) => (
                                            <div
                                                key={orderProduct.id}
                                                className="flex items-center justify-between p-4 bg-white border rounded-md shadow-sm"
                                            >
                                                {/* 상품 이미지 */}
                                                <div className="flex items-center space-x-4">
                                                    <img
                                                        src={formImageUrl(orderProduct.product.imageUrl)}
                                                        alt={orderProduct.product.name}
                                                        className="w-16 h-16 rounded-md object-cover"
                                                    />
                                                    <div>
                                                        {/* 상품 이름 및 설명 */}
                                                        <h3 className="font-bold text-gray-700">{orderProduct.product.name}</h3>
                                                        {/*<p className="text-sm text-gray-500">{orderProduct.product.description}</p>*/}
                                                    </div>
                                                </div>
                                                {/* 주문 상세 정보 */}
                                                <div className="text-right">
                                                    <p className="text-gray-600">
                                                        수량: <span
                                                        className="font-semibold">{orderProduct.quantity}</span>
                                                    </p>
                                                    <p className="text-gray-600">
                                                        개당 : <span
                                                        className="font-semibold">{orderProduct.price.toLocaleString()}원</span>
                                                    </p>
                                                    <p className="text-gray-800 font-semibold">
                                                        총
                                                        : {(orderProduct.price * orderProduct.quantity).toLocaleString()}원
                                                    </p>
                                                </div>
                                            </div>
                                        ))}
                                    </div>
                                </div>
                            )}

                            {deliveryData.deliveryCompanyName ? (
                                <div className="mt-6">
                                    <h2 className="text-lg font-semibold text-gray-800 mb-4">배송 정보</h2>
                                    <div className="space-y-4">
                                        <div className="flex justify-between">
                                            <span className="font-semibold text-gray-600">Delivery Company:</span>
                                            <span className="text-gray-800">{deliveryData.deliveryCompanyName}</span>
                                        </div>
                                        <div className="flex justify-between">
                                            <span className="font-semibold text-gray-600">Tracking Number:</span>
                                            <span className="text-gray-800">{deliveryData.deliveryTrackingNumber}</span>
                                        </div>
                                        <div className="flex justify-between">
                                            <span className="font-semibold text-gray-600">Tracking URL:</span>
                                            <Link
                                                href={deliveryData.trackingURLTemplate}
                                                target="_blank"
                                                rel="noopener noreferrer"
                                                className="text-blue-500 hover:underline"
                                            >
                                                Track Your Order
                                            </Link>
                                        </div>
                                    </div>
                                </div>
                            ) : (
                                <div className="mt-6">
                                    <h2 className="text-lg font-semibold text-gray-800 mb-4">배송 정보</h2>
                                    <div className="text-gray-800">배송 시작 전입니다.</div>
                                </div>
                            )}
                        </div>
                    </div>
                ) : (
                    <div className="flex items-center justify-center min-h-[200px] bg-gray-50 rounded-md shadow-md">
                        <p className="text-gray-500">Loading delivery details...</p>
                    </div>
                )}
            </div>
        </div>
    );
}

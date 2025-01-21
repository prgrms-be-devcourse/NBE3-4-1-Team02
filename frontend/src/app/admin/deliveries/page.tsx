"use client";

import React, { useState, useEffect } from "react";
import { useSearchParams, useRouter } from "next/navigation";
import Link from "next/link";

type DeliveryTrackingThumbnailViewDto = {
    orderId: number;
    email: string;
    deliveryCompanyName: string | null;
    deliveryTrackingNumber: string | null;
    thumbnailProductName: string;
    totalCountOfProductType: number;
    address: string;
    postalCode: string;
    trackingURLTemplate: string;
};

export default function DeliveriesPage() {
    const [deliveries, setDeliveries] = useState<DeliveryTrackingThumbnailViewDto[]>([]);
    const [page, setPage] = useState(1);
    const [email, setEmail] = useState("");
    const [totalPages, setTotalPages] = useState(1);
    const searchParams = useSearchParams();  // 쿼리 파라미터를 처리
    const router = useRouter();

    // 데이터를 fetch하는 함수
    const fetchDeliveries = async () => {
        try {
            const validPage = Number(page) && page > 0 ? Number(page) : 1;

            const params = new URLSearchParams();
            params.append("page", validPage.toString());
            if (email) params.append("email", email);

            const response = await fetch(
                `http://localhost:8080/api/v1/admin/deliveries?${params.toString()}`
            );

            if (!response.ok) {
                throw new Error("Failed to fetch deliveries");
            }

            const data = await response.json();
            setDeliveries(data.content);  // Assuming Spring Pageable returns content array
            setTotalPages(data.totalPages);  // Assuming totalPages is returned
        } catch (error) {
            console.error(error);
        }
    };

    const handleSearch = (e: React.FormEvent) => {
        e.preventDefault();

        router.push(`/admin/deliveries?email=${email}`);
        setPage(1);  // 검색 시 페이지 초기화
        fetchDeliveries();  // 검색 버튼을 눌렀을 때 데이터 fetch
    };

    useEffect(() => {
        const emailParam = searchParams.get("email") || "";
        setEmail(emailParam); // email만 상태에 설정
        fetchDeliveries(); // 이메일이 변경될 때마다 데이터 요청
    }, [searchParams]);

    const handlePageChange = (newPage: number) => {
        const validPage = isNaN(newPage) || newPage <= 0 ? 1 : newPage;
        setPage(validPage);

        // page는 URL에 포함하지 않음
        if (email) {
            router.push(`/admin/deliveries?email=${email}`); // email만 URL에 추가
        } else {
            router.push(`/admin/deliveries`); // email이 없으면 URL에 페이지 파라미터를 제외
        }
    };


    return (
        <div className="min-h-screen bg-gray-100">
            <div className="max-w-4xl mx-auto p-8">
                <h1
                    className="text-3xl font-bold text-center text-gray-800 mb-8 cursor-pointer"
                    onClick={() => {
                        setPage(1); // 페이지 상태 초기화
                        router.push("/admin/deliveries"); // page 파라미터 없이 이동
                    }}
                >
                    배송 관리
                </h1>

                <form onSubmit={handleSearch} className="mb-6 flex items-center space-x-4">
                    <input
                        type="text"
                        placeholder="이메일로 검색"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        className="flex-1 p-2 border rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 text-gray-800"
                    />
                    <button
                        type="submit"
                        className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600"
                    >
                        검색
                    </button>
                </form>

                <div className="bg-white p-6 rounded-lg shadow-md space-y-4">
                    {deliveries.map((delivery) => (
                        <div
                            key={delivery.orderId}
                            className="flex items-center justify-between p-4 border rounded-md"
                        >
                            <div>
                                <h3 className="font-bold text-gray-700">
                                    {delivery.thumbnailProductName}
                                    {delivery.totalCountOfProductType > 1 && (
                                        <span
                                            className="text-sm text-gray-500"> 외 {delivery.totalCountOfProductType - 1}개</span>
                                    )}
                                </h3>
                                <p className="text-sm text-gray-500">Email: {delivery.email}</p>
                            </div>
                            <Link
                                href={`/admin/deliveries/${delivery.orderId}`}
                                className="text-blue-500 hover:underline"
                            >
                                상세 보기
                            </Link>
                        </div>
                    ))}
                </div>

                <div className="flex justify-between mt-6">
                    {/* 이전 버튼 */}
                    <div className="flex-1">
                        {page > 1 && (
                            <button
                                onClick={() => handlePageChange(page - 1)}
                                className="px-4 py-2 bg-gray-300 rounded-md"
                            >
                                이전
                            </button>
                        )}
                    </div>

                    {/* 페이지 정보 */}
                    <span className="text-gray-700 flex-1 text-center">Page {page} of {totalPages}</span>

                    {/* 다음 버튼 */}
                    <div className="flex-1 text-right">
                        {page < totalPages && (
                            <button
                                onClick={() => handlePageChange(page + 1)}
                                className="px-4 py-2 bg-gray-300 rounded-md"
                            >
                                다음
                            </button>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
}

"use client"

import React, { useEffect, useState } from "react";
import { useRouter } from "next/navigation";

type DeliveryTimePolicy = {
    id: number;
    deliveryTime: string;
    createdAt: string;
};

type PageResponse = {
    content: DeliveryTimePolicy[];
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
};


const DeliveryTimePolicyPage = () => {
    const [deliveryTimePolicies, setDeliveryTimePolicies] = useState<DeliveryTimePolicy[]>([]);
    const [currentPage, setCurrentPage] = useState<number>(1); // 1부터 시작
    const [totalPages, setTotalPages] = useState<number>(0);
    const router = useRouter();

    const fetchDeliveryTimePolicies = async (page: number) => {
        try {
            // 1부터 시작하므로 page - 1을 보냄
            if (!localStorage.getItem('adminToken')) {
                router.push('/admin/login');
                return;
            }
            const token = localStorage.getItem('adminToken');
            const response = await fetch(`http://localhost:8080/api/v1/admin/policies/delivery-time?page=${page}&size=5`,
                {
                    method: "GET",
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        "Content-Type": "application/json",
                    }
                });
            if (!response.ok) {
                throw new Error("데이터를 가져오는데 실패했습니다.");
            }
            const data: PageResponse = await response.json();
            setDeliveryTimePolicies(data.content);
            setTotalPages(data.totalPages);
        } catch (error) {
            console.error(error);
        }
    };

    useEffect(() => {
        fetchDeliveryTimePolicies(currentPage);
    }, [currentPage]);

    const handlePageChange = (newPage: number) => {
        if (newPage >= 1 && newPage <= totalPages) { // 1 이상 totalPages 이하로 페이지 제한
            setCurrentPage(newPage);
        }
    };

    return (
        <div className="max-w-4xl mx-auto p-8">
            <h1 className="text-3xl font-bold text-center text-gray-800 mb-8">배송 시간 정책</h1>
            <div className="px-4 py-3 text-sm text-center font-semibold text-gray-800"><span>제일 최신 배송 시간만 적용됩니다.</span></div>
            <div className="flex justify-center mb-6">
                <button
                    onClick={() => router.push("/admin/policies/delivery-time/create-form")/* 여기에 신규 등록 페이지로 리디렉션하는 로직 작성 */}
                    className="px-6 py-3 bg-blue-500 text-white rounded-md hover:bg-blue-600 transition-colors"
                >
                    신규 등록
                </button>
            </div>
            <div className="overflow-x-auto max-w-4xl mx-auto">
                <table
                    className="min-w-full table-auto border-separate border-spacing-0 border border-gray-200 rounded-lg shadow-md">
                    <thead>
                    <tr>
                        <th className="px-4 py-2 bg-blue-500 text-white rounded-md w-1/2">배송 시간</th>
                        <th className="px-4 py-2 bg-blue-500 text-white rounded-md w-1/2">등록일</th>
                    </tr>
                    </thead>
                    <tbody>
                    {deliveryTimePolicies.map((policy) => (
                        <tr key={policy.id}>
                            <td className="px-4 py-3 text-sm text-center font-semibold text-gray-800 border-b">매일 {policy.deliveryTime}</td>
                            <td className="px-4 py-3 text-sm text-center font-semibold text-gray-800 border-b">{policy.createdAt}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>

            <div className="flex justify-between mt-6">
                {/* 이전 버튼 */}
                <div className="flex-1">
                    {currentPage > 1 && (
                        <button
                            onClick={() => handlePageChange(currentPage - 1)}
                            className="px-4 py-2 bg-blue-500 rounded-md hover:bg-blue-600 transition-shadow"
                        >
                            이전
                        </button>
                    )}
                </div>

                {/* 페이지 정보 */}
                <span className="text-gray-700 flex-1 text-center">Page {currentPage} of {totalPages}</span>

                {/* 다음 버튼 */}
                <div className="flex-1 text-right">
                    {currentPage < totalPages && (
                        <button
                            onClick={() => handlePageChange(currentPage + 1)}
                            className="px-4 py-2 bg-blue-500 rounded-md hover:bg-blue-600 transition-shadow"
                        >
                            다음
                        </button>
                    )}
                </div>
            </div>
        </div>
    );
};

export default DeliveryTimePolicyPage;

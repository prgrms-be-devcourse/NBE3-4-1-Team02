// "use client";
//
// import React, { useState, useEffect } from "react";
// import { useSearchParams, useRouter } from "next/navigation";
// import Link from "next/link";
//
// type DeliveryTrackingThumbnailViewDto = {
//     orderId: number;
//     email: string;
//     deliveryCompanyName: string | null;
//     deliveryTrackingNumber: string | null;
//     thumbnailProductName: string;
//     totalCountOfProductType: number;
//     address: string;
//     postalCode: string;
//     trackingURLTemplate: string;
// };
//
// export default function DeliveriesPage() {
//     const [deliveries, setDeliveries] = useState<DeliveryTrackingThumbnailViewDto[]>([]);
//     const [page, setPage] = useState(1);
//     const [email, setEmail] = useState("");
//     const [totalPages, setTotalPages] = useState(1);
//     const searchParams = useSearchParams();  // 쿼리 파라미터를 처리
//     const router = useRouter();
//
//     useEffect(() => {
//         const fetchDeliveries = async () => {
//             try {
//                 const params = new URLSearchParams();
//                 if (page) params.append("page", page.toString());
//                 if (email) params.append("email", email);
//
//                 const response = await fetch(
//                     `http://localhost:8080/api/v1/admin/deliveries?${params.toString()}`
//                 );
//
//                 if (!response.ok) {
//                     throw new Error("Failed to fetch deliveries");
//                 }
//
//                 const data = await response.json();
//                 setDeliveries(data.content);  // Assuming Spring Pageable returns content array
//                 setTotalPages(data.totalPages);  // Assuming totalPages is returned
//             } catch (error) {
//                 console.error(error);
//             }
//         };
//
//         fetchDeliveries();
//     }, [page, email]);  // 의존성 배열에 page와 email을 넣어줍니다.
//
//     const handleSearch = (e: React.FormEvent) => {
//         e.preventDefault();
//         setPage(1);  // 검색 시 페이지 초기화
//     };
//
//     const handlePageChange = (newPage: number) => {
//         setPage(newPage);
//     };
//
//     return (
//         <div className="min-h-screen bg-gray-100">
//             <div className="max-w-4xl mx-auto p-8">
//                 <h1 className="text-3xl font-bold text-center text-gray-800 mb-8">배송 관리</h1>
//
//                 <form onSubmit={handleSearch} className="mb-6 flex items-center space-x-4">
//                     <input
//                         type="text"
//                         placeholder="이메일로 검색"
//                         value={email}
//                         onChange={(e) => setEmail(e.target.value)}
//                         className="flex-1 p-2 border rounded-md"
//                     />
//                     <button
//                         type="submit"
//                         className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600"
//                     >
//                         검색
//                     </button>
//                 </form>
//
//                 <div className="bg-white p-6 rounded-lg shadow-md space-y-4">
//                     {deliveries.map((delivery) => (
//                         <div
//                             key={delivery.orderId}
//                             className="flex items-center justify-between p-4 border rounded-md"
//                         >
//                             <div>
//                                 <h3 className="font-bold text-gray-700">Order ID: {delivery.orderId}</h3>
//                                 <p className="text-sm text-gray-500">Email: {delivery.email}</p>
//                             </div>
//                             <Link
//                                 href={`/admin/deliveries/${delivery.orderId}`}
//                                 className="text-blue-500 hover:underline"
//                             >
//                                 상세 보기
//                             </Link>
//                         </div>
//                     ))}
//                 </div>
//
//                 <div className="flex justify-between mt-6">
//                     <button
//                         onClick={() => handlePageChange(page - 1)}
//                         disabled={page <= 1}
//                         className={`px-4 py-2 bg-gray-300 rounded-md ${page <= 1 && "opacity-50 cursor-not-allowed"}`}
//                     >
//                         이전
//                     </button>
//                     <span className="text-gray-700">Page {page} of {totalPages}</span>
//                     <button
//                         onClick={() => handlePageChange(page + 1)}
//                         disabled={page >= totalPages}
//                         className={`px-4 py-2 bg-gray-300 rounded-md ${page >= totalPages && "opacity-50 cursor-not-allowed"}`}
//                     >
//                         다음
//                     </button>
//                 </div>
//             </div>
//         </div>
//     );
// }
//
"use client";

import React, { useState, useEffect } from "react";
import { useSearchParams, useRouter } from "next/navigation";
import Link from "next/link";

// Delivery 타입을 DeliveryTrackingThumbnailViewDto로 변경
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
            const params = new URLSearchParams();
            if (page) params.append("page", page.toString());
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

    useEffect(() => {
        // 첫 로드 시 데이터 fetch
        fetchDeliveries();
    }, [page]);  // 페이지가 바뀔 때만 데이터를 fetch

    const handleSearch = (e: React.FormEvent) => {
        e.preventDefault();
        setPage(1);  // 검색 시 페이지 초기화
        fetchDeliveries();  // 검색 버튼을 눌렀을 때 데이터 fetch
    };

    const handlePageChange = (newPage: number) => {
        setPage(newPage);
    };

    return (
        <div className="min-h-screen bg-gray-100">
            <div className="max-w-4xl mx-auto p-8">
                <h1 className="text-3xl font-bold text-center text-gray-800 mb-8">배송 관리</h1>

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

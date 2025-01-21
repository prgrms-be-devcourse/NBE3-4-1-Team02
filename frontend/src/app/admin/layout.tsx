"use client";

import React, { useState } from 'react';
import Link from "next/link";

export default function AdminLayout({
                                           children,
                                       }: {
    children: React.ReactNode
}) {
    // '정책 관리' 클릭 시 서브 메뉴 표시 여부를 위한 상태
    const [isPoliciesMenuOpen, setIsPoliciesMenuOpen] = useState(false);

    // 서브 메뉴 열고 닫는 함수
    const togglePoliciesMenu = () => {
        setIsPoliciesMenuOpen((prev) => !prev); // 상태 토글
    };

    return (
        <div className="min-h-screen flex">
            {/* 사이드바 */}
            <div className="w-64 bg-blue-500 text-white p-6">
                <h2 className="text-xl font-semibold mb-6">관리자 메뉴</h2>
                <ul>
                    <li>
                        <Link href="/admin/products" className="block py-2 hover:bg-blue-600 transition-colors rounded">
                            제품 관리
                        </Link>
                    </li>
                    <li>
                        <Link href="/admin/deliveries" className="block py-2 hover:bg-blue-600 transition-colors rounded">
                            배송 관리
                        </Link>
                    </li>
                    <li className="relative">
                        <Link
                            href="#"
                            onClick={togglePoliciesMenu}
                            className="block py-2 hover:bg-blue-600 transition-colors rounded"
                        >
                            정책 관리
                        </Link>
                        {/* 서브 메뉴 (CSS 애니메이션 적용) */}
                        <ul
                            className={`absolute left-0 w-full bg-blue-500 transition-colors rounded mt-1 transition-all duration-300 ease-in-out ${
                                isPoliciesMenuOpen ? 'max-h-96 opacity-100' : 'max-h-0 opacity-0'
                            } overflow-hidden`}
                        >
                            <li>
                                <Link href="/admin/policies/delivery-time" className="text-sm block py-2 px-4 hover:bg-blue-600 transition-colors">
                                    배송 시간 정책
                                </Link>
                            </li>
                            <li>
                                <Link href="#" className="text-sm block py-2 px-4 hover:bg-blue-600 transition-colors">
                                    이용 약관
                                </Link>
                            </li>
                            <li>
                                <Link href="#" className="text-sm block py-2 px-4 hover:bg-blue-600 transition-colors">
                                    개인정보 처리방침
                                </Link>
                            </li>
                            <li>
                                <Link href="#" className="text-sm block py-2 px-4 hover:bg-blue-600 transition-colors">
                                    환불 정책
                                </Link>
                            </li>
                        </ul>
                    </li>
                    {/* 추가적인 메뉴 항목들 */}
                </ul>
            </div>

            {/* 콘텐츠 영역 */
            }
            <div className="flex-1 p-8 bg-gray-100">
                {children} {/* 여기에 각 페이지의 콘텐츠가 렌더링됩니다 */}
    </div>
</div>
)
}
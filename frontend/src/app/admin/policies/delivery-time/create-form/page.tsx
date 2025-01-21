"use client";

import React, { useState } from "react";
import { useRouter } from "next/navigation";

export default function DeliveryTimePolicyPage() {
    const [amPm, setAmPm] = useState("AM");
    const [hour, setHour] = useState("1");
    const [minute, setMinute] = useState("0");
    const [second, setSecond] = useState("0");
    const router = useRouter();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        // 오후(PM)인 경우 시간에 12를 더하는 로직 추가
        let adjustedHour = parseInt(hour);
        if (amPm === "PM" && adjustedHour !== 12) {
            adjustedHour += 12;  // 오후 12시가 아닌 경우 12를 더함
        }
        if (amPm === "AM" && adjustedHour === 12) {
            adjustedHour = 0;  // 오전 12시는 0시로 처리
        }

        const payload = {
            hour: adjustedHour,
            minute: parseInt(minute),
            second: parseInt(second),
        };

        try {

            if (!localStorage.getItem('adminToken')) {
                router.push('/admin/login');
                return;
            }
            const token = localStorage.getItem('adminToken');

            const response = await fetch("http://localhost:8080/api/v1/admin/policies/delivery-time", {
                method: "POST",
                headers: {
                    'Authorization': `Bearer ${token}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(payload),
            });

            if (response.ok) {
                alert("정책이 성공적으로 등록되었습니다.");
                router.push("/admin/policies/delivery-time");
            } else {
                alert("정책 등록에 실패했습니다.");
            }
        } catch (error) {
            console.error(error);
            alert("서버와의 연결에 실패했습니다.");
        }
    };

    return (
        <div className="flex flex-col items-center p-8 text-black">
            <h1 className="text-3xl font-bold text-center text-gray-800 mb-8">배송 시간 정책 등록</h1>

            <form onSubmit={handleSubmit} className="flex flex-col items-center w-full max-w-md space-y-6">
                <div className="flex items-center space-x-4">
                    {/* 오전/오후 선택 */}
                    <select
                        value={amPm}
                        onChange={(e) => setAmPm(e.target.value)}
                        className="p-2 border border-gray-300 rounded-md text-lg w-24"
                    >
                        <option value="AM">오전</option>
                        <option value="PM">오후</option>
                    </select>

                    {/* 시간 선택 */}
                    <select
                        value={hour}
                        onChange={(e) => setHour(e.target.value)}
                        className="p-2 border border-gray-300 rounded-md text-lg w-20"
                    >
                        {[...Array(12).keys()].map((i) => (
                            <option key={i} value={i + 1}>
                                {i + 1}
                            </option>
                        ))}
                    </select>

                    <span className="text-lg">:</span>

                    {/* 분 선택 */}
                    <select
                        value={minute}
                        onChange={(e) => setMinute(e.target.value)}
                        className="p-2 border border-gray-300 rounded-md text-lg w-20"
                    >
                        {[...Array(60).keys()].map((i) => (
                            <option key={i} value={i}>
                                {i < 10 ? `0${i}` : i}
                            </option>
                        ))}
                    </select>

                    <span className="text-lg">:</span>

                    {/* 초 선택 */}
                    <select
                        value={second}
                        onChange={(e) => setSecond(e.target.value)}
                        className="p-2 border border-gray-300 rounded-md text-lg w-20"
                    >
                        {[...Array(60).keys()].map((i) => (
                            <option key={i} value={i}>
                                {i < 10 ? `0${i}` : i}
                            </option>
                        ))}
                    </select>
                </div>

                <button
                    type="submit"
                    className="px-6 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 transition-colors"
                >
                    저장
                </button>
            </form>
        </div>
    );
}

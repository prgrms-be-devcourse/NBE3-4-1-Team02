"use client";

import { useEffect } from "react";
import { useRouter } from "next/navigation";

export default function Dashboard() {
  const router = useRouter();

  useEffect(() => {
    // 컴포넌트 마운트 시 토큰 확인
    const token = localStorage.getItem("adminToken");
    if (!token) {
      router.push("/admin/login");
    }
  }, [router]);

  const handleLogout = async () => {
    try {
        const token = localStorage.getItem("adminToken");
        await fetch("http://localhost:8080/api/v1/admin/logout", {
            method: "POST",
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        
        // 로컬 스토리지의 토큰 제거
        localStorage.removeItem("adminToken");
        // 로그인 페이지로 리다이렉트
        router.push("/admin/login");
    } catch (err) {
        console.error("로그아웃 중 오류 발생:", err);
            // 에러가 발생해도 토큰 제거 및 로그인 페이지로 리다이렉트
        localStorage.removeItem("adminToken");
        router.push("/admin/login");
    }
};

  return (
    <div className="min-h-screen bg-gray-100 p-8">
      <div className="max-w-4xl mx-auto">
        <h1 className="text-3xl font-bold mb-8 text-black">관리자 대시보드</h1>
        <div className="bg-white rounded-lg shadow p-6">
          <h2 className="text-xl font-semibold mb-4 text-black">환영합니다!</h2>
          <button
            onClick={handleLogout}
            className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600"
          >
            로그아웃
          </button>
        </div>
      </div>
    </div>
  );
} 
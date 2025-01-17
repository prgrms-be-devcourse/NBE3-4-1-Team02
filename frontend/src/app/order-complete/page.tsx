"use client";

import { useSearchParams } from "next/navigation";

export default function OrderComplete() {
  const searchParams = useSearchParams();
  const email = searchParams.get("email");
  const status = searchParams.get("status");
  const orderProducts = JSON.parse(searchParams.get("orderProducts") || "[]"); // 문자열을 JSON 객체로 변환

  if (!email || !status || orderProducts.length === 0) {
    return (
        <div className="text-center mt-10">
          주문 정보를 불러오는 데 실패했습니다.
        </div>
    );
  }

  return (
      <div className="max-w-3xl mx-auto p-6 bg-white shadow-md rounded-lg mt-10">
        <h1 className="text-2xl font-bold text-center text-green-500">
          주문이 완료되었습니다!
        </h1>
        <p className="text-center text-gray-700 mt-4">
          이메일: <strong>{email}</strong>
        </p>
        <p className="text-center text-gray-700 mt-2">
          주문 상태: <strong>{status}</strong>
        </p>

        {/* 주문 상품 정보 */}
        <div className="mt-6">
          <h2 className="text-lg font-semibold text-gray-700">주문 상품</h2>
          <ul className="mt-4 space-y-2">
            {orderProducts.map((product: any, index: number) => (
                <li
                    key={index}
                    className="p-4 bg-gray-100 rounded-md shadow-sm flex justify-between items-center"
                >
                  <div>
                    <h3 className="font-semibold">{product.productName}</h3>
                    <p className="text-gray-600">가격: {product.price}원</p>
                  </div>
                  <p className="text-gray-700">수량: {product.quantity}개</p>
                </li>
            ))}
          </ul>
        </div>
      </div>
  );
}

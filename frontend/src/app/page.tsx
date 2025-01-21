"use client";

import React, { useState, useEffect } from "react";
import { useRouter } from "next/navigation";

declare global {
  interface Window {
    daum: any;
  }
}

// 타입 정의
type Product = {
  id: number;
  name: string;
  price: number;
  stock: number;
  status: boolean;
};

type CartItem = {
  productId: number;
  name: string;
  quantity: number;
  price: number;
};

export default function CreateOrder() {
  const router = useRouter();

  // 상태 정의
  const [products, setProducts] = useState<Product[]>([]); // 상품 목록
  const [cart, setCart] = useState<CartItem[]>([]); // 장바구니
  const [email, setEmail] = useState(""); // 이메일
  const [address, setAddress] = useState(""); // 주소
  const [postalCode, setPostalCode] = useState(""); // 우편번호
  const [totalPrice, setTotalPrice] = useState(0); // 총 금액

  // 상품 목록 가져오기
  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/v1/products");
        if (!response.ok) {
          throw new Error("상품 데이터를 가져오는 데 실패했습니다.");
        }
        const data: Product[] = await response.json();
        setProducts(data);
      } catch (err) {
        console.error(err);
        alert("상품 데이터를 가져오는 중 오류가 발생했습니다.");
      }
    };

    fetchProducts();
  }, []);

  // Daum 우편번호 검색 API
  const handleAddressSearch = () => {
    new window.daum.Postcode({
      oncomplete: function (data: any) {
        setAddress(data.address); // 주소 입력
        setPostalCode(data.zonecode); // 우편번호 입력
      },
    }).open();
  };

  // 장바구니에 상품 추가
  const addToCart = (product: Product) => {
    const existingProduct = cart.find((item) => item.productId === product.id);
    if (existingProduct) {
      setCart(
          cart.map((item) =>
              item.productId === product.id
                  ? { ...item, quantity: item.quantity + 1 }
                  : item
          )
      );
    } else {
      setCart([
        ...cart,
        { productId: product.id, name: product.name, quantity: 1, price: product.price },
      ]);
    }
    setTotalPrice(totalPrice + product.price);
  };

  // 장바구니에서 상품 삭제
  const removeFromCart = (productId: number) => {
    const product = cart.find((item) => item.productId === productId);
    if (product) {
      setCart(cart.filter((item) => item.productId !== productId));
      setTotalPrice(totalPrice - product.price * product.quantity);
    }
  };

  // 주문 생성
  const handleOrderSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!email || !address || cart.length === 0) {
      alert("이메일, 주소, 상품을 입력해주세요!");
      return;
    }

    const requestData = {
      email,
      address,
      postalCode,
      orderProducts: cart.map((item) => ({
        productId: item.productId,
        quantity: item.quantity,
      })),
    };

    try {
      const response = await fetch("http://localhost:8080/api/v1/orders", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(requestData),
      });

      if (response.ok) {
        const data = await response.json();
        alert("주문이 성공적으로 생성되었습니다!");

        // orderProducts를 문자열로 변환하여 URL-safe 포맷으로 인코딩
        const orderProducts = encodeURIComponent(JSON.stringify(data.orderProducts));

        router.push(
            `/order-complete?email=${data.email}&status=${data.status}&orderProducts=${orderProducts}`
        );
      } else {
        const error = await response.json();
        alert(`주문 실패: ${error.message}`);
      }
    } catch (err) {
      console.error(err);
      alert("주문 요청 중 오류가 발생했습니다.");
    }
  };

  return (
      <div className="min-h-screen bg-gray-200">
        <div className="max-w-7xl mx-auto p-8">
          <h1 className="text-3xl font-bold text-center mb-8">Grids & Circle</h1>
          <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
            {/* Left: 상품 목록 */}
            <div className="lg:col-span-2">
              <h2 className="text-lg font-semibold mb-4">상품 목록</h2>
              <div className="grid grid-cols-1 gap-4">
                {products.map((product) => (
                    <div
                        key={product.id}
                        className="flex items-center justify-between p-4 bg-white border rounded-md shadow-sm"
                    >
                      <div>
                        <h3 className="font-bold text-gray-700">{product.name}</h3>
                        <span className={`text-sm ${product.status ? 'text-green-600' : 'text-red-600'}`}>
                          {product.status ? '판매중' : '품절'}
                        </span>
                      </div>
                      <p className="text-gray-700">{product.price}원</p>
                      {product.status ? (
                          <button onClick={() => addToCart(product)} className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600">
                            추가
                          </button>
                      ) : (
                          <button disabled className="px-4 py-2 bg-gray-400 text-white rounded-md cursor-not-allowed">
                            품절
                          </button>
                      )}
                    </div>
                ))}
              </div>
            </div>

            {/* Right: Summary and 주문 정보 */}
            <div>
              <h2 className="text-lg font-semibold mb-4">Summary</h2>
              {cart.length > 0 ? (
                  <div className="space-y-4 mb-4">
                    {cart.map((item) => (
                        <div
                            key={item.productId}
                            className="flex justify-between items-center p-4 bg-white border rounded-md shadow-sm"
                        >
                          <h3 className="font-semibold text-gray-700">{item.name}</h3>
                          <p className="text-gray-600">{item.quantity}개</p>
                          <button
                              onClick={() => removeFromCart(item.productId)}
                              className="text-red-500 hover:underline"
                          >
                            삭제
                          </button>
                        </div>
                    ))}
                  </div>
              ) : (
                  <p className="mb-4 text-gray-600">장바구니가 비어 있습니다.</p>
              )}

              {/* 주문 정보 */}
              <form onSubmit={handleOrderSubmit} className="space-y-4">
                <div>
                  <label className="block font-semibold">이메일</label>
                  <input
                      type="email"
                      value={email}
                      onChange={(e) => setEmail(e.target.value)}
                      className="w-full p-2 border rounded-md"
                      required
                  />
                </div>
                <div>
                  <label className="block font-semibold">주소</label>
                  <div className="flex">
                    <input
                        type="text"
                        value={address}
                        className="flex-1 p-2 border rounded-md"
                        readOnly
                    />
                    <button
                        type="button"
                        onClick={handleAddressSearch}
                        className="ml-2 px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600"
                    >
                      주소 검색
                    </button>
                  </div>
                </div>
                <div>
                  <label className="block font-semibold">우편번호</label>
                  <input
                      type="text"
                      value={postalCode}
                      className="w-full p-2 border rounded-md"
                      readOnly
                  />
                </div>
                <p className="text-lg font-bold text-gray-700 mt-4">
                  당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.
                </p>
                <p className="font-bold text-right text-lg mt-2">총 금액: {totalPrice}원</p>
                <button
                    type="submit"
                    className="w-full px-4 py-2 bg-green-500 text-white rounded-md hover:bg-green-600"
                >
                  결제하기
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
  );
}

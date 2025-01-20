// src/components/products/ProductList.tsx
'use client';
import { useState, useEffect } from 'react';

interface Product {
    id: number;
    name: string;
    price: number;
    stock: number;
    status: boolean;
}

export default function ProductList() {
    const [products, setProducts] = useState<Product[]>([]);

    useEffect(() => {
        fetch('http://localhost:8080/products')
            .then(res => res.json())
            .then(data => setProducts(data))
            .catch(error => console.error('Error:', error));
    }, []);

    return (
        <div className="overflow-x-auto">
            <table className="min-w-full border-collapse bg-white shadow-sm rounded-lg">
                <thead>
                <tr className="bg-gray-50">
                    <th className="border-b px-6 py-4 text-left text-sm font-medium text-gray-500">상품명</th>
                    <th className="border-b px-6 py-4 text-left text-sm font-medium text-gray-500">가격</th>
                    <th className="border-b px-6 py-4 text-left text-sm font-medium text-gray-500">재고</th>
                    <th className="border-b px-6 py-4 text-left text-sm font-medium text-gray-500">상태</th>
                </tr>
                </thead>
                <tbody className="divide-y divide-gray-200">
                {products.map(product => (
                    <tr key={product.id}>
                        <td className="px-6 py-4 text-sm">{product.name}</td>
                        <td className="px-6 py-4 text-sm">{product.price.toLocaleString()}원</td>
                        <td className="px-6 py-4 text-sm">{product.stock}</td>
                        <td className="px-6 py-4 text-sm">
                <span className={`inline-flex rounded-full px-2 text-xs font-semibold leading-5 ${
                    product.status
                        ? 'bg-green-100 text-green-800'
                        : 'bg-red-100 text-red-800'
                }`}>
                  {product.status ? '판매중' : '품절'}
                </span>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}
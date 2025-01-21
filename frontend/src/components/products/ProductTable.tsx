import React from 'react';
import { Product } from '@/types/product';
import { Pencil, Trash2 } from 'lucide-react';
import {API_BASE_URL, API_ENDPOINTS} from "@/constants/api";

interface ProductTableProps {
    products: Product[];
    onEdit: (product: Product) => void;
    onDelete: (id: number) => void;
    onStatusToggle: (id: number, status: boolean) => void;
}

export const ProductTable = ({ products, onEdit, onDelete, onStatusToggle }: ProductTableProps) => {
    // products가 없거나 배열이 아닐 때 처리
    if (!products || !Array.isArray(products)) {
        return <div>상품이 없습니다.</div>;
    }
    const formImageUrl = (imageUrl: string) => {
        return `http://localhost:8080${imageUrl.replace('/static', '/api/v1')}`;
    }


    return (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            {products.map((product) => (
                <div key={product.id} className="border rounded-lg p-4 shadow">
                    <img
                        src={formImageUrl(product.imageUrl)}
                        alt={product.name}
                        className="w-full h-48 object-cover mb-4 rounded"
                    />
                    <h3 className="text-gray-600">{product.name}</h3>
                    <p className="text-gray-600">Price: {product.price}₩</p>
                    <p className="text-gray-600">재고: {product.stock}</p>
                    <div className="flex justify-between items-center mt-4">
                        <div className="flex gap-2">
                            <button
                                onClick={() => onEdit(product)}
                                className="p-2 text-blue-500 hover:bg-blue-100 rounded"
                            >
                                <Pencil size={20} />
                            </button>
                            <button
                                onClick={() => onDelete(product.id)}
                                className="p-2 text-red-500 hover:bg-red-100 rounded"
                            >
                                <Trash2 size={20} />
                            </button>
                        </div>
                        <button
                            onClick={() => onStatusToggle(product.id, product.status)}
                            className={`px-3 py-1 rounded ${
                                product.status
                                    ? 'bg-green-100 text-green-800'
                                    : 'bg-gray-100 text-gray-800'
                            }`}
                        >
                            {product.status ? 'On Sale' : 'On Hold'}
                        </button>
                    </div>
                </div>
            ))}
        </div>
    );
};
'use client';

import React, { useState, useEffect } from 'react';
import { ProductTable } from '@/components/products/ProductTable';
import { Product, ProductFormData } from '@/types/product';
import { API_ENDPOINTS } from '@/constants/api';
import { PlusCircle } from 'lucide-react';
import { useRouter } from 'next/navigation';

export default function ProductManagementPage() {
    const [products, setProducts] = useState<Product[]>([]);
    const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [formMode, setFormMode] = useState<'create' | 'edit'>('create');
    const [formData, setFormData] = useState<ProductFormData>({
        name: '',
        price: '',
        stock: '',
        status: true,
        description: '',
        image: null
    });
    const router = useRouter();

    // 상품 목록 조회
    const fetchProducts = async () => {
        try {
            const response = await fetch(API_ENDPOINTS.PRODUCTS);
            const data = await response.json();
            setProducts(data);
        } catch (error) {
            console.error('Error fetching products:', error);
        }
    };

    useEffect(() => {
        fetchProducts();
    }, []);

    // 입력 핸들러
    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value, type } = e.target;
        if (type === 'file') {
            const fileInput = e.target as HTMLInputElement;
            const file = fileInput.files?.[0];
            setFormData(prev => ({
                ...prev,
                image: file || null
            }));
        } else {
            setFormData(prev => ({
                ...prev,
                [name]: value
            }));
        }
    };

    // 상품 생성
    const handleCreate = async (e: React.FormEvent) => {
        e.preventDefault();

        const formDataToSend = new FormData();
        formDataToSend.append('name', formData.name);
        formDataToSend.append('price', formData.price.toString());
        formDataToSend.append('stock', formData.stock.toString());
        formDataToSend.append('status', formData.status.toString());
        formDataToSend.append('description', formData.description);
        if (formData.image) {
            formDataToSend.append('file', formData.image);
        }

        try {
            const response = await fetch(API_ENDPOINTS.PRODUCTS, {
                method: 'POST',
                body: formDataToSend,
            });

            if (!response.ok) throw new Error('Failed to create product');

            await fetchProducts();
            setIsModalOpen(false);
            resetForm();
        } catch (error) {
            console.error('Error creating product:', error);
            alert('상품 등록에 실패했습니다.');
        }
    };

    // 상품 수정
    const handleUpdate = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!selectedProduct) return;

        try {
            const response = await fetch(API_ENDPOINTS.PRODUCT_DETAIL(selectedProduct.id), {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    id: selectedProduct.id,
                    name: formData.name,
                    price: Number(formData.price),
                    stock: Number(formData.stock),
                    status: formData.status,
                    description: formData.description,
                    imageUrl: selectedProduct.imageUrl,
                }),
            });

            if (!response.ok) throw new Error('Failed to update product');

            await fetchProducts();
            setIsModalOpen(false);
            resetForm();
        } catch (error) {
            console.error('Error updating product:', error);
            alert('상품 수정에 실패했습니다.');
        }
    };

    // 상품 삭제
    const handleDelete = async (id: number) => {
        if (window.confirm('정말로 이 상품을 삭제하시겠습니까?')) {
            try {
                const response = await fetch(API_ENDPOINTS.PRODUCT_DETAIL(id), {
                    method: 'DELETE',
                });

                if (response.ok) {
                    await fetchProducts();
                }
            } catch (error) {
                console.error('Error deleting product:', error);
            }
        }
    };

    // 상태 변경
    const handleStatusToggle = async (id: number, currentStatus: boolean) => {
        try {
            const response = await fetch(API_ENDPOINTS.PRODUCT_STATUS(id), {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ status: !currentStatus }),
            });

            if (response.ok) {
                await fetchProducts();
            }
        } catch (error) {
            console.error('Error updating status:', error);
        }
    };

    // 상품 수정 모달 열기
    const handleEditClick = (product: Product) => {
        setSelectedProduct(product);
        setFormData({
            name: product.name,
            price: product.price.toString(),
            stock: product.stock.toString(),
            status: product.status,
            description: product.description || '',
            image: null
        });
        setFormMode('edit');
        setIsModalOpen(true);
    };

    // 폼 초기화
    const resetForm = () => {
        setFormData({
            name: '',
            price: '',
            stock: '',
            status: true,
            description: '',
            image: null
        });
        setSelectedProduct(null);
    };

    return (
        <div className="container mx-auto p-4">
            <div className="flex justify-between items-center mb-6">
                <h1 className="text-2xl font-bold">Product Management</h1>
                <button
                    onClick={() => {
                        setFormMode('create');
                        setIsModalOpen(true);
                        resetForm();
                    }}
                    className="flex items-center gap-2 bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
                >
                    <PlusCircle size={20} />
                    Add Product
                </button>
            </div>

            <ProductTable
                products={products}
                onEdit={handleEditClick}
                onDelete={handleDelete}
                onStatusToggle={handleStatusToggle}
            />

            {isModalOpen && (
                <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
                    <div className="bg-white p-6 rounded-lg w-full max-w-md">
                        <h2 className="text-xl font-bold mb-4">
                            {formMode === 'create' ? '상품 등록' : '상품 수정'}
                        </h2>
                        <form onSubmit={formMode === 'create' ? handleCreate : handleUpdate}>
                            <div className="space-y-4">
                                <div>
                                    <label className="block text-sm font-medium mb-1">상품명</label>
                                    <input
                                        type="text"
                                        name="name"
                                        value={formData.name}
                                        onChange={handleInputChange}
                                        className="w-full border rounded p-2"
                                        required
                                    />
                                </div>
                                <div>
                                    <label className="block text-sm font-medium mb-1">가격</label>
                                    <input
                                        type="number"
                                        name="price"
                                        value={formData.price}
                                        onChange={handleInputChange}
                                        className="w-full border rounded p-2"
                                        required
                                    />
                                </div>
                                <div>
                                    <label className="block text-sm font-medium mb-1">재고</label>
                                    <input
                                        type="number"
                                        name="stock"
                                        value={formData.stock}
                                        onChange={handleInputChange}
                                        className="w-full border rounded p-2"
                                        required
                                    />
                                </div>
                                <div>
                                    <label className="block text-sm font-medium mb-1">설명</label>
                                    <textarea
                                        name="description"
                                        value={formData.description}
                                        onChange={handleInputChange}
                                        className="w-full border rounded p-2"
                                        rows={3}
                                    />
                                </div>
                                {formMode === 'create' && (
                                    <div>
                                        <label className="block text-sm font-medium mb-1">이미지</label>
                                        <input
                                            type="file"
                                            name="image"
                                            onChange={handleInputChange}
                                            className="w-full border rounded p-2"
                                            accept="image/*"
                                            required
                                        />
                                    </div>
                                )}
                            </div>
                            <div className="flex justify-end gap-2 mt-6">
                                <button
                                    type="button"
                                    onClick={() => {
                                        setIsModalOpen(false);
                                        resetForm();
                                    }}
                                    className="px-4 py-2 text-gray-600 hover:bg-gray-100 rounded"
                                >
                                    취소
                                </button>
                                <button
                                    type="submit"
                                    className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
                                >
                                    {formMode === 'create' ? '등록' : '수정'}
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
}
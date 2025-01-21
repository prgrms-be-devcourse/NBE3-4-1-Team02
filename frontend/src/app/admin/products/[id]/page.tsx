'use client';

import { useForm } from 'react-hook-form';
import { useRouter } from 'next/navigation';
import { useQuery } from '@tanstack/react-query';
import { Product, ProductFormData } from '@/types/product';

export default function EditProductPage({ params }: { params: { id: string } }) {
    const router = useRouter();
    const { id } = params;

    const { data: product } = useQuery<Product>({
        queryKey: ['product', id],
        queryFn: () => fetch(`http://localhost:8080/products/${id}`).then(res => res.json())
    });

    const { register, handleSubmit } = useForm<ProductFormData>({
        values: product ? {
            name: product.name,
            price: product.price.toString(),
            stock: product.stock.toString(),
            status: product.status.toString(),
            description: product.description || '',
            image: undefined as unknown as FileList // 기존 이미지는 form에서 처리하지 않음
        } : undefined
    });

    const onSubmit = async (data: ProductFormData) => {
        await fetch(`http://localhost:8080/products/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                name: data.name,
                price: parseInt(data.price),
                stock: parseInt(data.stock),
                status: data.status === 'true',
                description: data.description
            })
        });

        router.push('/admin/products');
    };

    if (!product) return <div>Loading...</div>;

    return (
        <div className="p-4">
            <h1 className="text-xl font-bold mb-4">상품 수정</h1>
            <form onSubmit={handleSubmit(onSubmit)} className="space-y-4 max-w-xl">
                <input {...register('name')} className="w-full border p-2"/>
                <input type="number" {...register('price')} className="w-full border p-2"/>
                <input type="number" {...register('stock')} className="w-full border p-2"/>
                <select {...register('status')} className="w-full border p-2">
                    <option value="true">판매중</option>
                    <option value="false">품절</option>
                </select>
                <textarea {...register('description')} className="w-full border p-2" rows={4}/>
                <div className="flex gap-2">
                    <button
                        type="button"
                        onClick={() => router.back()}
                        className="px-4 py-2 bg-gray-500 text-white rounded"
                    >
                        취소
                    </button>
                    <button
                        type="submit"
                        className="px-4 py-2 bg-blue-500 text-white rounded"
                    >
                        수정
                    </button>
                </div>
            </form>
        </div>
    );
}
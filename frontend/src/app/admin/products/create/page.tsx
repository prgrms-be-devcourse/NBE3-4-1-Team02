// 'use client';
//
// import { useForm } from 'react-hook-form';
// import { useRouter } from 'next/navigation';
// import { ProductFormData } from '@/types/product';
//
// export default function CreateProductPage() {
//     const router = useRouter();
//     const { register, handleSubmit } = useForm<ProductFormData>();
//
//     const onSubmit = async (data : ProductFormData) => {
//         try {
//             const formData = new FormData();
//             if (data.image[0]) {
//                 formData.append('file', data.image[0]);
//             }
//             formData.append('name', data.name);
//             formData.append('price', data.price);
//             formData.append('stock', data.stock);
//             formData.append('status', data.status);
//             formData.append('description', data.description || '');
//
//             const response = await fetch('http://localhost:8080/products', {  // URL 수정
//                 method: 'POST',
//                 body: formData,
//             });
//
//             if (response.ok) {
//                 alert('상품이 등록되었습니다.');
//                 router.push('/admin/products');
//             } else {
//                 const errorData = await response.json();
//                 alert(`등록 실패: ${errorData.message || '알 수 없는 오류가 발생했습니다.'}`);
//             }
//         } catch (error) {
//             console.error('등록 중 오류 발생:', error);
//             alert('등록 중 오류가 발생했습니다.');
//         }
//     };
//
//     return (
//         <div className="p-4">
//             <h1 className="text-xl font-bold mb-4">상품 등록</h1>
//             <form onSubmit={handleSubmit(onSubmit)} className="space-y-4 max-w-xl">
//                 <input {...register('name')} placeholder="상품명" className="w-full border p-2"/>
//                 <input
//                     type="number"
//                     {...register('price')}
//                     placeholder="가격"
//                     className="w-full border p-2"
//                 />
//                 <input
//                     type="number"
//                     {...register('stock')}
//                     placeholder="재고"
//                     className="w-full border p-2"
//                 />
//                 <select {...register('status')} className="w-full border p-2">
//                     <option value="true">판매중</option>
//                     <option value="false">품절</option>
//                 </select>
//                 <input
//                     type="file"
//                     {...register('image')}
//                     accept="image/*"
//                 />
//                 <textarea
//                     {...register('description')}
//                     placeholder="상품 설명"
//                     className="w-full border p-2"
//                     rows={4}
//                 />
//                 <div className="flex gap-2">
//                     <button
//                         type="button"
//                         onClick={() => router.back()}
//                         className="px-4 py-2 bg-gray-500 text-white rounded"
//                     >
//                         취소
//                     </button>
//                     <button
//                         type="submit"
//                         className="px-4 py-2 bg-blue-500 text-white rounded"
//                     >
//                         등록
//                     </button>
//                 </div>
//             </form>
//         </div>
//     );
// }
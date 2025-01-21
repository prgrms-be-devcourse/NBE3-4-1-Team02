export interface Product {
    id: number;
    name: string;
    price: number;
    stock: number;
    status: boolean;
    imageUrl: string;
    description: string;
}

export interface ProductFormData {
    name: string;
    price: string | number;
    stock: string | number;
    status: boolean;
    description: string;
    image: File | null;
}

export interface ProductTableProps {
    products: Product[];  // 명시적으로 배열 타입 지정
    onEdit: (product: Product) => void;
    onDelete: (id: number) => void;
    onStatusToggle: (id: number, status: boolean) => void;
}
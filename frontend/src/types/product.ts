export interface Product {
    id: number;
    name: string;
    price: number;
    stock: number;
    status: boolean;
    imageUrl: string;
    description?: string;
}

export interface ProductFormData {
    name: string;
    price: string | number;
    stock: string | number;
    status: boolean;
    description: string;
    image: File | null;
}
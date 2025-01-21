// 기본 URL과 prefix를 분리해서 관리
export const API_BASE_URL = 'http://localhost:8080';
export const API_PREFIX = '/api/v1';

export const API_ENDPOINTS = {
    // prefix를 포함한 전체 경로 설정
    PRODUCTS: `${API_BASE_URL}${API_PREFIX}/admin/products`,
    PRODUCT_STATUS: (id: number) => `${API_BASE_URL}${API_PREFIX}/admin/products/${id}/status`,
    PRODUCT_DETAIL: (id: number) => `${API_BASE_URL}${API_PREFIX}/admin/products/${id}`,
};
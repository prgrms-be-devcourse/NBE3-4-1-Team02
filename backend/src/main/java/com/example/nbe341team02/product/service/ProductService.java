package com.example.nbe341team02.product.service;


import com.example.nbe341team02.product.dto.ProductDTO;
import com.example.nbe341team02.product.entity.Product;
import com.example.nbe341team02.product.exception.ProductNotFoundException;
import com.example.nbe341team02.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    //상품 목록 조회
    public List<ProductDTO> findAllProducts() {
        List<Product> products = productRepository.findAll();


        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found.");
        }

        List<ProductDTO> productDTOList = new ArrayList<>();
        for( Product product : products){
            ProductDTO productDTO = new ProductDTO(
                    product.getProductId(),
                    product.getProductName(),
                    product.getProductPrice(),
                    product.getProductStock(),
                    product.isProductStatus()
            );
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }

    //상품 추가 기능
    public ProductDTO addProduct(ProductDTO productDTO) {
        // ProductDTO를 Product 엔티티로 변환
        Product product = new Product(
                null,
                productDTO.getProductName(),
                productDTO.getProductPrice(),
                productDTO.getProductStock(),
                productDTO.isProductStatus(),
                LocalDateTime.now(), // createdAt
                LocalDateTime.now()  // updatedAt
        );
        Product savedProduct = productRepository.save(product);
        // 저장된 상품을 DTO로 변환하여 반환
        return new ProductDTO(
                savedProduct.getProductId(),
                savedProduct.getProductName(),
                savedProduct.getProductPrice(),
                savedProduct.getProductStock(),
                savedProduct.isProductStatus()
        );
    }

    //--김규진--
    //상품 수정 기능
    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        //예외 - 상품이 없음
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        product.setProductName(productDTO.getProductName());
        product.setProductPrice(productDTO.getProductPrice());
        product.setProductStock(productDTO.getProductStock());
        product.setProductStatus(productDTO.isProductStatus());

        Product updatedProduct = productRepository.save(product);

        return convertToDTO(updatedProduct);

    }
    // 상품 삭제
    @Transactional
    public void deleteProduct(Long id) {
        //예외 - 상품이 없음
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        productRepository.delete(product);
    }

    // DTO로 변환 메소드
    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
                product.getProductId(),
                product.getProductName(),
                product.getProductPrice(),
                product.getProductStock(),
                product.isProductStatus()
        );
    }

    // Entity로 변환 메소드
    private Product convertToEntity(ProductDTO productDTO) {
        return Product.builder()
                .productName(productDTO.getProductName())
                .productPrice(productDTO.getProductPrice())
                .productStock(productDTO.getProductStock())
                .productStatus(productDTO.isProductStatus())
                .build();
    }

}

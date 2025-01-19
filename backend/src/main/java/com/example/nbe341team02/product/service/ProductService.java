//package com.example.nbe341team02.product.service;
//
//
//import com.example.nbe341team02.product.dto.ProductDTO;
//import com.example.nbe341team02.product.entity.Product;
//import com.example.nbe341team02.product.exception.ProductNotFoundException;
//import com.example.nbe341team02.product.repository.ProductRepository;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class ProductService {
//    private final ProductRepository productRepository;
//    //상품 목록 조회
//    public List<ProductDTO> findAllProducts() {
//        List<Product> products = productRepository.findAll();
//
//
//        if (products.isEmpty()) {
//            throw new ProductNotFoundException("No products found.");
//        }
//
//        List<ProductDTO> productDTOList = new ArrayList<>();
//        for( Product product : products){
//            ProductDTO productDTO = new ProductDTO(
//                    product.getId(),
//                    product.getName(),
//                    product.getPrice(),
//                    product.getStock(),
//                    product.isStatus()
//            );
//            productDTOList.add(productDTO);
//        }
//        return productDTOList;
//    }
//
//    //상품 추가 기능
//    public ProductDTO addProduct(ProductDTO productDTO) {
//        // ProductDTO를 Product 엔티티로 변환
//        Product product = new Product(
//                null,
//                productDTO.getName(),
//                productDTO.getPrice(),
//                productDTO.getStock(),
//                productDTO.isStatus(),
//                LocalDateTime.now(), // createdAt
//                LocalDateTime.now()  // updatedAt
//        );
//
//
//        Product savedProduct = productRepository.save(product);
//
//        // 저장된 상품을 DTO로 변환하여 반환
//        return new ProductDTO(
//                savedProduct.getId(),
//                savedProduct.getName(),
//                savedProduct.getPrice(),
//                savedProduct.getStock(),
//                savedProduct.isStatus()
//        );
//    }
//
//    //--김규진--
//    //상품 수정 기능
//    @Transactional
//    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
//        //예외 - 상품이 없음
//        Product product = productRepository.findById(id)
//                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
//
//        product.setName(productDTO.getName());
//        product.setPrice(productDTO.getPrice());
//        product.setStock(productDTO.getStock());
//        product.setStatus(productDTO.isStatus());
//
//        Product updatedProduct = productRepository.save(product);
//
//        return convertToDTO(updatedProduct);
//
//    }
//    // 상품 삭제
//    @Transactional
//    public void deleteProduct(Long id) {
//        //예외 - 상품이 없음
//        Product product = productRepository.findById(id)
//                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
//
//        productRepository.delete(product);
//    }
//
//    // DTO로 변환 메소드
//    private ProductDTO convertToDTO(Product product) {
//        return new ProductDTO(
//                product.getId(),
//                product.getName(),
//                product.getPrice(),
//                product.getStock(),
//                product.isStatus()
//        );
//    }
//
//    // Entity로 변환 메소드
//    private Product convertToEntity(ProductDTO productDTO) {
//        return Product.builder()
//                .name(productDTO.getName())
//                .price(productDTO.getPrice())
//                .stock(productDTO.getStock())
//                .status(productDTO.isStatus())
//                .build();
//    }
//
//}
